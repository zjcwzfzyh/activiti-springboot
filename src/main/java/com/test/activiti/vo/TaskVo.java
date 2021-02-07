package com.test.activiti.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @program: activiti-springboot
 * @description:
 * @author: 张永辉
 * @create: 2021-01-28 15:02
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString(callSuper = true)
public class TaskVo {
    private Integer days;
    private String applyerName;
    private String reason;
    private String taskId;
    private String executionId;
    private String processInstanceId;
}
