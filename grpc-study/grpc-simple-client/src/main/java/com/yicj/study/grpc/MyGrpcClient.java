package com.yicj.study.grpc;

import com.yicj.study.grpc.model.HelloRequest;
import com.yicj.study.grpc.model.HelloResponse;
import com.yicj.study.grpc.model.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyGrpcClient {

    public static void main(String[] args) throws Exception{
        ManagedChannel channel =
                ManagedChannelBuilder.forAddress("localhost", 8082).usePlaintext().build() ;
        HelloServiceGrpc.HelloServiceBlockingStub stub =
                HelloServiceGrpc.newBlockingStub(channel) ;

        HelloRequest request = HelloRequest.newBuilder()
                .setName("forezp").setAge(17).addHobbies("football")
                .putTags("how?", "wonderful").build();

        HelloResponse response = stub.hello(request);
        log.info("response : {}",response);
        channel.shutdown() ;
    }
}
