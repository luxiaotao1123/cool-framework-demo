package com.cool.demo.system.mapper;

import com.cool.demo.system.entity.Api;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ApiMapper extends BaseMapper<Api> {

}
