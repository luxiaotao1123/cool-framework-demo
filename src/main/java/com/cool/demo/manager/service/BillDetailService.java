package com.cool.demo.manager.service;

import com.cool.demo.manager.entity.BillDetail;
import com.baomidou.mybatisplus.service.IService;

public interface BillDetailService extends IService<BillDetail> {
    BillDetail getBillDetailByOrderIdandBoxNumber(Long billId, Long boxNumber);

    int getStatistics(String boxer);
}
