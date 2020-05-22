package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.WrkMast;
import com.cool.demo.asrs.service.WrkMastService;
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
public class WrkMastController extends BaseController {

    @Autowired
    private WrkMastService wrkMastService;

    @RequestMapping(value = "/wrkMast/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(wrkMastService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/wrkMast/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<WrkMast> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        if (!Cools.isEmpty(orderByField)){wrapper.orderBy(humpToLine(orderByField), "asc".equals(orderByType));}
        return R.ok(wrkMastService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/wrkMast/add/auth")
    @ManagerAuth
    public R add(WrkMast wrkMast) {
        wrkMast.setModiUser(getUserId());
        wrkMast.setModiTime(new Date());
        wrkMast.setAppeUser(getUserId());
        wrkMast.setAppeTime(new Date());
        wrkMastService.insert(wrkMast);
        return R.ok();
    }

	@RequestMapping(value = "/wrkMast/update/auth")
	@ManagerAuth
    public R update(WrkMast wrkMast){
        if (Cools.isEmpty(wrkMast) || null==wrkMast.getWrkNo()){
            return R.error();
        }
        wrkMast.setModiUser(getUserId());
        wrkMast.setModiTime(new Date());
        wrkMastService.updateById(wrkMast);
        return R.ok();
    }

    @RequestMapping(value = "/wrkMast/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<WrkMast> list = JSONArray.parseArray(param, WrkMast.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (WrkMast entity : list){
            wrkMastService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/wrkMast/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<WrkMast> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("wrkMast"));
        convert(map, wrapper);
        List<WrkMast> list = wrkMastService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/wrkMastQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<WrkMast> wrapper = new EntityWrapper<>();
        wrapper.like("wrk_no", condition);
        Page<WrkMast> page = wrkMastService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (WrkMast wrkMast : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", wrkMast.getWrkNo());
            map.put("value", wrkMast.getWrkNo());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/wrkMast/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<WrkMast> wrapper = new EntityWrapper<WrkMast>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != wrkMastService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(WrkMast.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
