package com.test.activiti.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.activiti.entity.RoleInfo;
import com.test.activiti.entity.RolePermRelated;
import com.test.activiti.mapper.RoleInfoMapper;
import com.test.activiti.mapper.RolePermRelatedMapper;
import com.test.activiti.service.IRoleInfoService;
import com.test.activiti.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author aaa
 * @since 2020-05-06
 */
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo> implements IRoleInfoService {

    @Autowired
    private RoleInfoMapper roleInfoMapper;
    @Autowired
    private RolePermRelatedMapper rolePermRelatedMapper;

    /**
     * @Description : 获取角色列表
     * @methodName : getRoleInfoList
     * @param roleInfo :
     * @param pageUtil :
     * @return : com.baomidou.mybatisplus.core.metadata.IPage<com.test.activiti.entity.RoleInfo>
     * @exception :
     * @author : aaa
     */
    @Override
    public IPage<RoleInfo> getRoleInfoList(RoleInfo roleInfo, PageUtil pageUtil) {
        IPage<RoleInfo> iPage = new Page<>(pageUtil.getPage(),pageUtil.getLimit());
        QueryWrapper<RoleInfo> queryWrapper = new QueryWrapper<>();
        if (roleInfo.getRoleName() != null && !"".equals(roleInfo.getRoleName())){
            queryWrapper.like("role_name",roleInfo.getRoleName());
        }
        Optional.ofNullable(roleInfo.getStartTime()).ifPresent(n ->
                queryWrapper.gt("create_time",roleInfo.getStartTime()));
        Optional.ofNullable(roleInfo.getEndTime()).ifPresent(n ->
                queryWrapper.lt("create_time",roleInfo.getEndTime()));
        queryWrapper.eq("is_enable",1);
        iPage = roleInfoMapper.selectPage(iPage,queryWrapper);
        return iPage;
    }

    /**
     * @Description : 添加角色
     * @methodName : addRoleInfo
     * @param roleInfo :
     * @param permIds :
     * @return : boolean
     * @exception :
     * @author : aaa
     */
    @Override
    @Transactional
    public boolean addRoleInfo(RoleInfo roleInfo, String permIds) {
        QueryWrapper<RoleInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name",roleInfo.getRoleName());
        queryWrapper.eq("is_enable",1);
        RoleInfo info = roleInfoMapper.selectOne(queryWrapper);
        if (info != null){
            return false;
        }
        roleInfoMapper.insert(roleInfo);
        String[] split = permIds.split(",");
        for (int i = 0; i < split.length; i++) {
            RolePermRelated rolePermRelated = new RolePermRelated();
            rolePermRelated.setRoleId(roleInfo.getId()).setPermId(Long.parseLong(split[i]));
            rolePermRelatedMapper.insert(rolePermRelated);
        }
        return true;
    }

    /**
     * @Description : 修改角色
     * @methodName : updateRoleInfo
     * @param roleInfo :
     * @param permIds :
     * @return : void
     * @exception :
     * @author : aaa
     */
    @Override
    @Transactional
    public void updateRoleInfo(RoleInfo roleInfo, String permIds) {
        //删除角色与权限关系
        QueryWrapper<RolePermRelated> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleInfo.getId());
        rolePermRelatedMapper.delete(queryWrapper);
        //修改角色信息
        roleInfoMapper.updateById(roleInfo);
        String[] split = permIds.split(",");
        for (int i = 0; i < split.length; i++) {
            RolePermRelated rolePermRelated = new RolePermRelated();
            rolePermRelated.setRoleId(roleInfo.getId()).setPermId(Long.parseLong(split[i]));
            rolePermRelatedMapper.insert(rolePermRelated);
        }
    }

    /**
     * @Description : 删除角色
     * @methodName : deleteRoleInfo
     * @param id :
     * @return : void
     * @exception :
     * @author : aaa
     */
    @Override
    public void deleteRoleInfo(Long id) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setId(id).setIsEnable(0);
        roleInfoMapper.updateById(roleInfo);
    }

    @Override
    public List<RoleInfo> getRoleInfoListByUserId(Long userId) {
        return roleInfoMapper.getRoleInfoListByUserId(userId);
    }
}
