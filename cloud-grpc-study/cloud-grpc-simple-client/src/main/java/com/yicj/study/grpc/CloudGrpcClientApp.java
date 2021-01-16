package com.yicj.study.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CloudGrpcClientApp {

    public static void main(String[] args) {

        SpringApplication.run(CloudGrpcClientApp.class, args) ;
    }
}
