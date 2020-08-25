package com.cool.demo.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cool.demo.system.entity.Resource;
import com.cool.demo.system.entity.RoleResource;
import com.cool.demo.system.mapper.RoleResourceMapper;
import com.cool.demo.system.service.RoleResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleResourceService")
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {

    @Override
    public List<Resource> getMenuButtomResource(Long fatherResourceId, Long userId) {
        return this.baseMapper.getMenuButtomResource(fatherResourceId, userId);
    }
}
