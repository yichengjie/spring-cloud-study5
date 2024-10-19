package com.yicj.study.ribbon.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CLIENT-A", contextId = "helloFeignClient")
public interface HelloFeignClient {

    @GetMapping("/hello2")
    String hello2(@RequestParam("name")String name) ;
}
