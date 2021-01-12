package com.test.activiti.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.activiti.entity.PermissionInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 权限信息表 Mapper 接口
 * </p>
 *
 * @author aaa
 * @since 2020-05-06
 */
@Repository
public interface PermissionInfoMapper extends BaseMapper<PermissionInfo> {
    List<PermissionInfo> getPermissionInfoListByRoleId(Long roleId);
}
