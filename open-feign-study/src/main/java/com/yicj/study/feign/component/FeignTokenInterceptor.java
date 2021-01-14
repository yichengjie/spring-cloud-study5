package com.yicj.study.feign.component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Component
public class FeignTokenInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        if (request == null){
            return;
        }
        // 将token对应值往下传递
        String xtoken = request.getHeader("x-token");
        if (xtoken != null){
            template.header("x-token", xtoken) ;
        }
    }
}
