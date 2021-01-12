package com.test.activiti.config;/*
package com.test.activiti.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


*/
/**
 * @program: storage
 * @description: 图片路径配置类
 * @author: aaa
 * @create: 2020-03-23 11:40
 **//*

@Configuration
public class ImgPathConfig implements WebMvcConfigurer {
    @Value("${img.path}")
    private String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:/"+path+"/");
    }
}
*/
