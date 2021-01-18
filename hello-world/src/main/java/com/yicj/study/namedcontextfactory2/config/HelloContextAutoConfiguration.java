package com.yicj.study.namedcontextfactory2.config;

import com.yicj.study.namedcontextfactory2.service.HelloBean;
import com.yicj.study.namedcontextfactory2.service.IHelloContext;
import com.yicj.study.namedcontextfactory2.service.impl.MyContextBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class HelloContextAutoConfiguration {
    private String client = "test0";
    @Value("${testcontext.name:hello}")
    private String name;

    @Bean
    @ConditionalOnMissingBean
    public IHelloContext getTestContext(HelloBean testBean) {
        System.out.println(testBean.getName());
        return new MyContextBean(client + ":" + name);
    }
}