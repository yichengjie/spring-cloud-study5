package com.yicj.study.clienta.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    @GetMapping("/v1")
    public String v1(){
        return "v1" ;
    }

    @GetMapping("/v2")
    public String v2(){
        return "v2" ;
    }
}
