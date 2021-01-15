package com.yicj.study.ribbon;

import com.yicj.study.ribbon.anno.AvoidScan;
import com.yicj.study.ribbon.config.RibbonRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@EnableDiscoveryClient
@SpringBootApplication
// 不自动扫描AvoidScan注解的类
@RibbonClients({
    @RibbonClient(name = "client-a", configuration = RibbonRuleConfig.class)
})
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {AvoidScan.class})})
public class RibbonLoadbalancerApp {
    public static void main(String[] args) {
        SpringApplication.run(RibbonLoadbalancerApp.class, args) ;
    }
}
