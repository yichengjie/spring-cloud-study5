package com.yicj.study.namedcontextfactory2.config;

import com.yicj.study.namedcontextfactory2.anno.AvoidScan;
import com.yicj.study.namedcontextfactory2.service.IHelloContext;
import com.yicj.study.namedcontextfactory2.service.impl.MyContextBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AvoidScan
@Configuration
@EnableConfigurationProperties
public class Hello1ContextAutoConfiguration {

    private String client = "test1";

    @Value("${helloContext.name:hello}")
    private String name;

    @Bean
    @ConditionalOnMissingBean
    public IHelloContext getTestContext() {
        return new MyContextBean(client+":"+name);
    }
}