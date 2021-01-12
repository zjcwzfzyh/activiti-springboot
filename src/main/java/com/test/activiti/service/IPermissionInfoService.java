package com.test.activiti.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.activiti.entity.PermissionInfo;

import java.util.List;

/**
 * <p>
 * 权限信息表 服务类
 * </p>
 *
 * @author aaa
 * @since 2020-05-06
 */
public interface IPermissionInfoService extends IService<PermissionInfo> {
    List<PermissionInfo> getPermissionInfoListByRoleId(Long roleId);
}
