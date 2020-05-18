package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.BasCrnpMapper;
import com.cool.demo.asrs.entity.BasCrnp;
import com.cool.demo.asrs.service.BasCrnpService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("basCrnpService")
public class BasCrnpServiceImpl extends ServiceImpl<BasCrnpMapper, BasCrnp> implements BasCrnpService {

}
