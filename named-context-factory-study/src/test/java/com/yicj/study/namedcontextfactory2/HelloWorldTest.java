package com.yicj.study.namedcontextfactory2;

import org.junit.Test;
import org.springframework.core.env.MapPropertySource;

import java.util.Collections;
import java.util.Map;

public class HelloWorldTest {

    @Test
    public void test2(){
        String propertySourceName = "helloContext" ;
        String propertyName = "helloContext.name" ;
        String name = "test1" ;
        Map<String, Object> stringObjectMap = Collections.singletonMap(propertyName, name);
        MapPropertySource mapPropertySource = new MapPropertySource(propertySourceName, stringObjectMap);
        System.out.println(mapPropertySource);
    }
}
