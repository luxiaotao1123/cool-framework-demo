package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.BasWhsMapper;
import com.cool.demo.asrs.entity.BasWhs;
import com.cool.demo.asrs.service.BasWhsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("basWhsService")
public class BasWhsServiceImpl extends ServiceImpl<BasWhsMapper, BasWhs> implements BasWhsService {

}
