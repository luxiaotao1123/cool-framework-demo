package com.cool.demo.system.service;

import com.cool.demo.system.entity.OperateLog;
import com.baomidou.mybatisplus.service.IService;

public interface OperateLogService extends IService<OperateLog> {

    int selectCountByCurrentWeek();

}
