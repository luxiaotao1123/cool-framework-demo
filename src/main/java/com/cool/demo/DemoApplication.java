package com.cool.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cool.demo.dao")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//    @Autowired
//    private UserService userService;
//
//
//    @GetMapping("/test")
//    @ResponseBody
//    public String test(){
//        User user = userService.selectById("1136299639260106754");
//        return user.toString();
//    }

}
