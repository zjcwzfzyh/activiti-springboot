package com.test.activiti.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.test.activiti.entity.UserInfo;
import com.test.activiti.util.PageUtil;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author aaa
 * @since 2020-05-06
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * @Description : 获取用户列表信息
     * @methodName : getUserInfoList
     * @param userInfo :
     * @param pageUtil :
     * @return : IPage<UserInfo>
     * @exception :
     * @author : aaa
     */
    IPage<UserInfo> getUserInfoList(UserInfo userInfo, PageUtil pageUtil);

    /**
     * @Description : 添加用户信息
     * @methodName : addUserInfo
     * @param userInfo :
     * @param roleIds :
     * @return : boolean
     * @exception :
     * @author : aaa
     */
    boolean addUserInfo(UserInfo userInfo, String[] roleIds);

    /**
     * @Description : 修改用户信息
     * @methodName : updateUserInfo
     * @param userInfo :
     * @param roleIds :
     * @return : void
     * @exception :
     * @author : aaa
     */
    void updateUserInfo(UserInfo userInfo, String[] roleIds);

    /**
     * @Description : 删除用户信息
     * @methodName : deleteUserInfo
     * @param id :
     * @return : void
     * @exception :
     * @author : aaa
     */
    void deleteUserInfo(Long id);

    /**
     * @Description : 重置密码
     * @methodName : resetPassword
     * @param userInfo :
     * @return : void
     * @exception :
     * @author : aaa
     */
    void resetPassword(UserInfo userInfo);

    /**
     * @Description : 修改密码
     * @methodName : updateUserPassword
     * @param password :
     * @return : void
     * @exception :
     * @author : aaa
     */
    boolean updateUserPassword(String oldPassword, String password);

    /**
     * @Description : 根据id获取用户信息
     * @methodName : getUserInfoById
     * @param id :
     * @return : java.util.List<java.lang.Object>
     * @exception :
     * @author : aaa
     */
    List<Object> getUserInfoById(Long id);


    void test();
}
