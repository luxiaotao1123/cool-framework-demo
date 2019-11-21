package com.cool.demo.system.service.impl;

import com.cool.demo.system.mapper.RolePermissionMapper;
import com.cool.demo.system.entity.RolePermission;
import com.cool.demo.system.service.RolePermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

}
