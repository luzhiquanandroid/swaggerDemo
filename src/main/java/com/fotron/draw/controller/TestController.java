package com.fotron.draw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: niuhuan
 * @createDate: 2018/11/27
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@RequestMapping("/api")
@RestController
public class TestController {
    /**
     * 检查服务是否开启
     *
     * @return
     */
    @GetMapping("/test")
    public String test() {
        return "OK";
    }
}
