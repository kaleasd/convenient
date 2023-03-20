package com.gyh.cloudzuul.filter;

import com.gyh.cloudzuul.dao.CommonGrayRuleDaoCustom;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class GrayFilter extends ZuulFilter {


    @Override
    public String filterType() {
        // 路由
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Autowired
    private CommonGrayRuleDaoCustom commonGrayRuleDaoCustom;

    //在run中写业务逻辑
    @Override
    public Object run() throws ZuulException {
        // 查库
        // commonGrayRuleDaoCustom.selectByUserId();
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        int userId = Integer.parseInt(request.getHeader("userId"));
        // 根据用户ID查规则，库存
        // 金丝雀
        if (userId == 1) {
            RibbonFilterContextHolder.getCurrentContext().add("version","v1");
        }
        // 普通用户
        else if (userId == 2) {
            RibbonFilterContextHolder.getCurrentContext().add("version", "v2");
        }
        return null;
    }
}
