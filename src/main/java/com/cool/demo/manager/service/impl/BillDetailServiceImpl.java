package com.cool.demo.manager.service.impl;

import com.cool.demo.manager.mapper.BillDetailMapper;
import com.cool.demo.manager.entity.BillDetail;
import com.cool.demo.manager.service.BillDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("billDetailService")
public class BillDetailServiceImpl extends ServiceImpl<BillDetailMapper, BillDetail> implements BillDetailService {

}
