package com.yicj.study.ribbon.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.yicj.study.ribbon.anno.AvoidScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AvoidScan
@Configuration
public class RibbonRuleConfig {
    // 全局配置负载均衡策略
    @Bean
    public IRule ribbonRule(){
        return new RandomRule() ;
    }
}
