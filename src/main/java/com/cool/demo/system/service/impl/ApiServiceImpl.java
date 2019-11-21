package com.cool.demo.system.service.impl;

import com.cool.demo.system.mapper.ApiMapper;
import com.cool.demo.system.entity.Api;
import com.cool.demo.system.service.ApiService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("apiService")
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {

}
