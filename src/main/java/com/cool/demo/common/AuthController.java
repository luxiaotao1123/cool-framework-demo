package com.cool.demo.common;

import com.core.common.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by vincent on 2019-06-11
 */
@Controller
public class AuthController {

    @RequestMapping("/index")
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
            return R.ok("sdadsa");
        }else {
            return R.error("密码错误");
        }
    }

}
