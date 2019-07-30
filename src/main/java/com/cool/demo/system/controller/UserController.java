package com.cool.demo.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public String index(){
        return "user/user";
    }

    @RequestMapping(value = "/user/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(userService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/user/list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  User user){
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.setEntity(user);
        return R.ok(userService.selectPage(new Page<>(curr, limit), wrapper));
    }

    @PostMapping(value = "/user/add/auth")
    @ResponseBody
    public R add(User user) {
        userService.insert(user);
        return R.ok();
    }

	@RequestMapping(value = "/user/update/auth")
    @ResponseBody
    public R update(User user){
        if (Cools.isEmpty(user) || null==user.getId()){
            return R.error();
        }
        userService.updateById(user);
        return R.ok();
    }

    @RequestMapping(value = "/user/delete/auth")
    @ResponseBody
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        userService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
