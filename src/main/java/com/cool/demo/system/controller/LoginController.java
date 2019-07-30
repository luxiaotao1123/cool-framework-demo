package com.cool.demo.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vincent on 2019-07-30
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login.action")
    @ResponseBody
    public R loginAction(String username, String password){
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("mobile", username);
        User user = userService.selectOne(wrapper);
        if (Cools.isEmpty(user)){
            return R.error("账号不存在");
        }
        if (user.getStatus()!=1){
            return R.error("账号已被禁用");
        }
        if (!user.getPassword().equals(password)){
            return R.error("密码错误");
        }
        return R.ok();
    }
}
