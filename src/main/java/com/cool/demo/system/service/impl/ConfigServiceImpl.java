package com.cool.demo.system.service.impl;

import com.cool.demo.system.mapper.ConfigMapper;
import com.cool.demo.system.entity.Config;
import com.cool.demo.system.service.ConfigService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("configService")
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

}
