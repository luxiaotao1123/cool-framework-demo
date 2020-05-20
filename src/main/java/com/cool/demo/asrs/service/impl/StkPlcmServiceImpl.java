package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.StkPlcmMapper;
import com.cool.demo.asrs.entity.StkPlcm;
import com.cool.demo.asrs.service.StkPlcmService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("stkPlcmService")
public class StkPlcmServiceImpl extends ServiceImpl<StkPlcmMapper, StkPlcm> implements StkPlcmService {

}
