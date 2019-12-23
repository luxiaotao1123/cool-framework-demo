package com.cool.demo.manager.service.impl;

import com.cool.demo.manager.mapper.ItemMapper;
import com.cool.demo.manager.entity.Item;
import com.cool.demo.manager.service.ItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("itemService")
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

}
