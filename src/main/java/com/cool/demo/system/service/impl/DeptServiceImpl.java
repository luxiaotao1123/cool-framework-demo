package com.cool.demo.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cool.demo.system.entity.Dept;
import com.cool.demo.system.mapper.DeptMapper;
import com.cool.demo.system.service.DeptService;
import com.cool.demo.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deptService")
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
    private UserService userService;

    @Override
    public int getMemberCount(Long deptId) {
        Dept dept = selectById(deptId);
        return 0;
    }
}
