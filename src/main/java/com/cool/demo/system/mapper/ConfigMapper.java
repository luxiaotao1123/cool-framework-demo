package com.cool.demo.system.mapper;

import com.cool.demo.system.entity.Config;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ConfigMapper extends BaseMapper<Config> {

}
