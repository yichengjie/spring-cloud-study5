package com.yicj.study.gateway.filter;

import com.yicj.study.gateway.exception.MicroserviceException;
import com.yicj.study.gateway.model.BaseRespVo;
import com.yicj.study.gateway.utils.JsonUtil;
import com.yicj.study.gateway.utils.ResponseUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


/**
 * 3. RequestAuthFilter
 * 另外我们还可以通过GlobalFilter实现请求过滤，OAUTH授权，相关代码如下：
 * 请求方式验证过滤器（RequestAuthFilter）：
 */
public class RequestAuthFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String method = serverHttpRequest.getMethodValue();
        if (!"POST".equals(method)) {
            ServerHttpResponse response = exchange.getResponse();
            BaseRespVo baseRespVo = ResponseUtils.responseMsg(MicroserviceException.ERR_100008, "非法请求", null);
            byte[] bits = JsonUtil.toJson(baseRespVo).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //指定编码，否则在浏览器中会中文乱码
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange);
    }
}
