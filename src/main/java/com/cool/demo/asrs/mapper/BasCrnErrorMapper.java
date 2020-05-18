package com.cool.demo.asrs.mapper;

import com.cool.demo.asrs.entity.BasCrnError;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BasCrnErrorMapper extends BaseMapper<BasCrnError> {

}
