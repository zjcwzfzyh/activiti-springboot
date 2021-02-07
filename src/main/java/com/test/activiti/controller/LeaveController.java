package com.test.activiti.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.activiti.common.LoginUserInfoManager;
import com.test.activiti.common.ResponseResult;
import com.test.activiti.entity.Form;
import com.test.activiti.entity.UserInfo;
import com.test.activiti.service.IFormService;
import com.test.activiti.service.IUserInfoService;
import com.test.activiti.util.PageUtil;
import com.test.activiti.vo.HistoryVo;
import com.test.activiti.vo.TaskVo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: activiti-springboot
 * @description: 请假流程控制层
 * @author: 张永辉
 * @create: 2021-01-20 14:32
 **/
@RestController
@Slf4j
public class LeaveController {

    @Autowired
    private IFormService formService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;


    /**
     * 提交请假单
     * @param form
     * @return
     */
    @RequestMapping("/submitLeaveForm")
    public ResponseResult submitLeaveForm(Form form){
        //1.保存请假单
        form.setApplyer(LoginUserInfoManager.getUserInfo().getId());
        form.setStatus(1);
        form.setIsSub(0);
        formService.save(form);
        //2.启动流程实例
        List<Deployment> deploymentList =
                repositoryService.createDeploymentQuery().deploymentName("SpringAutoDeployment").orderByDeploymenTime().desc().list();
        Deployment deployment = deploymentList.get(0);
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        if (null == processDefinition) {
            throw new RuntimeException("流程启动失败");
        }
        //3.设置通过条件
        Map<String, Object> variables = new HashMap<>();
        variables.put("form", form);
        runtimeService.startProcessInstanceByKey(processDefinition.getKey(), form.getId().toString(), variables);

        return ResponseResult.success();
    }

    /**
     * 完成请假单
     * @param id
     * @return
     */
    @RequestMapping("/completeSubmit")
    public ResponseResult completeSubmit(Long id){
        //1 根据id获取请假单信息
        Form form = formService.getById(id);

        //2 根据业务Id，完成任务
        Execution execution = runtimeService.createExecutionQuery().processInstanceBusinessKey(id.toString()).singleResult();
        String processInstanceId = execution.getProcessInstanceId();
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(form.getApplyer().toString()).singleResult();

        //3 设置通过条件
        //taskService.setVariable(task.getId(), "askForm", form);
        form.setIsSub(1);
        formService.updateById(form);

        //4 完成任务
        taskService.complete(task.getId());
        return ResponseResult.success();
    }

    /**
     * 查看任务列表
     * @return
     */
    @RequestMapping("/getTaskList")
    public ResponseResult getTaskList(PageUtil pageUtil){
        Long userId = LoginUserInfoManager.getUserInfo().getId();
        /*TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId.toString());
        List<Task> taskList = taskQuery.listPage((pageUtil.getPage() - 1) * pageUtil.getLimit(), pageUtil.getLimit());
        List<TaskVo> list = new ArrayList<>();
        taskList.forEach(t -> {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult();
            Form form = formService.getById(processInstance.getBusinessKey());
            UserInfo userInfo = userInfoService.getById(form.getApplyer());
            TaskVo taskVo = new TaskVo();
            taskVo.setApplyerName(userInfo.getName()).setReason(form.getReason()).setDays(form.getDays()).setTaskId(t.getId()).setProcessInstanceId(t.getProcessInstanceId());
            list.add(taskVo);
        });
        Page<TaskVo> taskPage = new Page<>();
        taskPage.setRecords(list);
        taskPage.setTotal(taskQuery.count());

        */
        //从执行人
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(userId.toString()).list();
        //从候选人角度查询任务
        List<Task> list = taskService.createTaskQuery().taskCandidateUser(userId.toString()).list();
        taskList.addAll(list);
        //List<Task> tasks = taskList.subList((pageUtil.getPage() - 1) * pageUtil.getLimit(), pageUtil.getPage() * pageUtil.getLimit());
        List<TaskVo> taskVos = new ArrayList<>();
        taskList.forEach(t -> {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).singleResult();
            Form form = formService.getById(processInstance.getBusinessKey());
            UserInfo userInfo = userInfoService.getById(form.getApplyer());
            TaskVo taskVo = new TaskVo();
            taskVo.setApplyerName(userInfo.getName()).setReason(form.getReason()).setDays(form.getDays()).setTaskId(t.getId()).setProcessInstanceId(t.getProcessInstanceId());
            taskVos.add(taskVo);
        });

        Page<TaskVo> taskPage = new Page<>();
        taskPage.setRecords(taskVos);
        taskPage.setTotal(taskList.size());


        return ResponseResult.success(taskPage);
    }

    /**
     * 审批通过
     * @param taskId 任务主键
     * @param comment 审批意见
     * @return
     */
    @RequestMapping("/approveTask")
    public ResponseResult approveTask(String taskId, String comment){
        log.info("审批通过");
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        Form form = formService.getById(businessKey);
        String assignee = task.getAssignee();
        if (assignee == null){
            //这是一个候选人任务，要先拾取，再完成
            taskService.claim(taskId, LoginUserInfoManager.getUserInfo().getId().toString());
            //查询最新任务
            task = taskService.createTaskQuery().taskId(taskId).singleResult();
        }else {
            //否则这是一个有处理人的任务
        }
        form.setApprover(LoginUserInfoManager.getUserInfo().getId());


        //完成任务
        taskService.addComment(taskId, task.getProcessInstanceId(), comment == null ? "" : comment);
        taskService.complete(task.getId());

        if (isFinished(processInstance.getProcessInstanceId())){
            form.setStatus(3);
        }
        formService.updateById(form);
        return ResponseResult.success();
    }

    /**
     * 审批驳回
     * @param taskId
     * @param comment
     * @return
     */
    @RequestMapping("/backOff")
    public ResponseResult backOff(String taskId, String comment){
        log.info("审批驳回");
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        Form form = formService.getById(businessKey);
        form.setStatus(2);
        taskService.setVariable(taskId, "form", form);

        if (null == task.getAssignee()) {
            //这是一个候选人任务，需要先拾取
            taskService.claim(taskId, LoginUserInfoManager.getUserInfo().getId().toString());
        }
        form.setApprover(LoginUserInfoManager.getUserInfo().getId());
        formService.updateById(form);

        //完成任务
        taskService.addComment(taskId, task.getProcessInstanceId(), comment == null ? "" : comment);
        taskService.complete(task.getId());

        return ResponseResult.success();
    }

    /**
     * 作废任务
     * @param formId
     * @return
     */
    @RequestMapping("/deleteTask")
    public ResponseResult deleteTask(Long formId){
        Form form = formService.getById(formId);
        Execution execution = runtimeService.createExecutionQuery().processInstanceBusinessKey(String.valueOf(formId)).singleResult();
        String processInstanceId = execution.getProcessInstanceId();
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(form.getApplyer().toString()).singleResult();

        //修改数据库申请单信息
        form.setStatus(0);
        formService.updateById(form);
        //更新全局变量
        taskService.setVariable(task.getId(), "form", form);
        //完成任务
        taskService.complete(task.getId());

        return ResponseResult.success();
    }

    @RequestMapping("/getComment")
    public ResponseResult getComment(Long formId){
        //注意：已完成的流程实例不能使用runtimeService查询，应使用historyService查询
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(String.valueOf(formId)).singleResult();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance.getId())
                .orderByHistoricActivityInstanceEndTime()
                .desc()
                .list();
        List<HistoricActivityInstance> newList = new ArrayList<>();
        list.forEach(h -> {
            if (!"EndEvent".equals(h.getActivityName())&&!"StartEvent".equals(h.getActivityName())&&!"提交请假单".equals(h.getActivityName())){
                newList.add(h);
            }
        });
        List<HistoryVo> historyVos = new ArrayList<>();
        newList.forEach(h ->{
            String taskId = h.getTaskId();
            List<Comment> taskComments = taskService.getTaskComments(taskId);
            Comment comment = taskComments.get(0);
            UserInfo userInfo = userInfoService.getById(h.getAssignee());
            HistoryVo historyVo = new HistoryVo();
            historyVo.setAssignee(userInfo.getName()).setComment(comment.getFullMessage()).setFinishTime(h.getEndTime()).setName(h.getActivityName());
            historyVos.add(historyVo);
        });
        Page<HistoryVo> page = new Page<>();
        page.setRecords(historyVos);
        return ResponseResult.success(page);
    }

    /**
     * 处理过的任务列表
     * @return
     */
    @RequestMapping("/queryDoneTasks")
    public ResponseResult queryDoneTasks(){
        Long userId = LoginUserInfoManager.getUserInfo().getId();
        List<HistoricActivityInstance> taskList = historyService.createHistoricActivityInstanceQuery()
                .taskAssignee(userId.toString())
                .finished()
                .list();
        List<HistoryVo> historyVos = new ArrayList<>();
        taskList.forEach(t -> {
            String processInstanceId = t.getProcessInstanceId();
            HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            Form form = formService.getById(businessKey);
            UserInfo userInfo = userInfoService.getById(form.getApplyer());

            String taskId = t.getTaskId();
            List<Comment> taskComments = taskService.getTaskComments(taskId);
            Comment comment = taskComments.get(0);
            HistoryVo historyVo = new HistoryVo();
            historyVo.setName(t.getActivityName()).setFinishTime(t.getEndTime()).setComment(comment.getFullMessage()).setApplyer(userInfo.getName());
            historyVos.add(historyVo);
        });


        Page<HistoryVo> page = new Page<>();
        page.setRecords(historyVos);
        return ResponseResult.success(page);
    }


    /**
     * 完成任务方法
     * @param taskId
     * @param processInstanceId
     * @param comment
     * @param form
     */
    private void completeTask(String taskId, String processInstanceId, String comment, Form form) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("form", form);
        // 添加批注时候的审核人
        taskService.addComment(taskId, processInstanceId, comment == null ? "" : comment);
        taskService.complete(taskId, variables);
    }

    /**
     * 判断流程是否完成
     *
     * @param processInstanceId 流程实例ID
     * @return boolean 已完成-true，未完成-false
     */
    public boolean isFinished(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().finished().processInstanceId(processInstanceId).count() > 0;
    }
}
