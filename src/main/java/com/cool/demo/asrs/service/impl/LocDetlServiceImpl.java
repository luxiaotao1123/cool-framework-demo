package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.LocDetlMapper;
import com.cool.demo.asrs.entity.LocDetl;
import com.cool.demo.asrs.service.LocDetlService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("locDetlService")
public class LocDetlServiceImpl extends ServiceImpl<LocDetlMapper, LocDetl> implements LocDetlService {

}
