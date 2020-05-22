package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.BasWhs;
import com.cool.demo.asrs.service.BasWhsService;
import com.cool.demo.common.web.BaseController;
import com.core.annotations.ManagerAuth;
import com.core.common.BaseRes;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BasWhsController extends BaseController {

    @Autowired
    private BasWhsService basWhsService;

    @RequestMapping(value = "/basWhs/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(basWhsService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/basWhs/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<BasWhs> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        if (!Cools.isEmpty(orderByField)){wrapper.orderBy(humpToLine(orderByField), "asc".equals(orderByType));}
        return R.ok(basWhsService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/basWhs/add/auth")
    @ManagerAuth
    public R add(BasWhs basWhs) {
        basWhs.setModiUser(getUserId());
        basWhs.setModiTime(new Date());
        basWhs.setAppeUser(getUserId());
        basWhs.setAppeTime(new Date());
        basWhsService.insert(basWhs);
        return R.ok();
    }

	@RequestMapping(value = "/basWhs/update/auth")
    @ManagerAuth
    public R update(BasWhs basWhs){
        if (Cools.isEmpty(basWhs) || null==basWhs.getId()){
            return R.error();
        }
        basWhs.setModiUser(getUserId());
        basWhs.setModiTime(new Date());
        basWhsService.updateById(basWhs);
        return R.ok();
    }

    @RequestMapping(value = "/basWhs/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<BasWhs> list = JSONArray.parseArray(param, BasWhs.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (BasWhs entity : list){
            basWhsService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/basWhs/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<BasWhs> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("basWhs"));
        convert(map, wrapper);
        List<BasWhs> list = basWhsService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/basWhsQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<BasWhs> wrapper = new EntityWrapper<>();
        wrapper.like("whs_desc", condition);
        Page<BasWhs> page = basWhsService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (BasWhs basWhs : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", basWhs.getId());
            map.put("value", basWhs.getWhsDesc());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/basWhs/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<BasWhs> wrapper = new EntityWrapper<BasWhs>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != basWhsService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(BasWhs.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
