package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import com.cool.demo.common.web.BaseController;
import com.cool.demo.system.entity.Host;
import com.cool.demo.system.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class HostController extends BaseController {

    @Autowired
    private HostService hostService;

    @RequestMapping(value = "/host/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(hostService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/host/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Host> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(hostService.selectPage(new Page<>(curr, limit), wrapper));
    }

    private <T> void convert(Map<String, Object> map, EntityWrapper<T> wrapper){
        for (Map.Entry<String, Object> entry : map.entrySet()){
            String val = String.valueOf(entry.getValue());
            if (val.contains(RANGE_TIME_LINK)){
                String[] dates = val.split(RANGE_TIME_LINK);
                wrapper.ge(entry.getKey(), DateUtils.convert(dates[0]));
                wrapper.le(entry.getKey(), DateUtils.convert(dates[1]));
            } else {
                wrapper.like(entry.getKey(), val);
            }
        }
    }

    @RequestMapping(value = "/host/edit/auth")
    @ManagerAuth
    public R edit(Host host) {
        if (Cools.isEmpty(host)){
            return R.error();
        }
        if (null == host.getId()){
            hostService.insert(host);
        } else {
            hostService.updateById(host);
        }
        return R.ok();
    }

    @RequestMapping(value = "/host/add/auth")
    @ManagerAuth
    public R add(Host host) {
        hostService.insert(host);
        return R.ok();
    }

	@RequestMapping(value = "/host/update/auth")
    @ManagerAuth
    public R update(Host host){
        if (Cools.isEmpty(host) || null==host.getId()){
            return R.error();
        }
        hostService.updateById(host);
        return R.ok();
    }

    @RequestMapping(value = "/host/delete/auth")
    @ManagerAuth
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        hostService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/host/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Host> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("host"));
        convert(map, wrapper);
        List<Host> list = hostService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/hostQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<Host> wrapper = new EntityWrapper<>();
        wrapper.like("name", condition);
        Page<Host> page = hostService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Host host : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", host.getId());
            map.put("value", host.getName());
            result.add(map);
        }
        return R.ok(result);
    }

}
