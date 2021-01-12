package com.test.activiti.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.activiti.entity.UserRoleRelated;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户和角色中间表 Mapper 接口
 * </p>
 *
 * @author aaa
 * @since 2020-05-06
 */
@Repository
public interface UserRoleRelatedMapper extends BaseMapper<UserRoleRelated> {

}
