package com.test.activiti.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.test.activiti.mapper")
@Configuration
public class MapperConfig {
}
