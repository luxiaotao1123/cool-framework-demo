package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.LocMast;
import com.cool.demo.asrs.service.LocMastService;
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
public class LocMastController extends BaseController {

    @Autowired
    private LocMastService locMastService;

    @RequestMapping(value = "/locMast/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(locMastService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/locMast/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<LocMast> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
//        wrapper.orderBy("id", false);
        return R.ok(locMastService.selectPage(new Page<>(curr, limit), wrapper));
    }

    private void convert(Map<String, Object> map, EntityWrapper wrapper){
        for (Map.Entry<String, Object> entry : map.entrySet()){
            if (entry.getKey().endsWith(">")) {
                wrapper.ge(Cools.deleteChar(entry.getKey()), DateUtils.convert(String.valueOf(entry.getValue())));
            } else if (entry.getKey().endsWith("<")) {
                wrapper.le(Cools.deleteChar(entry.getKey()), DateUtils.convert(String.valueOf(entry.getValue())));
            } else {
                wrapper.eq(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
    }

    @RequestMapping(value = "/locMast/add/auth")
    @ManagerAuth
    public R add(LocMast locMast) {
        locMast.setModiUser(getUserId());
        locMast.setModiTime(new Date());
        locMast.setAppeUser(getUserId());
        locMast.setAppeTime(new Date());
        locMastService.insert(locMast);
        return R.ok();
    }

	@RequestMapping(value = "/locMast/update/auth")
	@ManagerAuth
    public R update(LocMast locMast){
        if (Cools.isEmpty(locMast) || null==locMast.getLocNo()){
            return R.error();
        }
        locMast.setModiUser(getUserId());
        locMast.setModiTime(new Date());
        locMastService.updateById(locMast);
        return R.ok();
    }

    @RequestMapping(value = "/locMast/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<LocMast> list = JSONArray.parseArray(param, LocMast.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (LocMast entity : list){
            locMastService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/locMast/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<LocMast> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("locMast"));
        convert(map, wrapper);
        List<LocMast> list = locMastService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/locMastQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<LocMast> wrapper = new EntityWrapper<>();
        wrapper.like("loc_no", condition);
        Page<LocMast> page = locMastService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (LocMast locMast : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", locMast.getLocNo());
            map.put("value", locMast.getLocNo());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/locMast/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<LocMast> wrapper = new EntityWrapper<LocMast>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != locMastService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(LocMast.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
