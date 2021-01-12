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
* 系统数据字典表
* </p>
*
* @author aaa
* @since 2020-05-07
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("tbl_gb_dictionary")
@ApiModel(value="Dictionary对象", description="系统数据字典表")
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父级id")
    @TableField("dic_parentid")
    private Long dicParentid;

    @ApiModelProperty(value = "变量名称")
    @TableField("dic_name")
    private String dicName;

    @ApiModelProperty(value = "备注信息")
    @TableField("dic_remark")
    private String dicRemark;

    @ApiModelProperty(value = "变量状态（0.删除 1.使用 2 停用）")
    @TableField("dic_status")
    private Integer dicStatus;


}