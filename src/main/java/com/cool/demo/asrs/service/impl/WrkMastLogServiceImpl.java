package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.WrkMastLogMapper;
import com.cool.demo.asrs.entity.WrkMastLog;
import com.cool.demo.asrs.service.WrkMastLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("wrkMastLogService")
public class WrkMastLogServiceImpl extends ServiceImpl<WrkMastLogMapper, WrkMastLog> implements WrkMastLogService {

}
