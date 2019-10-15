package com.cool.demo.manager.service.impl;

import com.cool.demo.manager.mapper.RemarkMapper;
import com.cool.demo.manager.entity.Remark;
import com.cool.demo.manager.service.RemarkService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("remarkService")
public class RemarkServiceImpl extends ServiceImpl<RemarkMapper, Remark> implements RemarkService {

}
