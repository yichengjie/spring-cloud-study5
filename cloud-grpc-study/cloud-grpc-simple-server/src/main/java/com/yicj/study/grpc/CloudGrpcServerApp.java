package com.yicj.study.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CloudGrpcServerApp {
    public static void main(String[] args) {

        SpringApplication.run(CloudGrpcServerApp.class, args) ;
    }
}
