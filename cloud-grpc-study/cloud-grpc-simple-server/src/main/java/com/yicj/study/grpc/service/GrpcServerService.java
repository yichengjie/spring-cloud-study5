package com.yicj.study.grpc.service;

import com.yicj.study.grpc.lib.HelloReply;
import com.yicj.study.grpc.lib.HelloRequest;
import com.yicj.study.grpc.lib.SimpleGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

@GrpcService(SimpleGrpc.class)
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply= HelloReply.newBuilder()
                .setMessage("Hello ==========> " + request.getName()).build() ;
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
