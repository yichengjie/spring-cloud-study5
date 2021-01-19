package com.yicj.study.namedcontextfactory2.config;

import com.yicj.study.namedcontextfactory2.anno.AvoidScan;
import com.yicj.study.namedcontextfactory2.service.impl.MyShowContextBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AvoidScan
@Configuration
@EnableConfigurationProperties
public class CommonContextAutoConfiguration {
    private String client = "common";
    @Value("${helloContext.name:hello}")
    private String name;

    @Bean
    @ConditionalOnMissingBean
    public MyShowContextBean getCommonContext() {
        return new MyShowContextBean(client + ":" + name);
    }
}