package com.test.activiti.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.activiti.common.LoginUserInfoManager;
import com.test.activiti.common.ResponseResult;
import com.test.activiti.entity.Form;
import com.test.activiti.entity.UserInfo;
import com.test.activiti.service.IFormService;
import com.test.activiti.service.IUserInfoService;
import com.test.activiti.util.PageUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
* <p>
*  控制层
* </p>
*
* @author aaa
* @since 2021-01-21
*/
@Slf4j
@RestController
@Api(tags = "接口")
public class FormController {

    @Autowired
    private IFormService formService;
    @Autowired
    private IUserInfoService userInfoService;

    @GetMapping("/getFormList")
    public ResponseResult getFormList(Form form, PageUtil pageUtil){
        IPage<Form> iPage = new Page<>(pageUtil.getPage(),pageUtil.getLimit());
        QueryWrapper<Form> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("applyer", LoginUserInfoManager.getUserInfo().getId());
        iPage = formService.page(iPage, queryWrapper);
        iPage.getRecords().forEach(f -> {
            UserInfo applyer = userInfoService.getById(f.getApplyer());
            UserInfo approver = userInfoService.getById(f.getApprover());
            Optional.ofNullable(applyer).ifPresent(n -> f.setApplyerName(n.getName()));
            Optional.ofNullable(approver).ifPresent(n -> f.setApproverName(n.getName()));
        });
        return ResponseResult.success(iPage);
    }

}
