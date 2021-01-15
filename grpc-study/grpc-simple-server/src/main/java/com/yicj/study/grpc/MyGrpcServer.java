package com.yicj.study.grpc;

import com.yicj.study.grpc.service.HelloService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyGrpcServer {
    public static void main(String[] args) throws Exception{
        Server server = ServerBuilder.forPort(8082).addService(new HelloService()).build();
        log.info("Starting server ...");
        server.start() ;
        log.info("Server started");
        server.awaitTermination();
    }
}
