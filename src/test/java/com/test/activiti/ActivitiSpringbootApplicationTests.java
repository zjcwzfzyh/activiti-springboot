package com.test.activiti;

import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiSpringbootApplicationTests {

    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired(required = false)
    private SecurityUtil securityUtil;

    @Test
    public void contextLoads() {
        System.out.println(123);
    }

    @Test
    public void testDefinition(){
        securityUtil.logInAs("salaboy");

        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        processDefinitionPage.getTotalItems();
        System.out.println(processDefinitionPage.getTotalItems());
        for (ProcessDefinition processDefinition : processDefinitionPage.getContent()) {
            System.out.println(processDefinition);
        }
    }

    @Test
    public void test(){
        securityUtil.logInAs("salaboy");

        ProcessInstance myProcess_1 = processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey("myProcess_1").build());
        System.out.println("流程实例ID：" + myProcess_1.getId());
    }

    /**
     * 查询任务，并完成自己的任务
     */
    @Test
    public void testTask() {
        securityUtil.logInAs("ryandawsonuk");
        Page<Task> taskPage=taskRuntime.tasks(Pageable.of(0,10));
        if (taskPage.getTotalItems()>0){
            for (Task task:taskPage.getContent()){
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
                System.out.println("任务："+task);
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
            }
        }
        Page<Task> taskPage2=taskRuntime.tasks(Pageable.of(0,10));
        if (taskPage2.getTotalItems()>0){
            System.out.println("任务："+taskPage2.getContent());
        }
    }
}
