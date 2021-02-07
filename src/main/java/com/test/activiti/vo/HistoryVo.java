package com.test.activiti.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @program: activiti-springboot
 * @description: 、
 * @author: 张永辉
 * @create: 2021-02-06 14:46
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(callSuper = true)
public class HistoryVo {
    /**
     * 任务名称
     */
    private String name;

    /**
     * 处理人
     */
    private String assignee;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 批注
     */
    private String comment;

    /**
     * 申请人
     */
    private String applyer;

}
