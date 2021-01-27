package com.yicj.study.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

//4. OAUTH授权过滤器（OAuthSignatureFilter）：
public class OAuthSignatureFilter implements GlobalFilter {

    /**授权访问用户名*/
    @Value("${spring.security.user.name}")
    private String securityUserName;
    /**授权访问密码*/
    @Value("${spring.security.user.password}")
    private String securityUserPassword;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /**oauth授权*/
        String auth = securityUserName.concat(":").concat(securityUserPassword);
        String encodedAuth = new sun.misc.BASE64Encoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + encodedAuth;
        //向headers中放授权信息
        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().header("Authorization", authHeader)
                .build();
        //将现在的request变成change对象
        ServerWebExchange build = exchange.mutate().request(serverHttpRequest).build();
        return chain.filter(build);
    }

}
