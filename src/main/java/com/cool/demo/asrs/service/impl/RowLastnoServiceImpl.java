package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.RowLastnoMapper;
import com.cool.demo.asrs.entity.RowLastno;
import com.cool.demo.asrs.service.RowLastnoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("rowLastnoService")
public class RowLastnoServiceImpl extends ServiceImpl<RowLastnoMapper, RowLastno> implements RowLastnoService {

}
