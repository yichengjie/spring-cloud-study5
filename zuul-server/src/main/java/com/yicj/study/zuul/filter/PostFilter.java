package com.yicj.study.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

// 主要用于检测有无定制ResponseBody，以及设置响应字符集，避免中文乱码,此外还设定了Http状态码
@Slf4j
public class PostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("这是PostFilter");
        RequestContext ctx = RequestContext.getCurrentContext();
        // 处理返回中文乱码
        ctx.getResponse().setCharacterEncoding("UTF-8");
        // 获取上下文中保存的responseBody
        String responseBody = ctx.getResponseBody();
        // 如果responseBody不为空，则说明流程有异常发生
        if (responseBody != null){
            // 设置固定状态码
            ctx.setResponseStatusCode(500);
            // 替换响应报文
            ctx.setResponseBody(responseBody);
        }
        return null;
    }
}
