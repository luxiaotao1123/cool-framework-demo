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
@RequestMapping("/userLogin")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @RequestMapping("")
    public String index(){
        return "userLogin/userLogin";
    }

    @RequestMapping(value = "/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(userLoginService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer page,
                  @RequestParam(defaultValue = "10")Integer limit){
        return R.ok(userLoginService.selectPage(new Page<>(page, limit)));
    }

    @RequestMapping(value = "/add/auth")
    @ResponseBody
    public R add(UserLogin userLogin) {
        userLoginService.insert(userLogin);
        return R.ok();
    }

	@RequestMapping(value = "/update/auth")
    @ResponseBody
    public R update(UserLogin userLogin){
        if (Cools.isEmpty(userLogin) || null==userLogin.getId()){
            return R.error();
        }
        userLoginService.updateById(userLogin);
        return R.ok();
    }
}
