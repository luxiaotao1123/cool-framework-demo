package com.cool.demo.system;

import com.cool.demo.common.service.DingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vincent on 2019-12-20
 */
@RestController
public class TestController {

    @Autowired
    private DingService dingService;

    @GetMapping("/test")
    public String sda(){
        String userId = dingService.getUserId("sdads", "dsadsa");
        return userId==null?"error":"ok";
    }

}
