package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.common.DateUtils;
import com.cool.demo.manager.entity.Item;
import com.cool.demo.manager.service.ItemService;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ItemController extends AbstractBaseController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item")
    public String index(){
        return "item/item";
    }

    @RequestMapping("/item_detail")
    public String detail(){
        return "item/item_detail";
    }

    @RequestMapping(value = "/item/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(itemService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/item/list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Item> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(itemService.selectPage(new Page<>(curr, limit), wrapper));
    }

    private void convert(Map<String, Object> map, EntityWrapper wrapper){
        for (Map.Entry<String, Object> entry : map.entrySet()){
            if (entry.getKey().endsWith(">")) {
                wrapper.ge(Cools.deleteChar(entry.getKey()), DateUtils.convert(String.valueOf(entry.getValue())));
            } else if (entry.getKey().endsWith("<")) {
                wrapper.le(Cools.deleteChar(entry.getKey()), DateUtils.convert(String.valueOf(entry.getValue())));
            } else {
                wrapper.like(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
    }

    @RequestMapping(value = "/item/edit/auth")
    @ResponseBody
    public R edit(Item item) {
        if (Cools.isEmpty(item)){
            return R.error();
        }
        if (null == item.getId()){
            itemService.insert(item);
        } else {
            itemService.updateById(item);
        }
        return R.ok();
    }

    @RequestMapping(value = "/item/add/auth")
    @ResponseBody
    public R add(Item item) {
        itemService.insert(item);
        return R.ok();
    }

	@RequestMapping(value = "/item/update/auth")
    @ResponseBody
    public R update(Item item){
        if (Cools.isEmpty(item) || null==item.getId()){
            return R.error();
        }
        itemService.updateById(item);
        return R.ok();
    }

    @RequestMapping(value = "/item/delete/auth")
    @ResponseBody
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        itemService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/item/export/auth")
    @ResponseBody
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Item> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("item"));
        convert(map, wrapper);
        List<Item> list = itemService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/itemQuery/auth")
    @ResponseBody
    public R query(String condition) {
        EntityWrapper<Item> wrapper = new EntityWrapper<>();
        wrapper.like("name", condition);
        Page<Item> page = itemService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Item item : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("value", item.getName());
            result.add(map);
        }
        return R.ok(result);
    }

}
