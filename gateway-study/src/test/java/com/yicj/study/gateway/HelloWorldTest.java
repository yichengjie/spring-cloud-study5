package com.yicj.study.gateway;

import org.junit.Test;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class HelloWorldTest {

    @Test
    public void rewritePath(){
        String regexp = "/client-a/(?<segment>.*)" ;
        String replacement = "/$\\{segment}".replace("$\\", "$") ;
        String path = "http://localhost:8080/client-a/hello" ;
        String newPath = path.replaceAll(regexp, replacement);
        System.out.println(newPath);
    }

    @Test
    public void expand(){
        String template = "/client-a/{hello}" ;
        Map<String, String> variables = new HashMap<>() ;
        variables.put("hello", "123") ;
        String path = UriComponentsBuilder.fromPath(template).build().expand(variables)
                .getPath();
        System.out.println(path);
    }
}
