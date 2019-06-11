package com.cool.demo;

import com.core.common.R;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@MapperScan("com.cool.demo.dao")
@Controller
public class Boot {

    public static void main(String[] args) {
        SpringApplication.run(Boot.class, args);
    }

    @RequestMapping("/auth")
    public String index(){
        return "index";
    }

    @RequestMapping("/")
    public String login(){
        return "login";
    }

    @RequestMapping("/login.action")
    @ResponseBody
    public R loginAction(String username, String password){
        if (username.equals("admin") && password.equals("admin")){
            return R.ok("token123");
        }else {
            return R.error();
        }
    }
}
