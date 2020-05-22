package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.WrkMastLog;
import com.cool.demo.asrs.service.WrkMastLogService;
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
public class WrkMastLogController extends BaseController {

    @Autowired
    private WrkMastLogService wrkMastLogService;

    @RequestMapping(value = "/wrkMastLog/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(wrkMastLogService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/wrkMastLog/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<WrkMastLog> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        if (!Cools.isEmpty(orderByField)){wrapper.orderBy(humpToLine(orderByField), "asc".equals(orderByType));}
        return R.ok(wrkMastLogService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/wrkMastLog/add/auth")
    @ManagerAuth
    public R add(WrkMastLog wrkMastLog) {
        wrkMastLogService.insert(wrkMastLog);
        return R.ok();
    }

	@RequestMapping(value = "/wrkMastLog/update/auth")
	@ManagerAuth
    public R update(WrkMastLog wrkMastLog){
        if (Cools.isEmpty(wrkMastLog) || null==wrkMastLog.getId()){
            return R.error();
        }
        wrkMastLogService.updateById(wrkMastLog);
        return R.ok();
    }

    @RequestMapping(value = "/wrkMastLog/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<WrkMastLog> list = JSONArray.parseArray(param, WrkMastLog.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (WrkMastLog entity : list){
            wrkMastLogService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/wrkMastLog/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<WrkMastLog> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("wrkMastLog"));
        convert(map, wrapper);
        List<WrkMastLog> list = wrkMastLogService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/wrkMastLogQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<WrkMastLog> wrapper = new EntityWrapper<>();
        wrapper.like("id", condition);
        Page<WrkMastLog> page = wrkMastLogService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (WrkMastLog wrkMastLog : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", wrkMastLog.getId());
            map.put("value", wrkMastLog.getId());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/wrkMastLog/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<WrkMastLog> wrapper = new EntityWrapper<WrkMastLog>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != wrkMastLogService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(WrkMastLog.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
