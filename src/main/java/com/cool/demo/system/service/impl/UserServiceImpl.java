package com.cool.demo.system.service.impl;

import com.cool.demo.system.mapper.UserMapper;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
