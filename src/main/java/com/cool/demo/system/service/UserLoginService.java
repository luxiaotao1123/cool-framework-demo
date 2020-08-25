package com.cool.demo.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.cool.demo.system.entity.UserLogin;

public interface UserLoginService extends IService<UserLogin> {

    int selectCountByCurrentWeek();

}
