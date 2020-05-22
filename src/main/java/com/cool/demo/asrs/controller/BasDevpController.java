package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.BasDevp;
import com.cool.demo.asrs.service.BasDevpService;
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
public class BasDevpController extends BaseController {

    @Autowired
    private BasDevpService basDevpService;

    @RequestMapping(value = "/basDevp/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(basDevpService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/basDevp/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<BasDevp> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        if (!Cools.isEmpty(orderByField)){wrapper.orderBy(humpToLine(orderByField), "asc".equals(orderByType));}
        return R.ok(basDevpService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/basDevp/add/auth")
    @ManagerAuth
    public R add(BasDevp basDevp) {
        basDevp.setModiUser(getUserId());
        basDevp.setModiTime(new Date());
        basDevp.setAppeUser(getUserId());
        basDevp.setAppeTime(new Date());
        basDevpService.insert(basDevp);
        return R.ok();
    }

	@RequestMapping(value = "/basDevp/update/auth")
	@ManagerAuth
    public R update(BasDevp basDevp){
        if (Cools.isEmpty(basDevp) || null==basDevp.getDevNo()){
            return R.error();
        }
        basDevp.setModiUser(getUserId());
        basDevp.setModiTime(new Date());
        basDevpService.updateById(basDevp);
        return R.ok();
    }

    @RequestMapping(value = "/basDevp/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<BasDevp> list = JSONArray.parseArray(param, BasDevp.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (BasDevp entity : list){
            basDevpService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/basDevp/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<BasDevp> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("basDevp"));
        convert(map, wrapper);
        List<BasDevp> list = basDevpService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/basDevpQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<BasDevp> wrapper = new EntityWrapper<>();
        wrapper.like("dev_no", condition);
        Page<BasDevp> page = basDevpService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (BasDevp basDevp : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", basDevp.getDevNo());
            map.put("value", basDevp.getDevNo());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/basDevp/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<BasDevp> wrapper = new EntityWrapper<BasDevp>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != basDevpService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(BasDevp.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
