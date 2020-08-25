package com.cool.demo.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.mapper.UserMapper;
import com.cool.demo.system.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<User> getUserByDept(Long dept) {
        return this.baseMapper.getUserByDept(dept);
    }
}
