package com.test.activiti.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.activiti.common.LoginUserInfoManager;
import com.test.activiti.entity.UserInfo;
import com.test.activiti.entity.UserRoleRelated;
import com.test.activiti.mapper.UserInfoMapper;
import com.test.activiti.mapper.UserRoleRelatedMapper;
import com.test.activiti.service.IUserInfoService;
import com.test.activiti.util.MD5Util;
import com.test.activiti.util.PageUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author aaa
 * @since 2020-05-06
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserRoleRelatedMapper userRoleRelatedMapper;

    /**
     * @Description : 获取用户列表信息
     * @methodName : getUserInfoList
     * @param userInfo :
     * @param pageUtil :
     * @return : IPage<UserInfo>
     * @exception :
     * @author : aaa
     */
    @Override
    public IPage<UserInfo> getUserInfoList(UserInfo userInfo, PageUtil pageUtil) {
        IPage<UserInfo> iPage = new Page<>(pageUtil.getPage(),pageUtil.getLimit());
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        if (userInfo.getName() != null && !"".equals(userInfo.getName())){
            queryWrapper.like("name",userInfo.getName());
        }
        Optional.ofNullable(userInfo.getStartTime()).ifPresent(n ->
                queryWrapper.gt("create_time",userInfo.getStartTime()));
        Optional.ofNullable(userInfo.getEndTime()).ifPresent(n ->
                queryWrapper.lt("create_time",userInfo.getEndTime()));
        queryWrapper.eq("is_enable",1);
        iPage = userInfoMapper.selectPage(iPage,queryWrapper);
        return iPage;
    }

    /**
     * @Description : 添加用户信息
     * @methodName : addUserInfo
     * @param userInfo :
     * @param roleIds :
     * @return : boolean
     * @exception :
     * @author : aaa
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUserInfo(UserInfo userInfo, String[] roleIds) {
        userInfo.setPassword(MD5Util.getMD5(userInfo.getPassword())).setCreateTime(LocalDateTime.now());
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userInfo.getUserName());
        queryWrapper.eq("is_enable",1);
        UserInfo info = userInfoMapper.selectOne(queryWrapper);
        if (info != null){
            return false;
        }
        userInfoMapper.insert(userInfo);
        //添加用户与角色关系
        for (int i = 1; i < roleIds.length; i++) {
            UserRoleRelated userRoleRelated = new UserRoleRelated();
            userRoleRelated.setUserId(userInfo.getId()).setRoleId(Long.parseLong(roleIds[i]));
            userRoleRelatedMapper.insert(userRoleRelated);
        }
        return true;
    }

    /**
     * @Description : 修改用户信息
     * @methodName : updateUserInfo
     * @param userInfo :
     * @param roleIds :
     * @return : void
     * @exception :
     * @author : aaa
     */
    @Override
    @Transactional
    public void updateUserInfo(UserInfo userInfo, String[] roleIds) {
        //删除用户与角色的所有关系
        QueryWrapper<UserRoleRelated> userRoleRelatedWrapper = new QueryWrapper<>();
        userRoleRelatedWrapper.eq("user_id",userInfo.getId());
        userRoleRelatedMapper.delete(userRoleRelatedWrapper);

        //修改用户信息
        userInfoMapper.updateById(userInfo);
        //插入用户与角色的关系
        for (int i = 1; i < roleIds.length; i++) {
            UserRoleRelated userRoleRelated = new UserRoleRelated();
            userRoleRelated.setUserId(userInfo.getId()).setRoleId(Long.parseLong(roleIds[i]));
            userRoleRelatedMapper.insert(userRoleRelated);
        }
    }

    /**
     * @Description : 删除用户信息
     * @methodName : deleteUserInfo
     * @param id :
     * @return : void
     * @exception :
     * @author : aaa
     */
    @Override
    public void deleteUserInfo(Long id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id).setIsEnable(0);
        userInfoMapper.updateById(userInfo);
    }

    /**
     * @Description : 重置密码
     * @methodName : resetPassword
     * @param userInfo :
     * @return : void
     * @exception :
     * @author : aaa
     */
    @Override
    public void resetPassword(UserInfo userInfo) {
        userInfo.setPassword(MD5Util.getMD5("123456"));
        userInfoMapper.updateById(userInfo);
    }

    /**
     * @Description : 修改密码
     * @methodName : updateUserPassword
     * @param password :
     * @return : void
     * @exception :
     * @author : aaa
     */
    @Override
    public boolean updateUserPassword(String oldPassword,String password) {
        UserInfo userInfo = LoginUserInfoManager.getUserInfo();
        if (!userInfo.getPassword().equals(MD5Util.getMD5(oldPassword))){
            return false;
        }
        UserInfo info = new UserInfo();
        info.setId(userInfo.getId()).setPassword(MD5Util.getMD5(password));
        userInfoMapper.updateById(info);
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout();
        }
        return true;
    }

    /**
     * @Description : 根据id获取用户信息
     * @methodName : getUserInfoById
     * @param id :
     * @return : java.util.List<java.lang.Object>
     * @exception :
     * @author : aaa
     */
    @Override
    public List<Object> getUserInfoById(Long id) {
        List<Object> list = new ArrayList<>();
        UserInfo userInfo = userInfoMapper.selectById(id);
        QueryWrapper<UserRoleRelated> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        List<UserRoleRelated> roleInfos = userRoleRelatedMapper.selectList(queryWrapper);
        list.add(userInfo);
        list.add(roleInfos);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void test() {
        userInfoMapper.insert(new UserInfo().setName("测试数据").setUserName("测试数据"));
        int i = 1/0;
    }
}
