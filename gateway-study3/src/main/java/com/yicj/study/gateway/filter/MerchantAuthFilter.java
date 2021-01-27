package com.yicj.study.gateway.filter;

import com.yicj.study.gateway.component.RedisClientTemplate;
import com.yicj.study.gateway.exception.MicroserviceException;
import com.yicj.study.gateway.exception.MicroserviceServiceException;
import com.yicj.study.gateway.model.BaseReqVo;
import com.yicj.study.gateway.model.BaseRespVo;
import com.yicj.study.gateway.service.MerchantAuthService;
import com.yicj.study.gateway.utils.JsonUtil;
import com.yicj.study.gateway.utils.MicroserviceConstantParamUtils;
import com.yicj.study.gateway.utils.ResponseUtils;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * 2.MerchantAuthFilter
 * 建立商户认证过滤器，相关代码如下：
 * 1).获取存储在headers中的token。
 * 2).通过token获取我们存储在redis中的body内容(WebFlux 中不能使用阻塞的操作，目前想到的是通过这种方式实现)。
 * 3).获取到完整的body内容后我们就可以进行相应的商户认证操作。
 * 4).认证通过，将信息重新写入，不通过则返回异常信息。
 */
@Slf4j
public class MerchantAuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private MerchantAuthService merchantAuthService ;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /** 验证商户是否有权限访问 */
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String token = serverHttpRequest.getHeaders().get( "token" ).get( 0 );
        String bodyStr =  (String) redisClientTemplate.getObj("microservice:gateway:".concat(token));
        BaseReqVo baseReqVo = JsonUtil.fromJson( bodyStr, BaseReqVo.class );
        try {
            // 商户认证
            BaseRespVo<?> baseRespVo = merchantAuthService.checkMerchantAuth( baseReqVo );
            if (MicroserviceConstantParamUtils.RESULT_CODE_SUCC.equals( baseRespVo.getCode() )) {
                // 若验证成功，将信息重新写入避免request信息消费后后续无法从request获取信息的问题
                URI uri = serverHttpRequest.getURI();
                ServerHttpRequest request = serverHttpRequest.mutate().uri(uri).build();
                DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
                Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
                request = new ServerHttpRequestDecorator(request) {
                    @Override
                    public Flux<DataBuffer> getBody() {
                        return bodyFlux;
                    }
                };
                // 封装request，传给下一级
                return chain.filter(exchange.mutate().request(request).build());
            } else {
                // 若验证不成功，返回提示信息
                return gatewayResponse( baseRespVo.getCode(), baseRespVo.getMessage(), exchange );
            }
        } catch (MicroserviceServiceException ex) {
            // 若验证不成功，返回提示信息
            log.error( "商户访问权限验证异常，异常代码:{},异常信息:{}, 异常{}", ex.getCode(), ex.getMessage(), ex );
            return gatewayResponse( ex.getCode(), ex.getMessage(), exchange );
        } catch (Exception ex) {
            log.error( "商户访问权限验证服务异常:{}", ex.getMessage() );
            return gatewayResponse( MicroserviceException.ERR_100000, "系统异常", exchange );
        } finally {
            redisClientTemplate.del( "microservice:gateway:".concat( token ) );
        }
    }
    /**数据流处理方法*/
    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes( StandardCharsets.UTF_8 );
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory( ByteBufAllocator.DEFAULT );
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer( bytes.length );
        buffer.write( bytes );
        return buffer;
    }

    /**网关请求响应*/
    private Mono<Void> gatewayResponse(String code, String message, ServerWebExchange exchange) {
        // 若验证不成功，返回提示信息
        ServerHttpResponse response = exchange.getResponse();
        BaseRespVo baseRespVo = ResponseUtils.responseMsg( code, message, null );
        byte[] bits = JsonUtil.toJson( baseRespVo ).getBytes( StandardCharsets.UTF_8 );
        DataBuffer buffer = response.bufferFactory().wrap( bits );
        response.setStatusCode( HttpStatus.UNAUTHORIZED );
        // 指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add( "Content-Type", "text/plain;charset=UTF-8" );
        return response.writeWith( Mono.just( buffer ) );
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
