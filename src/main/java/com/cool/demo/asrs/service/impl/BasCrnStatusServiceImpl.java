package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.BasCrnStatusMapper;
import com.cool.demo.asrs.entity.BasCrnStatus;
import com.cool.demo.asrs.service.BasCrnStatusService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("basCrnStatusService")
public class BasCrnStatusServiceImpl extends ServiceImpl<BasCrnStatusMapper, BasCrnStatus> implements BasCrnStatusService {

}
