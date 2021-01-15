package com.yicj.study.hystrix.controller;

import com.yicj.study.hystrix.service.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFeignController {

    @Autowired
    private UserFeignService userFeignService ;

    @GetMapping("/getFeignUser")
    public String getUser(String username){
        return userFeignService.getUser(username) ;
    }
}
