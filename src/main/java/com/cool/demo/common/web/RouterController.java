package com.cool.demo.common.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by vincent on 2019-07-30
 */
@Controller
public class RouterController {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @RequestMapping("/")
    public void index(HttpServletResponse response) {
        try{
            response.sendRedirect(contextPath+"/views/index.html");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @RequestMapping("/login")
    public void login(HttpServletResponse response) {
        try{
            response.sendRedirect(contextPath+"/views/login.html");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }



}
