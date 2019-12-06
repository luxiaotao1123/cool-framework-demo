package com.cool.demo.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.cool.demo.system.service.OperateLogService;
import com.cool.demo.system.service.UserLoginService;
import com.cool.demo.system.service.UserService;
import com.core.common.Arith;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vincent on 2019-12-06
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private OperateLogService operateLogService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserLoginService userLoginService;

    @RequestMapping("/top")
    public R top(){
        int logTotal = operateLogService.selectCount(new EntityWrapper<>());
        int logWeek = operateLogService.selectCountByCurrentWeek();
        int userTotal = userService.selectCount(new EntityWrapper<>());
        int loginWeek = userLoginService.selectCountByCurrentWeek();

        Map<String, Object> result = new HashMap<>();
        result.put("logTotal", logTotal);
        result.put("logWeek", logWeek);
        result.put("userTotal", userTotal);
        result.put("live", Arith.multiplys(0, Arith.divides(2, loginWeek, userTotal), 100));
        return R.ok(result);
    }

}
