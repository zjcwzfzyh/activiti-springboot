package com.test.activiti.litsener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.activiti.config.SpringContextHolder;
import com.test.activiti.entity.UserInfo;
import com.test.activiti.mapper.UserInfoMapper;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: activiti-springboot
 * @description: 放假流程监听器
 * @author: 张永辉
 * @create: 2021-01-20 14:21
 **/
@Component
public class LeaveLitsener implements TaskListener {

    /*@Autowired
    private UserInfoMapper userInfoMapper;*/

    @Override
    public void notify(DelegateTask delegateTask) {
        UserInfoMapper userInfoMapper = SpringContextHolder.getBean(UserInfoMapper.class);
        if ("部门经理审批".equals(delegateTask.getName())){
            List<UserInfo> list = userInfoMapper.selectList(new QueryWrapper<UserInfo>().eq("position", 1));
            List<String> ids = list.stream().map(u -> u.getId().toString()).collect(Collectors.toList());
            delegateTask.addCandidateUsers(ids);
        }
        if ("总经理审批".equals(delegateTask.getName())){
            List<UserInfo> list = userInfoMapper.selectList(new QueryWrapper<UserInfo>().eq("position", 2));
            List<String> ids = list.stream().map(u -> u.getId().toString()).collect(Collectors.toList());
            delegateTask.addCandidateUsers(ids);
        }
    }
}
