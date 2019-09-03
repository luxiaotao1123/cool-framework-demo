package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public String index(){
        return "user/user";
    }

    @RequestMapping("/user_detail")
    public String detail(){
        return "user/user_detail";
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

    @RequestMapping(value = "/user/edit/auth")
    @ResponseBody
    public R edit(User user) {
        if (Cools.isEmpty(user)){
            return R.error();
        }
        if (null == user.getId()){
            userService.insert(user);
        } else {
            userService.updateById(user);
        }
        return R.ok();
    }

    @RequestMapping(value = "/user/add/auth")
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

    @RequestMapping(value = "/user/export/auth")
    @ResponseBody
    public R export(@RequestBody JSONObject param) {
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.setEntity(JSONObject.parseObject(param.getJSONObject("user").toJSONString(), User.class));
        List<User> list = userService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

}
