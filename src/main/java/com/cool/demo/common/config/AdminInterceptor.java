package com.cool.demo.common.config;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cool.demo.common.utils.Http;
import com.cool.demo.system.entity.OperateLog;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.entity.UserLogin;
import com.cool.demo.system.service.OperateLogService;
import com.cool.demo.system.service.UserLoginService;
import com.cool.demo.system.service.UserService;
import com.core.common.BaseRes;
import com.core.common.Cools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vincent on 2019-06-13
 */
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private OperateLogService operateLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) {
            return true;
        }
        // 跨域设置
        // response.setHeader("Access-Control-Allow-Origin", "*");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return check(request, response, handlerMethod);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        Object obj = request.getAttribute("operateLog");
        if (obj instanceof OperateLog) {
            OperateLog operate = (OperateLog) obj;
            operate.setResponse(String.valueOf(response.getStatus()));
            operateLogService.insert(operate);
        }
    }

    private boolean check(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws IOException, ServletException {
        try {
            String token = request.getHeader("token");
            UserLogin userLogin = userLoginService.selectOne(new EntityWrapper<UserLogin>().eq("token", token));
            if (null == userLogin){
                Http.response(response, BaseRes.DENIED);
                return false;
            }
            User user = userService.selectById(userLogin.getUserId());
            String deToken = Cools.deTokn(token, user.getPassword());
            long timestamp = Long.parseLong(deToken.substring(0, 13));
            // 1天后过期
            if (System.currentTimeMillis() - timestamp > 86400000){
                Http.response(response, BaseRes.DENIED);
                return false;
            }
            // 操作日志
            OperateLog operateLog = new OperateLog();
            operateLog.setAction(request.getRequestURI());
            operateLog.setIp(request.getRemoteAddr());
            operateLog.setUserId(user.getId());
            operateLog.setRequest(JSON.toJSONString(request.getParameterMap()));
            // 请求缓存
            request.setAttribute("userId", user.getId());
            request.setAttribute("operateLog", operateLog);
            return true;
        } catch (Exception e){
            Http.response(response, BaseRes.DENIED);
            return false;
        }

    }

}
