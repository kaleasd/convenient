package com.gyh.cloudzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.SneakyThrows;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URL;

/**
 * @author 马士兵教育:晁鹏飞
 * @date
 */
@Component
public class HostFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @SneakyThrows
    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        // 获取 请求url
        String remoteAddr = request.getRequestURI();

        // 和 老地址 做匹配
        if (remoteAddr.contains("/zuul-api-driver")){
            // rmoteAddr => newAddr


            currentContext.setRouteHost(new URI("http://localhost:8003/test/sms-test3").toURL());
        }



        return null;
    }
}
