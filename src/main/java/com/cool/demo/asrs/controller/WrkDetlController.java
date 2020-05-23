package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.WrkDetl;
import com.cool.demo.asrs.service.WrkDetlService;
import com.cool.demo.common.web.BaseController;
import com.core.annotations.ManagerAuth;
import com.core.common.BaseRes;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WrkDetlController extends BaseController {

    @Autowired
    private WrkDetlService wrkDetlService;

    @RequestMapping(value = "/wrkDetl/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(wrkDetlService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/wrkDetl/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<WrkDetl> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        if (!Cools.isEmpty(orderByField)){wrapper.orderBy(humpToLine(orderByField), "asc".equals(orderByType));}
        return R.ok(wrkDetlService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/wrkDetl/add/auth")
    @ManagerAuth
    public R add(WrkDetl wrkDetl) {
        wrkDetlService.insert(wrkDetl);
        return R.ok();
    }

    @RequestMapping(value = "/wrkDetl/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<WrkDetl> list = JSONArray.parseArray(param, WrkDetl.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (WrkDetl entity : list){
            wrkDetlService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/wrkDetl/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<WrkDetl> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("wrkDetl"));
        convert(map, wrapper);
        List<WrkDetl> list = wrkDetlService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/wrkDetlQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<WrkDetl> wrapper = new EntityWrapper<>();
        wrapper.like("id", condition);
        Page<WrkDetl> page = wrkDetlService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (WrkDetl wrkDetl : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", wrkDetl.getWrkNo());
            map.put("value", wrkDetl.getWrkNo());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/wrkDetl/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<WrkDetl> wrapper = new EntityWrapper<WrkDetl>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != wrkDetlService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(WrkDetl.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
