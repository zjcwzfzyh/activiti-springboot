package com.test.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 视图控制层
 * @author aaa
 */
@Controller
public class ViewController {

    @GetMapping("/view")
    public String view(String url){
        return url;
    }

}
