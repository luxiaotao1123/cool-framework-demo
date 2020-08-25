package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.annotations.ManagerAuth;
import com.core.common.BaseRes;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import com.cool.demo.common.web.BaseController;
import com.cool.demo.system.entity.Dept;
import com.cool.demo.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/dept/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(deptService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/dept/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        EntityWrapper<Dept> wrapper = new EntityWrapper<>();
        excludeTrash(param);
        convert(param, wrapper);
        if (!Cools.isEmpty(orderByField)){wrapper.orderBy(humpToLine(orderByField), "asc".equals(orderByType));} {
            wrapper.orderBy("sort");
        }
        return R.parse("0-操作成功").add(deptService.selectList(wrapper));
    }

    private void convert(Map<String, Object> map, EntityWrapper wrapper){
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

    @RequestMapping(value = "/dept/add/auth")
    @ManagerAuth
    public R add(Dept dept) {
        dept.setCreateBy(getUserId());
        dept.setCreateTime(new Date());
        dept.setUpdateBy(getUserId());
        dept.setUpdateTime(new Date());
        if (dept.getParentId() == null) {
            dept.setParentId(0L);
        }
        deptService.insert(dept);
        return R.ok();
    }

	@RequestMapping(value = "/dept/update/auth")
	@ManagerAuth
    public R update(Dept dept){
        if (Cools.isEmpty(dept) || null==dept.getId()){
            return R.error();
        }
        deptService.updateById(dept);
        return R.ok();
    }

    @RequestMapping(value = "/dept/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        Dept dept = JSON.parseObject(param, Dept.class);
        if (Cools.isEmpty(dept)){
            return R.error();
        }
        deptService.delete(new EntityWrapper<>(dept));
        return R.ok();
    }

    @RequestMapping(value = "/dept/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        EntityWrapper<Dept> wrapper = new EntityWrapper<>();
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        Map<String, Object> map = excludeTrash(param.getJSONObject("dept"));
        convert(map, wrapper);
        List<Dept> list = deptService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/deptQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper.like("name", condition);
        Page<Dept> page = deptService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Dept dept : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", dept.getId());
            map.put("value", dept.getName());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/dept/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<Dept> wrapper = new EntityWrapper<Dept>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != deptService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(Dept.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
