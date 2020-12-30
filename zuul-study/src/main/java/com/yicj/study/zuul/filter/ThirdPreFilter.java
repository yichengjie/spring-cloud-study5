package com.yicj.study.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Slf4j
public class ThirdPreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        // 从上下文中获取logic-is-success值，用于判断此Filter是否执行
        return (boolean)ctx.get("logic-is-success");
    }

    @Override
    public Object run() throws ZuulException {
        log.info("这是ThirdPreFilter ！");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String b = request.getParameter("b");
        if (b == null){
            // 对该请求禁止路由，也就是禁止访问下游服务
            ctx.setSendZuulResponse(false);
            //设定responseBody供PostFilter使用
            //{"status":"500", "message":"b参数为空!"}
            ctx.setResponseBody("{\"status\":\"500\", \"message\":\"b参数为空!\"}");
            // 到这里此Filter逻辑结束
            return null ;
        }
        return null;
    }
}
