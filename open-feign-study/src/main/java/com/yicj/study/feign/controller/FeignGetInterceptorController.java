package com.yicj.study.feign.controller;

import com.yicj.study.feign.model.User;
import com.yicj.study.feign.service.FeignGetInterceptorFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feignGet")
public class FeignGetInterceptorController {

    @Autowired
    private FeignGetInterceptorFeign feignGetInterceptorFeign ;

    @RequestMapping(
        value = "/addUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<byte[]> addUser(User user){
        return feignGetInterceptorFeign.addUser(user) ;
    }

    @RequestMapping(
        value = "/updateUser",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<byte[]> updateUser(@RequestBody User user){
        return feignGetInterceptorFeign.updateUser(user) ;
    }
}
