package com.cool.demo.manager.service;

import com.cool.demo.manager.entity.Bill;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

public interface BillService extends IService<Bill> {

    List<Bill> selectByDetialId(String[] ids);
}
