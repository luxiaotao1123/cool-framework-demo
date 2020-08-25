package com.cool.demo.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.cool.demo.system.entity.Dept;

public interface DeptService extends IService<Dept> {

    int getMemberCount(Long deptId);

}
