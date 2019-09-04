package com.cool.demo.system.service.impl;

import com.cool.demo.system.mapper.RoleMapper;
import com.cool.demo.system.entity.Role;
import com.cool.demo.system.service.RoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
