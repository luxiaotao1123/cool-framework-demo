package com.cool.demo.manager.mapper;

import com.cool.demo.manager.entity.BillDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BillDetailMapper extends BaseMapper<BillDetail> {
    BillDetail getBillDetailByOrderIdandBoxNumber(@Param("billId") Long billId, @Param("boxNumber") Long boxNumber);

    int getStatistics(String boxer);
}
