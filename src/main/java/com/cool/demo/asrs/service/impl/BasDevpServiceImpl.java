package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.BasDevpMapper;
import com.cool.demo.asrs.entity.BasDevp;
import com.cool.demo.asrs.service.BasDevpService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("basDevpService")
public class BasDevpServiceImpl extends ServiceImpl<BasDevpMapper, BasDevp> implements BasDevpService {

}
