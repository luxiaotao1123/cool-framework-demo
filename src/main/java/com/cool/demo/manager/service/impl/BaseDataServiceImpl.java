package com.cool.demo.manager.service.impl;

import com.cool.demo.manager.mapper.BaseDataMapper;
import com.cool.demo.manager.entity.BaseData;
import com.cool.demo.manager.service.BaseDataService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("baseDataService")
public class BaseDataServiceImpl extends ServiceImpl<BaseDataMapper, BaseData> implements BaseDataService {

}
