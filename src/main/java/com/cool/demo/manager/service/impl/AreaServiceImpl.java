package com.cool.demo.manager.service.impl;

import com.cool.demo.manager.mapper.AreaMapper;
import com.cool.demo.manager.entity.Area;
import com.cool.demo.manager.service.AreaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("areaService")
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

}
