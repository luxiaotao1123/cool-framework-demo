package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.WrkLastnoMapper;
import com.cool.demo.asrs.entity.WrkLastno;
import com.cool.demo.asrs.service.WrkLastnoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("wrkLastnoService")
public class WrkLastnoServiceImpl extends ServiceImpl<WrkLastnoMapper, WrkLastno> implements WrkLastnoService {

}
