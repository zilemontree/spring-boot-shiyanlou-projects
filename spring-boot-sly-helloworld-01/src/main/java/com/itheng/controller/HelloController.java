package com.itheng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author PV-CZH mail:zilemontree@yeah.net
 * @description Hello Spring Boot
 * @Date 2020/12/10 16:57
 * @title: HelloController
 * @projectName spring-boot-shiyanlou-projects
 */
@Controller
public class HelloController {
    /**
     * 发送一个hello请求
     * @return
     */
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello,Spring Boot!";
    }
}
