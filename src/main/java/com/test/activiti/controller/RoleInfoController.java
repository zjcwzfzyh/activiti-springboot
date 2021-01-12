package com.test.activiti.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.test.activiti.common.ResponseResult;
import com.test.activiti.common.ResponseResultEnum;
import com.test.activiti.entity.RoleInfo;
import com.test.activiti.service.IRoleInfoService;
import com.test.activiti.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* <p>
* 角色信息表 控制层
* </p>
*
* @author aaa
* @since 2020-05-06
*/
@Slf4j
@RestController
@Api(tags = "角色信息表接口")
public class RoleInfoController {

    @Autowired
    private IRoleInfoService roleInfoService;

    /**
     * @Description : 获取角色信息列表
     * @methodName : getRoleInfoList
     * @param roleInfo :
     * @param pageUtil :
     * @return : com.lfxwkj.storage.common.ResponseResult
     * @exception :
     * @author : aaa
     */
    @ApiOperation("获取角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleInfo",value = "角色对象",required = true,dataType = "RoleInfo"),
            @ApiImplicitParam(name = "pageUtil",value = "分页工具对象",required = true,dataType = "PageUtil")
    })
    @GetMapping("/getRoleInfoList")
    public ResponseResult getRoleInfoList(RoleInfo roleInfo, PageUtil pageUtil){
        ResponseResult responseResult = null;
        try {
            IPage<RoleInfo> iPage = roleInfoService.getRoleInfoList(roleInfo,pageUtil);
            log.info("获取角色信息列表成功");
            responseResult = ResponseResult.success(ResponseResultEnum.SUCCESS.getCode(),iPage,"获取角色信息列表成功");
        } catch(Exception e){
            log.error("获取角色信息列表失败",e);
            responseResult = ResponseResult.failure("获取角色信息列表失败");
        }
        return responseResult;
    }

    /**
     * @Description : 添加角色信息
     * @methodName : addRoleInfo
     * @param roleInfo :
     * @param permIds :
     * @return : com.lfxwkj.storage.common.ResponseResult
     * @exception :
     * @author : aaa
     */
    @PostMapping("/addRoleInfo")
    public ResponseResult addRoleInfo(RoleInfo roleInfo, String permIds) {
        ResponseResult responseResult = null;
        try {
            boolean b = roleInfoService.addRoleInfo(roleInfo, permIds);
            if (b){
                log.info("添加角色信息成功");
                responseResult = ResponseResult.success(ResponseResultEnum.SUCCESS.getCode(),null,"添加角色信息成功");
            }else {
                log.error("角色名称重复，添加角色信息失败");
                responseResult = ResponseResult.failure("角色名称重复，添加角色信息失败");
            }
        } catch(Exception e){
            log.error("添加角色信息失败",e);
            responseResult = ResponseResult.failure("添加角色信息失败");
        }
        return responseResult;
    }

    /**
     * @Description : 修改角色信息
     * @methodName : updateRoleInfo
     * @param roleInfo :
     * @param permIds :
     * @return : com.lfxwkj.storage.common.ResponseResult
     * @exception :
     * @author : aaa
     */
    @PutMapping("/updateRoleInfo")
    public ResponseResult updateRoleInfo(RoleInfo roleInfo, String permIds) {
        ResponseResult responseResult = null;
        try {
            roleInfoService.updateRoleInfo(roleInfo,permIds);
            log.info("修改角色信息成功");
            responseResult = ResponseResult.success(ResponseResultEnum.SUCCESS.getCode(),null,"修改角色信息成功");
        } catch(Exception e){
            log.error("修改角色信息失败",e);
            responseResult = ResponseResult.failure("修改角色信息失败");
        }
        return responseResult;
    }

    /**
     * @Description : 删除角色信息
     * @methodName : deleteRoleInfo
     * @param id :
     * @return : com.lfxwkj.storage.common.ResponseResult
     * @exception :
     * @author : aaa
     */
    @DeleteMapping("/deleteRoleInfo/{id}")
    public ResponseResult deleteRoleInfo(@PathVariable("id") Long id) {
        ResponseResult responseResult = null;
        try {
            roleInfoService.deleteRoleInfo(id);
            log.info("删除角色信息成功");
            responseResult = ResponseResult.success(ResponseResultEnum.SUCCESS.getCode(),null,"删除角色信息成功");
        } catch(Exception e){
            log.error("删除角色信息失败",e);
            responseResult = ResponseResult.failure("删除角色信息失败");
        }
        return responseResult;
    }

}
