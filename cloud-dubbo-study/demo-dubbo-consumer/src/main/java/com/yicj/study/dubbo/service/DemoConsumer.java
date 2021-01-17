package com.yicj.study.dubbo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

@Component
public class DemoConsumer {

    @Reference
    private HelloService helloService;

    public String callHello(){
        return helloService.hello();
    }
}