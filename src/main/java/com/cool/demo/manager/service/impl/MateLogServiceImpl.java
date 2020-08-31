package com.cool.demo.manager.service.impl;

import com.cool.demo.manager.mapper.MateLogMapper;
import com.cool.demo.manager.entity.MateLog;
import com.cool.demo.manager.service.MateLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("mateLogService")
public class MateLogServiceImpl extends ServiceImpl<MateLogMapper, MateLog> implements MateLogService {

}
