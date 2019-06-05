package com.cool.demo.controller;

import com.cool.demo.entity.User;
import com.cool.demo.service.UserService;
import com.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        User user = new User();
        user.setId("585849853892886528");
        user.setAge(18811);
//        user.setName("陆晓涛");
        long save = userService.save(user);
        return String.valueOf(save);
    }

	
}
