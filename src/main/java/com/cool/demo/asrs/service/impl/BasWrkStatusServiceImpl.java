package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.BasWrkStatusMapper;
import com.cool.demo.asrs.entity.BasWrkStatus;
import com.cool.demo.asrs.service.BasWrkStatusService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("basWrkStatusService")
public class BasWrkStatusServiceImpl extends ServiceImpl<BasWrkStatusMapper, BasWrkStatus> implements BasWrkStatusService {

}
