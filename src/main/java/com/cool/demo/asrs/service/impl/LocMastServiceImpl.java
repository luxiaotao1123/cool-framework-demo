package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.LocMastMapper;
import com.cool.demo.asrs.entity.LocMast;
import com.cool.demo.asrs.service.LocMastService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("locMastService")
public class LocMastServiceImpl extends ServiceImpl<LocMastMapper, LocMast> implements LocMastService {

}
