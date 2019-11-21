package com.cool.demo.system.service.impl;

import com.cool.demo.system.mapper.PermissionMapper;
import com.cool.demo.system.entity.Permission;
import com.cool.demo.system.service.PermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
