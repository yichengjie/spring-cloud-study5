package com.yicj.study.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

@Component
public class MyRewritePathGatewayFilterFactory extends AbstractGatewayFilterFactory<RewritePathGatewayFilterFactory.Config> {

    /**
     * Regexp key.
     */
    public static final String REGEXP_KEY = "regexp";

    /**
     * Replacement key.
     */
    public static final String REPLACEMENT_KEY = "replacement";

    public MyRewritePathGatewayFilterFactory() {
        super(RewritePathGatewayFilterFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(REGEXP_KEY, REPLACEMENT_KEY);
    }


    @Override
    public GatewayFilter apply(RewritePathGatewayFilterFactory.Config config) {
        String replacement = config.getReplacement().replace("$\\", "$");

        return (exchange, chain) ->{
            ServerHttpRequest req = exchange.getRequest();
            addOriginalRequestUrl(exchange, req.getURI());
            String path = req.getURI().getRawPath();
            String newPath = path.replaceAll(config.getRegexp(), replacement);

            UriComponents uriComponents = UriComponentsBuilder.fromPath(newPath).build();

            ServerHttpRequest request = req.mutate().uri(uriComponents.toUri()).build();

            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());

            return chain.filter(exchange.mutate().request(request).build());
        };
    }
}
