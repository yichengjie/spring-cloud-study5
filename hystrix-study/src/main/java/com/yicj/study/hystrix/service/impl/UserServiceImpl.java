package com.yicj.study.hystrix.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yicj.study.hystrix.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    @HystrixCommand(fallbackMethod = "defaultUser")
    public String getUser(String username){
        if (username.equalsIgnoreCase("spring")){
            return "this is real user" ;
        }else {
            throw new RuntimeException() ;
        }
    }
    // 出错调用该方法返回预设友好错误
    public String defaultUser(String username){
        return "The user does not exist in this system" ;
    }
}
