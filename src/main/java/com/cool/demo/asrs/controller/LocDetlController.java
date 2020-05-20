package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.LocDetl;
import com.cool.demo.asrs.service.LocDetlService;
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
public class LocDetlController extends BaseController {

    @Autowired
    private LocDetlService locDetlService;

    @RequestMapping(value = "/locDetl/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(locDetlService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/locDetl/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<LocDetl> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
//        wrapper.orderBy("id", false);
        return R.ok(locDetlService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/locDetl/add/auth")
    @ManagerAuth
    public R add(LocDetl locDetl) {
        locDetl.setModiUser(getUserId());
        locDetl.setModiTime(new Date());
        locDetl.setAppeUser(getUserId());
        locDetl.setAppeTime(new Date());
        locDetlService.insert(locDetl);
        return R.ok();
    }

	@RequestMapping(value = "/locDetl/update/auth")
	@ManagerAuth
    public R update(LocDetl locDetl){
        if (Cools.isEmpty(locDetl) || null==locDetl.getMatnr()){
            return R.error();
        }
        locDetl.setModiUser(getUserId());
        locDetl.setModiTime(new Date());
        locDetlService.updateById(locDetl);
        return R.ok();
    }

    @RequestMapping(value = "/locDetl/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<LocDetl> list = JSONArray.parseArray(param, LocDetl.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (LocDetl entity : list){
            locDetlService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/locDetl/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<LocDetl> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("locDetl"));
        convert(map, wrapper);
        List<LocDetl> list = locDetlService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/locDetlQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<LocDetl> wrapper = new EntityWrapper<>();
        wrapper.like("matnr", condition);
        Page<LocDetl> page = locDetlService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (LocDetl locDetl : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", locDetl.getMatnr());
            map.put("value", locDetl.getMatnr());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/locDetl/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<LocDetl> wrapper = new EntityWrapper<LocDetl>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != locDetlService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(LocDetl.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
