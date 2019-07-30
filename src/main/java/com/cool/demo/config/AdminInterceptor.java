package com.cool.demo.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.entity.UserLogin;
import com.cool.demo.system.service.UserLoginService;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
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
    private UserService userService;
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
        User user = userService.selectById(userLogin.getUserId());
        String deToken = Cools.deTokn(token, user.getPassword());
        long timestamp = Long.parseLong(deToken.substring(0, 13));
        // 5分钟过期
        if (System.currentTimeMillis() - timestamp > 300000){
            return false;
        }
        return true;
    }

}
