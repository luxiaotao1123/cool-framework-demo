package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.BasCrnErrorMapper;
import com.cool.demo.asrs.entity.BasCrnError;
import com.cool.demo.asrs.service.BasCrnErrorService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("basCrnErrorService")
public class BasCrnErrorServiceImpl extends ServiceImpl<BasCrnErrorMapper, BasCrnError> implements BasCrnErrorService {

}
