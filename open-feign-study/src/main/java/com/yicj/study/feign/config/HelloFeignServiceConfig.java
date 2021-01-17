package com.yicj.study.feign.config;

import com.yicj.study.feign.anno.AvoidScan;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AvoidScan
@Configuration
public class HelloFeignServiceConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
