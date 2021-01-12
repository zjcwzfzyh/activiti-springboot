package com.test.activiti.controller;

import com.test.activiti.common.ResponseResult;
import com.test.activiti.common.ResponseResultEnum;
import com.test.activiti.entity.UserInfo;
import com.test.activiti.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class LoginController {



    @GetMapping("/login")
    public ModelAndView toLoginPage(){
        return new ModelAndView("login");
    }


    /**
     * @Description : 用户登录
     * @methodName : login
     * @param userInfo : 
     * @return : com.xwkj.cost.common.ResponseResult
     * @exception : 
     * @author : aaa
     */
    @PostMapping("/loginUser")
    public ResponseResult login(UserInfo userInfo){
        ResponseResult responseResult = null;
        try {
            UsernamePasswordToken token = new UsernamePasswordToken();
            token.setUsername(userInfo.getUserName());
            String password = MD5Util.getMD5(userInfo.getPassword());
            token.setPassword(password.toCharArray());
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                subject.login(token);
            }
            log.info("登录成功！");
            responseResult = ResponseResult.success(ResponseResultEnum.SUCCESS.getCode(), null,"登录成功！");
        } catch (IncorrectCredentialsException ice) {
            return ResponseResult.failure("密码错误");
        } catch (UnknownAccountException uae) {
            return ResponseResult.failure("账号不存在");
        } catch (LockedAccountException e) {
            return ResponseResult.failure("账号被锁定");
        } catch (ExcessiveAttemptsException eae) {
            return ResponseResult.failure("操作频繁，请稍后再试");
        }catch(Exception e){
            log.error("登录失败",e);
            responseResult = ResponseResult.failure("登录失败");
        }
        return responseResult;
    }

    /**
     * @Description : 取消登录
     * @methodName : logout
     * @return : org.springframework.web.servlet.ModelAndView
     * @exception : 
     * @author : aaa
     */
    @GetMapping("/logout")
    public ModelAndView logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout();
        }
        return new ModelAndView("redirect:login");
    }


}
