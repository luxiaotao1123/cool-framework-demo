package com.cool.demo.system.mapper;

import com.cool.demo.system.entity.Host;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HostMapper extends BaseMapper<Host> {

}
