package com.gyh.cloudzuul.filter;

import com.gyh.internalcommon.constant.RedisKeyPrefixConstant;
import com.gyh.internalcommon.util.JwtInfo;
import com.gyh.internalcommon.util.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gyh
 * */
@Component
@Slf4j
public class AuthFilter2 extends ZuulFilter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 	该过滤器是否生效
     */
    @Override
    public boolean shouldFilter() {
        //获取上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        if (!requestContext.sendZuulResponse()){
            return false;
        }

//		boolean ifContinue = (boolean) requestContext.get("ifContinue");
//		if (ifContinue){
//			return true;
//		}else {
//			return false;
//		}

        return true;
    }

    /**
     * 	拦截后的具体业务逻辑
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("auth2 拦截");
        return null;
    }
    /**
     * 拦截类型，4中类型。
     */
    @Override
    public String filterType() {
        // TODO Auto-generated method stub
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 	值越小，越在前
     */
    @Override
    public int filterOrder() {
        // TODO Auto-generated method stub
        return 10;
    }
}
