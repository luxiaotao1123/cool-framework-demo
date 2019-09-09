package com.cool.demo.system.service.impl;

import com.cool.demo.system.mapper.RoleResourceMapper;
import com.cool.demo.system.entity.RoleResource;
import com.cool.demo.system.service.RoleResourceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("roleResourceService")
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {

}
