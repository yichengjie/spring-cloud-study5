package com.yicj.study.hystrix.controller;

import com.yicj.study.hystrix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService ;
    @GetMapping("/getUser")
    public String getUser(String username){
        return userService.getUser(username) ;
    }
}
