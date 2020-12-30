package com.yicj.study.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;

public class ModifyRequestEntityFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 放在FormBodyWrapperFilter之后即可,这里放在pre类型filter最后一个
        return PRE_DECORATION_FILTER_ORDER +1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        request.getParameterMap();
        Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
        if (requestQueryParams == null){
            requestQueryParams = new HashMap<>() ;
        }
        // 这里添加新增参数value,注意只取list的0位
        List<String> list = new ArrayList<>() ;
        list.add("12345") ;
        requestQueryParams.put("test", list) ;
        ctx.setRequestQueryParams(requestQueryParams);
        ctx.addZuulRequestHeader("xtoken", "mytoken-x");
        return null;
    }
}
