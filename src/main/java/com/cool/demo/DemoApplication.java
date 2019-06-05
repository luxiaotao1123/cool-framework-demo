package com.cool.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//    @GetMapping("/test")
//    @ResponseBody
//    public String test(){
//        User user = new User();
//        user.setAge(18);
//        user.setName("陆晓涛");
//        long save = userService.save(user);
//        return String.valueOf(save);
//    }


}
