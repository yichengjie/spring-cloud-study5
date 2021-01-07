package com.yicj.study.gateway.predicate;

import com.yicj.study.gateway.predicate.properties.TimeBetweenConfig;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

// 需要实现两个方法shortcutFieldOrder与apply
@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeBetweenConfig> {
    public TimeBetweenRoutePredicateFactory() {
        super(TimeBetweenConfig.class);
    }

    // 配置配置类和配置文件的映射关系的
    @Override
    public List<String> shortcutFieldOrder() {
        //TimeBetween=9:00, 17:00
        //配置TimeBetweenConfig与predicate工厂配置对应关系
        // 对应的start属性映射 9:00 ,   end属性映射17:00
        return Arrays.asList("start","end");
    }

    @Override
    public Predicate<ServerWebExchange> apply(TimeBetweenConfig config) {
        LocalTime start = config.getStart();
        LocalTime end = config.getEnd();
        return exchange -> {
            LocalTime now = LocalTime.now();
            boolean flag = now.isAfter(start) && now.isBefore(end);
            //return flag ;
            return false ;
        };
    }
}
