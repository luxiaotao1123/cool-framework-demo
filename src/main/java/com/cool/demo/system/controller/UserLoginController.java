package com.cool.demo.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.system.entity.UserLogin;
import com.cool.demo.system.service.UserLoginService;
import com.core.common.Cools;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @RequestMapping("/userLogin")
    public String index(){
        return "userLogin/userLogin";
    }

    @RequestMapping("/userLogin_add")
    public String add(){
        return "userLogin/userLogin_add";
    }

    @RequestMapping("/userLogin_detail")
    public String detail(){
        return "userLogin/userLogin_detail";
    }

    @RequestMapping(value = "/userLogin/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(userLoginService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/userLogin/list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit){
        return R.ok(userLoginService.selectPage(new Page<>(curr, limit)));
    }

    @RequestMapping(value = "/userLogin/add/auth")
    @ResponseBody
    public R add(UserLogin userLogin) {
        userLoginService.insert(userLogin);
        return R.ok();
    }

	@RequestMapping(value = "/userLogin/update/auth")
    @ResponseBody
    public R update(UserLogin userLogin){
        if (Cools.isEmpty(userLogin) || null==userLogin.getId()){
            return R.error();
        }
        userLoginService.updateById(userLogin);
        return R.ok();
    }
}
