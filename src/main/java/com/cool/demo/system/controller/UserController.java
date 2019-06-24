package com.cool.demo.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String index(){
        return "user/user";
    }

    @RequestMapping(value = "/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(userService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer page,
                  @RequestParam(defaultValue = "10")Integer limit){
        return R.ok(userService.selectPage(new Page<>(page, limit)));
    }

    @RequestMapping(value = "/add/auth")
    @ResponseBody
    public R add(User user) {
        userService.insert(user);
        return R.ok();
    }

	@RequestMapping(value = "/update/auth")
    @ResponseBody
    public R update(User user){
        if (Cools.isEmpty(user) || null==user.getId()){
            return R.error();
        }
        userService.updateById(user);
        return R.ok();
    }
}
