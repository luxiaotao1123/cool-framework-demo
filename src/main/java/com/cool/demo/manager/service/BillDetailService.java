package com.cool.demo.manager.service;

import com.cool.demo.manager.entity.BillDetail;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

public interface BillDetailService extends IService<BillDetail> {

    BillDetail getBillDetailByOrderIdandBoxNumber(Long billId, Long boxNumber);

    int getStatistics(String boxer);

    int getOutStockStatistics(String outStocker);

    List<BillDetail> selectByBillId(Long id);
}
