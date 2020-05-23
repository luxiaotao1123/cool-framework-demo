package com.cool.demo.asrs.service.impl;

import com.cool.demo.asrs.mapper.WrkDetlMapper;
import com.cool.demo.asrs.entity.WrkDetl;
import com.cool.demo.asrs.service.WrkDetlService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("wrkDetlService")
public class WrkDetlServiceImpl extends ServiceImpl<WrkDetlMapper, WrkDetl> implements WrkDetlService {

}
