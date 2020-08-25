package com.cool.demo.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.cool.demo.system.entity.User;

import java.util.List;

public interface UserService extends IService<User> {

    List<User> getUserByDept(Long deptParentId);

}
