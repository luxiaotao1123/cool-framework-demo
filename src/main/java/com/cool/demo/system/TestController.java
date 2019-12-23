package com.cool.demo.system;

import com.cool.demo.common.service.DingTalkService;
import com.cool.demo.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vincent on 2019-12-20
 */
@RestController
public class TestController {

    @Autowired
    private DingTalkService dingTalkService;

    @GetMapping("/test")
    public String sda(){
        String userId = dingTalkService.getUserId("sdads", "dsadsa");
        return userId==null?"error":"ok";
    }


    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/redis")
    public String redis(){

        boolean set = redisUtil.set("name", "luxiaotao");
        return set?"ok":"error";


    }
}
