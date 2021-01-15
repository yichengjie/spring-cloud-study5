package com.yicj.study.proto.model;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class PersonModelTest {

    @Test
    public void hello() throws InvalidProtocolBufferException {
        PersonModel.Person forezp = PersonModel.Person.newBuilder()
                .setId(1)
                .setName("forezp")
                .setEmail("abc@qq.com").build() ;

        for (byte b : forezp.toByteArray()){
            System.out.print(b);
        }

        System.out.println("====================> " + forezp.toByteString());
        System.out.println();
        log.info("\n" + "bytes长度" + forezp.toByteString().size());
        log.info("========forezp Byte 结束==========");
        log.info("========forezp 反序列化生成对象开始======");
        PersonModel.Person forezpCopy = PersonModel.Person.parseFrom(forezp.toByteArray()) ;
        log.info(forezpCopy.toString());
        log.info("====== forezp 反系列化生成对象结束=======");



    }

}
