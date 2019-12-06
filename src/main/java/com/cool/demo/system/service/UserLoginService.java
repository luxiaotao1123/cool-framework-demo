package com.cool.demo.system.service;

import com.cool.demo.system.entity.UserLogin;
import com.baomidou.mybatisplus.service.IService;

public interface UserLoginService extends IService<UserLogin> {

    int selectCountByCurrentWeek();

}
