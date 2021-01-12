package com.test.activiti.controller;

import com.test.activiti.common.ResponseResult;
import com.test.activiti.common.ResponseResultEnum;
import com.test.activiti.entity.Dictionary;
import com.test.activiti.service.IDictionaryService;
import com.test.activiti.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* <p>
* 系统数据字典表 控制层
* </p>
*
* @author aaa
* @since 2020-05-07
*/
@Slf4j
@RestController
@Api(tags = "系统数据字典表接口")
public class DictionaryController {

    @Autowired
    private IDictionaryService dictionaryService;

    /**
     * @Description : 获取数据字典列表信息
     * @methodName : getDictionaryList
     * @param dictionary :
     * @param pageUtil :
     * @return : com.lfxwkj.storage.common.ResponseResult
     * @exception :
     * @author : aaa
     */
    @GetMapping("/getDictionaryList")
    public ResponseResult getDictionaryList(Dictionary dictionary, PageUtil pageUtil){
        ResponseResult responseResult = null;
        try {
            IPage<Dictionary> iPage = new Page<>(pageUtil.getPage(),pageUtil.getLimit());
            QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dic_parentid",dictionary.getDicParentid());
            queryWrapper.ne("dic_status",0);
            if (dictionary.getDicName() != null && !"".equals(dictionary.getDicName())){
                queryWrapper.like("dic_name",dictionary.getDicName());
            }
            iPage = dictionaryService.page(iPage,queryWrapper);
            log.info("获取数据字典列表信息成功！");
            responseResult = ResponseResult.success(ResponseResultEnum.SUCCESS.getCode(),iPage,"获取数据字典列表信息成功！");
        } catch(Exception e){
            log.error("获取数据字典列表信息失败！",e);
            responseResult = ResponseResult.failure("获取数据字典列表信息失败！");
        }
        return responseResult;
    }

    /**
     * @Description : 添加数据字典信息
     * @methodName : addDictionary
     * @param dictionary :
     * @return : com.lfxwkj.storage.common.ResponseResult
     * @exception :
     * @author : aaa
     */
    @PostMapping("/addDictionary")
    public ResponseResult addDictionary(Dictionary dictionary) {
        ResponseResult responseResult = null;
        try {
            dictionaryService.save(dictionary);
            log.info("添加数据字典成功！");
            responseResult = ResponseResult.success(ResponseResultEnum.SUCCESS.getCode(),null,"添加成功！");
        } catch(Exception e){
            log.error("添加数据字典失败！",e);
            responseResult = ResponseResult.failure("添加失败！");
        }
        return responseResult;
    }

    /**
     * @Description : 修改数据字典信息
     * @methodName : updateDictionary
     * @param dictionary :
     * @return : com.lfxwkj.storage.common.ResponseResult
     * @exception :
     * @author : aaa
     */
    @PutMapping("/updateDictionary")
    public ResponseResult updateDictionary(Dictionary dictionary) {
        ResponseResult responseResult = null;
        try {
            dictionaryService.updateById(dictionary);
            log.info("修改数据字典成功！");
            responseResult = ResponseResult.success(ResponseResultEnum.SUCCESS.getCode(),null,"修改成功！");
        } catch(Exception e){
            log.error("修改数据字典失败！",e);
            responseResult = ResponseResult.failure("修改失败！");
        }
        return responseResult;
    }


    /**
     * @Description : 删除数据字典信息
     * @methodName : deleteDictionary
     * @param id :
     * @return : com.lfxwkj.storage.common.ResponseResult
     * @exception :
     * @author : aaa
     */
    @DeleteMapping("/deleteDictionary/{id}")
    public ResponseResult deleteDictionary(@PathVariable("id") Long id){
        ResponseResult responseResult = null;
        try {
            Dictionary dictionary = new Dictionary();
            dictionary.setId(id).setDicStatus(0);
            dictionaryService.updateById(dictionary);
            log.info("删除数据字典成功！");
            responseResult = ResponseResult.success(ResponseResultEnum.SUCCESS.getCode(),null,"删除成功！");
        } catch(Exception e){
            log.error("删除数据字典失败！",e);
            responseResult = ResponseResult.failure("删除失败！");
        }
        return responseResult;
    }

    /**
     * @Description : 获取数据字典下拉菜单列表
     * @methodName : getDicList
     * @param parentId :
     * @return : com.lfxwkj.storage.common.ResponseResult
     * @exception :
     * @author : aaa
     */
    @GetMapping("/getDicList/{parentId}")
    public ResponseResult getDicList(@PathVariable("parentId") Long parentId){
        ResponseResult responseResult = null;
        try {
            QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dic_parentid",parentId);
            queryWrapper.eq("dic_status",1);
            List<Dictionary> list = dictionaryService.list(queryWrapper);
            log.info("获取数据字典下拉菜单列表成功");
            responseResult = ResponseResult.success(ResponseResultEnum.SUCCESS.getCode(),list,"获取数据字典下拉菜单列表成功");
        } catch(Exception e){
            log.error("获取数据字典下拉菜单列表失败！",e);
            responseResult = ResponseResult.failure("获取数据字典下拉菜单列表失败！");
        }
        return responseResult;
    }

}
