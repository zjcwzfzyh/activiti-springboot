package com.test.activiti.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableField;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;
    import lombok.ToString;

/**
* <p>
* 角色和权限中间表
* </p>
*
* @author aaa
* @since 2020-05-06
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("tbl_gb_role_perm_related")
@ApiModel(value="RolePermRelated对象", description="角色和权限中间表")
public class RolePermRelated implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色主键")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "权限主键")
    @TableField("perm_id")
    private Long permId;


}