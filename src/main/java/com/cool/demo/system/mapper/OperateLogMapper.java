package com.cool.demo.system.mapper;

import com.cool.demo.system.entity.OperateLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OperateLogMapper extends BaseMapper<OperateLog> {

    @Select("select count(1) from sys_operate_log where yearweek(date_format(create_time,'%Y-%m-%d')) = yearweek(now());")
    int selectCountByCurrentWeek();

}
