package com.cool.demo.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by vincent on 2019-07-30
 */
@Controller
public class RouterController {

    @RequestMapping(value = {"/","/index"})
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/404")
    public String error404(){
        return "404";
    }

    @RequestMapping("/password")
    public String changePassword(){
        return "password";
    }

    @RequestMapping("/detail")
    public String userDetail(){
        return "detail";
    }

    @RequestMapping("permission/test/auth")
    @ResponseBody
    public String permissionTest(){
        return "Success";
    }
}
