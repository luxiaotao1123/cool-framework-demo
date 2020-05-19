package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.StaDescMapper;
import com.cool.demo.asrs.entity.StaDesc;
import com.cool.demo.asrs.service.StaDescService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("staDescService")
public class StaDescServiceImpl extends ServiceImpl<StaDescMapper, StaDesc> implements StaDescService {

}
