package com.yicj.study.grpc.service;

import com.yicj.study.grpc.lib.HelloWorldProto;
import com.yicj.study.grpc.lib.SimpleGrpc;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.stereotype.Service;


@Service
public class GrpcClientService {

    @GrpcClient("cloud-grpc-server")
    private Channel serverChannel;

    public String sendMessage(String name){
        SimpleGrpc.SimpleBlockingStub stub = SimpleGrpc.newBlockingStub(serverChannel) ;
        HelloWorldProto.HelloRequest request =
                HelloWorldProto.HelloRequest.newBuilder().setName(name).build();
        HelloWorldProto.HelloReply response = stub.sayHello(request);
        return response.getMessage() ;
    }
}
