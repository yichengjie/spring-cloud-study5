package com.yicj.study.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Slf4j
public class SecondPreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("这是SecondPreFilter ！");
        RequestContext ctx = RequestContext.getCurrentContext();
        // 从上下文中获取a参数
        HttpServletRequest request = ctx.getRequest();
        String a = request.getParameter("a");
        // 如果a参数为空则进
        if (a == null){
            // 对该请求禁止路由，也即是禁止访问下游服务
            ctx.setSendZuulResponse(false);
            // 设置responseBody供PostFilter使用
            //{"status":"500","message":"a参数为空！"}
            ctx.setResponseBody("{\"status\":\"500\",\"message\":\"a参数为空！\"}");
            // logic-is-success保存于上下文，作为同类型下游Filter的执行开关
            ctx.set("logic-is-success", false);
            // 到这里此Filter逻辑结束
            return null ;
        }
        // 设置避免空指针
        ctx.set("logic-is-success", true);
        return null ;
    }
}
