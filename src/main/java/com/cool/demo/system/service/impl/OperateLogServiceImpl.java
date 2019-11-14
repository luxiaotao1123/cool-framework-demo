package com.cool.demo.system.service.impl;

import com.cool.demo.system.mapper.OperateLogMapper;
import com.cool.demo.system.entity.OperateLog;
import com.cool.demo.system.service.OperateLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("operateLogService")
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements OperateLogService {

}
