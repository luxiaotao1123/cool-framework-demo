package com.cool.demo.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.cool.demo.system.entity.Resource;
import com.cool.demo.system.entity.RoleResource;

import java.util.List;

public interface RoleResourceService extends IService<RoleResource> {

    List<Resource> getMenuButtomResource(Long fatherResourceId, Long userId);

}
