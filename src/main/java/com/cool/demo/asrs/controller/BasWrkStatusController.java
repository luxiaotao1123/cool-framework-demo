package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.BasWrkStatus;
import com.cool.demo.asrs.service.BasWrkStatusService;
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
public class BasWrkStatusController extends BaseController {

    @Autowired
    private BasWrkStatusService basWrkStatusService;

    @RequestMapping(value = "/basWrkStatus/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(basWrkStatusService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/basWrkStatus/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<BasWrkStatus> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
//        wrapper.orderBy("id", false);
        return R.ok(basWrkStatusService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/basWrkStatus/edit/auth")
    @ManagerAuth
    public R edit(BasWrkStatus basWrkStatus) {
        if (Cools.isEmpty(basWrkStatus)){
            return R.error();
        }
        if (null == basWrkStatus.getWrkSts()){
            basWrkStatusService.insert(basWrkStatus);
        } else {
            basWrkStatusService.updateById(basWrkStatus);
        }
        return R.ok();
    }

    @RequestMapping(value = "/basWrkStatus/add/auth")
    @ManagerAuth
    public R add(BasWrkStatus basWrkStatus) {
        basWrkStatus.setModiUser(getUserId());
        basWrkStatus.setModiTime(new Date());
        basWrkStatusService.insert(basWrkStatus);
        return R.ok();
    }

	@RequestMapping(value = "/basWrkStatus/update/auth")
    public R update(BasWrkStatus basWrkStatus){
        if (Cools.isEmpty(basWrkStatus) || null==basWrkStatus.getWrkSts()){
            return R.error();
        }
        basWrkStatus.setModiUser(getUserId());
        basWrkStatus.setModiTime(new Date());
        basWrkStatusService.updateById(basWrkStatus);
        return R.ok();
    }

    @RequestMapping(value = "/basWrkStatus/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<BasWrkStatus> list = JSONArray.parseArray(param, BasWrkStatus.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (BasWrkStatus entity : list){
            basWrkStatusService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/basWrkStatus/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<BasWrkStatus> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("basWrkStatus"));
        convert(map, wrapper);
        List<BasWrkStatus> list = basWrkStatusService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/basWrkStatusQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<BasWrkStatus> wrapper = new EntityWrapper<>();
        wrapper.like("wrkDesc", condition);
        Page<BasWrkStatus> page = basWrkStatusService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (BasWrkStatus basWrkStatus : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", basWrkStatus.getWrkSts());
            map.put("value", basWrkStatus.getWrkDesc());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/basWrkStatus/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<BasWrkStatus> wrapper = new EntityWrapper<BasWrkStatus>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != basWrkStatusService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(BasWrkStatus.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
