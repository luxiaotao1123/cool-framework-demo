package com.cool.demo.common.web;

import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.BaseRes;
import com.core.controller.AbstractBaseController;
import com.core.exception.CoolException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vincent on 2019-09-09
 */
public class BaseController extends AbstractBaseController {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    private UserService userService;

    protected Long getUserId(){
        return Long.parseLong(String.valueOf(request.getAttribute("userId")));
    }

    protected User getUser(){
        User user = userService.selectById(getUserId());
        if (null == user) {
            throw new CoolException(BaseRes.DENIED);
        }
        return user;
    }
}
