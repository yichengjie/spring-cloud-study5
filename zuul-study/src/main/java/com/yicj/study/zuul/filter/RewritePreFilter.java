package com.yicj.study.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

public class RewritePreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    // 测试发现这里重写地址无效，SimpleHostRoutingFilter直接从中获取地址RequestContext.getCurrentContext().getRouteHost();
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = "http://www.baidu.com" ;
        ctx.setRouteHost(getUrl(url));
        return null;
    }

    private URL getUrl(String target) {
        try {
            return new URL(target);
        }
        catch (MalformedURLException ex) {
            throw new IllegalStateException("Target URL is malformed", ex);
        }
    }
}
