package com.yicj.study.beanpostprocessor;

import io.grpc.ClientInterceptor;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GrpcClient {
    String value();
    Class<? extends ClientInterceptor>[] interceptors() default {};
}