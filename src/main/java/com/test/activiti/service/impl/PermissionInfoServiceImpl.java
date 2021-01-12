package com.test.activiti.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.activiti.entity.PermissionInfo;
import com.test.activiti.mapper.PermissionInfoMapper;
import com.test.activiti.service.IPermissionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限信息表 服务实现类
 * </p>
 *
 * @author aaa
 * @since 2020-05-06
 */
@Service
public class PermissionInfoServiceImpl extends ServiceImpl<PermissionInfoMapper, PermissionInfo> implements IPermissionInfoService {
    @Autowired
    private PermissionInfoMapper permissionInfoMapper;

    @Override
    public List<PermissionInfo> getPermissionInfoListByRoleId(Long roleId) {
        return permissionInfoMapper.getPermissionInfoListByRoleId(roleId);
    }
}
