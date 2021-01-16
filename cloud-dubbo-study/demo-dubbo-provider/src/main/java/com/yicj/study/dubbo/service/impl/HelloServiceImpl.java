package com.yicj.study.dubbo.service.impl;

import com.yicj.study.dubbo.service.HelloService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello() {
        return "Hello at " + System.currentTimeMillis();
    }
}
