package com.yicj.study.gateway.filter;

import com.yicj.study.gateway.component.DefaultServerRequest;
import com.yicj.study.gateway.component.RedisClientTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * 1.Requestfilter
 * 我们采用Gateway网关的Gobalfilter，建立我们的第一个过滤器过滤所有请求。
 * 1).通过Spring 5 的 WebFlux我们使用bodyToMono方法把响应内容转换成类 String的对象，最终得到的结果是 Mono对象
 * 2).bodyToMono方法我们可以拿到完整的body内容，并返回String。
 * 3).我们生成唯一的token（通过UUID），并将token放入请求的header中。
 * 4).将获取到的完整body内容，存放到redis中。
 */
@Slf4j
@Component
public class RequestFilter implements GlobalFilter, Ordered {

	@Autowired
	private RedisClientTemplate redisClientTemplate;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		DefaultServerRequest req = new DefaultServerRequest( exchange );
		String token = UUID.randomUUID().toString();
		//向headers中放入token信息
		ServerHttpRequest serverHttpRequest =exchange.getRequest().mutate().header("token", token)
				.build();
		//将现在的request变成change对象
		ServerWebExchange build = exchange.mutate().request( serverHttpRequest ).build();
		return req.bodyToMono( String.class ).map( str -> {
			redisClientTemplate.setObjex( "microservice:gateway:".concat( token ), 180, str );
			return str;
		} ).then( chain.filter( build ) );
	}

	@Override
	public int getOrder() {
		return 0;
	}
}