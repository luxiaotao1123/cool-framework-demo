package com.cool.demo.manager.service.impl;

import com.cool.demo.manager.mapper.BillDetailMapper;
import com.cool.demo.manager.entity.BillDetail;
import com.cool.demo.manager.service.BillDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("billDetailService")
public class BillDetailServiceImpl extends ServiceImpl<BillDetailMapper, BillDetail> implements BillDetailService {

    @Autowired
    private  BillDetailMapper billDetailMapper;
    @Override
    public BillDetail getBillDetailByOrderIdandBoxNumber(Long billId, Long boxNumber ) {
        return billDetailMapper.getBillDetailByOrderIdandBoxNumber(billId,boxNumber );
    }

    @Override
    public int getStatistics(String boxer) {
        return billDetailMapper.getStatistics(boxer);
    }
}
