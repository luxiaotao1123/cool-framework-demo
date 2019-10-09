package com.cool.demo.common;

import com.core.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vincent on 2019-09-09
 */
public class BaseController extends AbstractBaseController implements CodeRes {

    @Autowired
    protected HttpServletRequest request;

    protected Long getUserId(){
        return Long.parseLong(String.valueOf(request.getAttribute("userId")));
    }

}
