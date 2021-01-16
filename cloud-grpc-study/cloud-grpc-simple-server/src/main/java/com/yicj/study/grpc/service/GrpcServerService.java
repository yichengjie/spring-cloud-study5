package com.yicj.study.grpc.service;

import com.yicj.study.grpc.lib.HelloWorldProto;
import com.yicj.study.grpc.lib.SimpleGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

@GrpcService(SimpleGrpc.class)
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {
    @Override
    public void sayHello(HelloWorldProto.HelloRequest request, StreamObserver<HelloWorldProto.HelloReply> responseObserver) {
        HelloWorldProto.HelloReply reply= HelloWorldProto.HelloReply.newBuilder()
                .setMessage("Hello ==========> " + request.getName()).build() ;
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
