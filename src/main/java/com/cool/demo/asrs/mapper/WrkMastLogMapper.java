package com.cool.demo.asrs.mapper;

import com.cool.demo.asrs.entity.WrkMastLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WrkMastLogMapper extends BaseMapper<WrkMastLog> {

}