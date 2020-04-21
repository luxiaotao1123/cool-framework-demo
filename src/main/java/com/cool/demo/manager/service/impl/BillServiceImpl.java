package com.cool.demo.manager.service.impl;

import com.cool.demo.manager.mapper.BillMapper;
import com.cool.demo.manager.entity.Bill;
import com.cool.demo.manager.service.BillService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("billService")
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

    @Autowired
    private BillMapper billMapper;
    @Override
    public List<Bill> selectByDetialId(String[] ids) {
        return billMapper.selectByDetialId(ids);
    }
}
