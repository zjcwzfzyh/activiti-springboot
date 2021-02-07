package com.test.activiti.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
* 
* </p>
*
* @author aaa
* @since 2021-01-21
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("tbl_gb_form")
@ApiModel(value="Form对象", description="")
public class Form implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("days")
    private Integer days;

    @TableField("reason")
    private String reason;

    @TableField("applyer")
    private Long applyer;

    @TableField("approver")
    private Long approver;

    @TableField("status")
    private Integer status;

    @TableField(exist = false)
    private String applyerName;

    @TableField(exist = false)
    private String approverName;

    @ApiModelProperty(value = "0 未提交 1 已提交")
    @TableField("is_sub")
    private Integer isSub;

}