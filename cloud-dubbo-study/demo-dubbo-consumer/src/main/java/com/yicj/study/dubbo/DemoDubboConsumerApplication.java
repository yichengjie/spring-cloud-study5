package com.yicj.study.dubbo;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class DemoDubboConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDubboConsumerApplication.class, args);
	}

}