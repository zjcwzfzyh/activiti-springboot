package com.test.activiti.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.activiti.entity.RoleInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author aaa
 * @since 2020-05-06
 */
@Repository
public interface RoleInfoMapper extends BaseMapper<RoleInfo> {
    List<RoleInfo> getRoleInfoListByUserId(Long userId);
}
