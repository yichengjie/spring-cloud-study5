package com.yicj.study.hystrix.service;

import com.yicj.study.hystrix.service.fallback.UserFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Primary
@FeignClient(name = "client-a", fallback = UserFeignServiceFallback.class)
public interface UserFeignService {

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    String getUser(@RequestParam("username") String username) ;
}
