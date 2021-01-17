package com.yicj.study.feign;

import com.yicj.study.feign.anno.AvoidScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@EnableFeignClients
@SpringBootApplication
@ComponentScan(excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {AvoidScan.class})
})
public class OpenFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenFeignApplication.class, args) ;
    }
}
