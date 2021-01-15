package com.yicj.study.grpc.service;

import com.yicj.study.grpc.model.HelloRequest;
import com.yicj.study.grpc.model.HelloResponse;
import com.yicj.study.grpc.model.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        log.info("request : {}", request);
        String greeting = "Hi " + request.getName() + " you are " + request.getAge() +" years old"
                + " your hoby is " + request.getHobbiesList() + " your tags " + request.getTagsMap() ;
        HelloResponse response = HelloResponse.newBuilder().setGreeting(greeting).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
