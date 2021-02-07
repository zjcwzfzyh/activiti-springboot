package com.test.activiti.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test.activiti.entity.RoleInfo;
import com.test.activiti.util.PageUtil;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author aaa
 * @since 2020-05-06
 */
public interface IRoleInfoService extends IService<RoleInfo> {

    /**
     * @Description : 获取角色列表
     * @methodName : getRoleInfoList
     * @param roleInfo :
     * @param pageUtil :
     * @return : com.baomidou.mybatisplus.core.metadata.IPage<com.test.activiti.entity.RoleInfo>
     * @exception :
     * @author : aaa
     */
    IPage<RoleInfo> getRoleInfoList(RoleInfo roleInfo, PageUtil pageUtil);

    /**
     * @Description : 添加角色
     * @methodName : addRoleInfo
     * @param roleInfo :
     * @param permIds :
     * @return : boolean
     * @exception :
     * @author : aaa
     */
    boolean addRoleInfo(RoleInfo roleInfo, String permIds);

    /**
     * @Description : 修改角色
     * @methodName : updateRoleInfo
     * @param roleInfo :
     * @param permIds :
     * @return : void
     * @exception :
     * @author : aaa
     */
    void updateRoleInfo(RoleInfo roleInfo, String permIds);

    /**
     * @Description : 删除角色
     * @methodName : deleteRoleInfo
     * @param id :
     * @return : void
     * @exception :
     * @author : aaa
     */
    void deleteRoleInfo(Long id);

    List<RoleInfo> getRoleInfoListByUserId(Long userId);


    void test();
}
