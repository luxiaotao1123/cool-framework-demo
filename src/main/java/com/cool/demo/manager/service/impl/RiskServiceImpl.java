package com.cool.demo.manager.service.impl;

import com.cool.demo.manager.mapper.RiskMapper;
import com.cool.demo.manager.entity.Risk;
import com.cool.demo.manager.service.RiskService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("riskService")
public class RiskServiceImpl extends ServiceImpl<RiskMapper, Risk> implements RiskService {

}
