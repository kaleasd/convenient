package com.gyh.cloudzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 4;
    }
    /**
     * 过滤器是否生效
     * */
    @Override
    public boolean shouldFilter() {
        // 获取上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String uri = request.getRequestURI();
        System.out.println("pre来源URI：" + uri);
        // 只有此接口才会被拦截
        String checkUri = "/api-passenger/api-passenger-gateway-test/hello";
        if (checkUri.equalsIgnoreCase(uri)) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("pre拦截");
        // 获取上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getHeader("token");
        System.out.println("pre 业务逻辑 token：" + token);
        return null;
    }
}
