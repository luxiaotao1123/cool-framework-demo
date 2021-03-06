package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import com.cool.demo.common.web.BaseController;
import com.cool.demo.system.entity.Permission;
import com.cool.demo.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/permission/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(permissionService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/permission/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Permission> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(permissionService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/permission/edit/auth")
    @ManagerAuth
    public R edit(Permission permission) {
        if (Cools.isEmpty(permission)){
            return R.error();
        }
        if (null == permission.getId()){
            permissionService.insert(permission);
        } else {
            permissionService.updateById(permission);
        }
        return R.ok();
    }

    @RequestMapping(value = "/permission/add/auth")
    @ManagerAuth
    public R add(Permission permission) {
        permissionService.insert(permission);
        return R.ok();
    }

	@RequestMapping(value = "/permission/update/auth")
    @ManagerAuth
    public R update(Permission permission){
        if (Cools.isEmpty(permission) || null==permission.getId()){
            return R.error();
        }
        permissionService.updateById(permission);
        return R.ok();
    }

    @RequestMapping(value = "/permission/delete/auth")
    @ManagerAuth
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        permissionService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/permission/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Permission> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("permission"));
        convert(map, wrapper);
        List<Permission> list = permissionService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/permissionQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<Permission> wrapper = new EntityWrapper<>();
        wrapper.like("name", condition);
        Page<Permission> page = permissionService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Permission permission : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", permission.getId());
            map.put("value", permission.getName());
            result.add(map);
        }
        return R.ok(result);
    }

}
