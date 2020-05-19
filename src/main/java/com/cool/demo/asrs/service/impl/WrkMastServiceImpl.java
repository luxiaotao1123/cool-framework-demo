package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.WrkMastMapper;
import com.cool.demo.asrs.entity.WrkMast;
import com.cool.demo.asrs.service.WrkMastService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("wrkMastService")
public class WrkMastServiceImpl extends ServiceImpl<WrkMastMapper, WrkMast> implements WrkMastService {

}
