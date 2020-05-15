package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.BasLocTypeMapper;
import com.cool.demo.asrs.entity.BasLocType;
import com.cool.demo.asrs.service.BasLocTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("basLocTypeService")
public class BasLocTypeServiceImpl extends ServiceImpl<BasLocTypeMapper, BasLocType> implements BasLocTypeService {

}
