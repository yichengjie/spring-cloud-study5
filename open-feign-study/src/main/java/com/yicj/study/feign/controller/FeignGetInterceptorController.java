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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/feignGet")
public class FeignGetInterceptorController {

    @Autowired
    private FeignGetInterceptorFeign feignGetInterceptorFeign ;

    @RequestMapping(
        value = "/addUser",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<byte[]> addUser(@RequestBody User user){
        Map<String,Object> queryParams = new HashMap<>() ;
        queryParams.put("id", user.getId()) ;
        queryParams.put("username", user.getUsername()) ;
        queryParams.put("password", user.getPassword()) ;
        queryParams.put("roles", user.getRoles()) ;
        return feignGetInterceptorFeign.addUser(queryParams) ;
    }


    @RequestMapping(
            value = "/addUser2",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<byte[]> addUser2(@RequestBody User user){
        return feignGetInterceptorFeign.addUser2(user) ;
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
