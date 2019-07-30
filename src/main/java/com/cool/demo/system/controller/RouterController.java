package com.cool.demo.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by vincent on 2019-07-30
 */
@Controller
public class RouterController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/")
    public String login(){
        return "login";
    }

    @RequestMapping("/demo")
    public String demo(){
        return "demo";
    }

    @RequestMapping("/index1")
    public String index1(){
        return "index1";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

}
