package com.yicj.study.clienta.controller;

import com.yicj.study.clienta.model.vo.UserInfoVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello world" ;
    }

    @GetMapping("/xxx")
    public String xxx(){
        return "hello xxx" ;
    }

    @PostMapping("/submitForm")
    public UserInfoVo submitForm(UserInfoVo userInfoVo){
        userInfoVo.setUsername(userInfoVo.getUsername() +" : OK");
        return userInfoVo ;
    }
}
