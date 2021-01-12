package com.test.activiti.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import com.test.activiti.service.IUserRoleRelatedService;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
/**
* <p>
* 用户和角色中间表 控制层
* </p>
*
* @author aaa
* @since 2020-05-06
*/
@Slf4j
@RestController
@Api(tags = "用户和角色中间表接口")
public class UserRoleRelatedController {

    @Autowired
    private IUserRoleRelatedService userRoleRelatedService;

}
