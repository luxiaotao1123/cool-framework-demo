package com.cool.demo.manager.mapper;

import com.cool.demo.manager.entity.Bill;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BillMapper extends BaseMapper<Bill> {

}