package com.cool.demo.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cool.demo.system.entity.UserLogin;
import com.cool.demo.system.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vincent on 2019-06-13
 */
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserLoginService userLoginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return check(request, response, handlerMethod);
    }

    private boolean check(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {
        String token = request.getHeader("token");
        UserLogin userLogin = userLoginService.selectOne(new EntityWrapper<UserLogin>().eq("token", token));
        if (null == userLogin){
            return false;
        }
        return true;
    }

}
