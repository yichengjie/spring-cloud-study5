package com.yicj.study.feign.service;

import com.yicj.study.feign.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "demo-goods", url = "http://localhost:8081")
public interface FeignGetInterceptorFeign {
    @RequestMapping(value = "/feignGet/addUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity<byte[]> addUser(User user) ;


    @RequestMapping(value = "/feignGet/updateUser",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity<byte[]> updateUser(@RequestBody User user) ;
}
