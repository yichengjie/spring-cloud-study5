package com.yicj.study.feign.service;

import com.yicj.study.feign.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "webmvc-api-service", url = "http://localhost:8081")
public interface FeignGetInterceptorFeign {
    // 在controller中通过map传递
    @RequestMapping(value = "/feignGet/addUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity<byte[]> addUser(@RequestParam Map<String, Object> user) ;

    // 通过RequestInterceptor转map解决
    @RequestMapping(value = "/feignGet/addUser",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity<byte[]> addUser2(User user) ;


    /*@RequestMapping(value = "/feignGet/addUser",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity<byte[]> addUser3(@SpringQueryMap User user) ;*/


    @RequestMapping(value = "/feignGet/updateUser",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    ResponseEntity<byte[]> updateUser(@RequestBody User user) ;
}
