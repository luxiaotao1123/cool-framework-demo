package com.cool.demo.manager.service.impl;

import com.cool.demo.manager.mapper.MateMapper;
import com.cool.demo.manager.entity.Mate;
import com.cool.demo.manager.service.MateService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("mateService")
public class MateServiceImpl extends ServiceImpl<MateMapper, Mate> implements MateService {

}
