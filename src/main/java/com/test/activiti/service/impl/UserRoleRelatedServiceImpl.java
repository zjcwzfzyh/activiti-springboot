package com.test.activiti.service.impl;

import com.test.activiti.entity.UserRoleRelated;
import com.test.activiti.mapper.UserRoleRelatedMapper;
import com.test.activiti.service.IUserRoleRelatedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色中间表 服务实现类
 * </p>
 *
 * @author aaa
 * @since 2020-05-06
 */
@Service
public class UserRoleRelatedServiceImpl extends ServiceImpl<UserRoleRelatedMapper, UserRoleRelated> implements IUserRoleRelatedService {

}
