package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.WrkLastno;
import com.cool.demo.asrs.service.WrkLastnoService;
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
public class WrkLastnoController extends BaseController {

    @Autowired
    private WrkLastnoService wrkLastnoService;

    @RequestMapping(value = "/wrkLastno/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(wrkLastnoService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/wrkLastno/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<WrkLastno> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
//        wrapper.orderBy("id", false);
        return R.ok(wrkLastnoService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/wrkLastno/add/auth")
    @ManagerAuth
    public R add(WrkLastno wrkLastno) {
        wrkLastno.setModiUser(getUserId());
        wrkLastno.setModiTime(new Date());
        wrkLastno.setAppeUser(getUserId());
        wrkLastno.setAppeTime(new Date());
        wrkLastnoService.insert(wrkLastno);
        return R.ok();
    }

	@RequestMapping(value = "/wrkLastno/update/auth")
	@ManagerAuth
    public R update(WrkLastno wrkLastno){
        if (Cools.isEmpty(wrkLastno) || null==wrkLastno.getWrkMk()){
            return R.error();
        }
        wrkLastno.setModiUser(getUserId());
        wrkLastno.setModiTime(new Date());
        wrkLastnoService.updateById(wrkLastno);
        return R.ok();
    }

    @RequestMapping(value = "/wrkLastno/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<WrkLastno> list = JSONArray.parseArray(param, WrkLastno.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (WrkLastno entity : list){
            wrkLastnoService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/wrkLastno/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<WrkLastno> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("wrkLastno"));
        convert(map, wrapper);
        List<WrkLastno> list = wrkLastnoService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/wrkLastnoQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<WrkLastno> wrapper = new EntityWrapper<>();
        wrapper.like("wrk_mk", condition);
        Page<WrkLastno> page = wrkLastnoService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (WrkLastno wrkLastno : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", wrkLastno.getWrkMk());
            map.put("value", wrkLastno.getWrkMk());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/wrkLastno/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<WrkLastno> wrapper = new EntityWrapper<WrkLastno>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != wrkLastnoService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(WrkLastno.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
