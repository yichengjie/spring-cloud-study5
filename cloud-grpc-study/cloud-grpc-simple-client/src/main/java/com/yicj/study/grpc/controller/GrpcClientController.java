package com.yicj.study.grpc.controller;

import com.yicj.study.grpc.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrpcClientController {

    @Autowired
    private GrpcClientService grpcClientService ;

    @RequestMapping("/printMessage")
    public String printMessage(@RequestParam(defaultValue = "Spring Cloud") String name){
        return grpcClientService.sendMessage(name) ;
    }
}
