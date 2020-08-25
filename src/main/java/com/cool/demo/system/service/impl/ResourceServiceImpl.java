package com.cool.demo.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cool.demo.system.entity.Resource;
import com.cool.demo.system.mapper.ResourceMapper;
import com.cool.demo.system.service.ResourceService;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

}
