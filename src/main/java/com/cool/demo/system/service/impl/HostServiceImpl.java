package com.cool.demo.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cool.demo.system.entity.Host;
import com.cool.demo.system.mapper.HostMapper;
import com.cool.demo.system.service.HostService;
import org.springframework.stereotype.Service;

@Service("hostService")
public class HostServiceImpl extends ServiceImpl<HostMapper, Host> implements HostService {

}
