package com.cool.demo;

import com.cool.demo.common.utils.RandomValidateCodeUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Boot extends SpringBootServletInitializer {

    public static void main(String[] args) {
        RandomValidateCodeUtil.init();
        SpringApplication.run(Boot.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        RandomValidateCodeUtil.init();
        return builder.sources(Boot.class);
    }
}
