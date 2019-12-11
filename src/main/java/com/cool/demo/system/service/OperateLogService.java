package com.cool.demo.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.cool.demo.system.entity.OperateLog;

import java.util.List;
import java.util.Map;

public interface OperateLogService extends IService<OperateLog> {

    int selectCountByCurrentWeek();

    List<Map<String, Object>> getReport(Integer year, Integer month);

}
