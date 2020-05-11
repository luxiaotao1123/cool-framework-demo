package com.cool.demo.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by vincent on 2019-07-30
 */
@Controller
public class RouterController {

    @RequestMapping("/")
    public void index(HttpServletResponse response) {
        try{
            String redirect = "/views/home/console.html";
            response.sendRedirect(redirect);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @RequestMapping("/login")
    public void login(HttpServletResponse response) {
        try{
            String redirect = "/views/login.html";
            response.sendRedirect(redirect);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
