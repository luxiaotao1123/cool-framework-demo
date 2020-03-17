package com.cool.demo.manager.service.impl;

import com.cool.demo.manager.mapper.BillMapper;
import com.cool.demo.manager.entity.Bill;
import com.cool.demo.manager.service.BillService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("billService")
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

}
