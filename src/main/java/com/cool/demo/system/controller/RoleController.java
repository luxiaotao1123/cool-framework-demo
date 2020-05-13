package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.common.web.BaseController;
import com.cool.demo.system.entity.Role;
import com.cool.demo.system.service.RoleService;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/role/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(roleService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/role/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);

        if (9527 == getUserId()) {
            return R.ok(roleService.selectPage(new Page<>(curr, limit), wrapper));
        }
        Long roleId = getUser().getRoleId();
        Role role = roleService.selectById(roleId);
        Long leaderId = role.getLeader();
        if (null != leaderId) {
            List<Long> leaderIds = new ArrayList<>();
            leaderIds.add(leaderId);
            while (leaderId != null) {
                Role leader = roleService.selectById(leaderId);
                leaderIds.add(leader.getId());
                leaderId = leader.getLeader();
            }
            wrapper.notIn("id", leaderIds);
        }
        if (null != role.getLevel()) {
            wrapper.gt("level", role.getLevel());
        }
        return R.ok(roleService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/role/edit/auth")
    @ManagerAuth
    public R edit(Role role) {
        if (Cools.isEmpty(role)){
            return R.error();
        }
        if (null == role.getId()){
            roleService.insert(role);
        } else {
            roleService.updateById(role);
        }
        return R.ok();
    }

    @RequestMapping(value = "/role/add/auth")
    @ManagerAuth
    public R add(Role role) {
        roleService.insert(role);
        return R.ok();
    }

	@RequestMapping(value = "/role/update/auth")
    public R update(Role role){
        if (Cools.isEmpty(role) || null==role.getId()){
            return R.error();
        }
        roleService.updateById(role);
        return R.ok();
    }

    @RequestMapping(value = "/role/delete/auth")
    @ManagerAuth
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        roleService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/role/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("role"));
        convert(map, wrapper);
        List<Role> list = roleService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/roleQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        wrapper.like("name", condition);
        // 上下级管理
        if (9527 != getUserId()) {
            Long roleId = getUser().getRoleId();
            Role role = roleService.selectById(roleId);
            Long leaderId = role.getLeader();
            if (null != leaderId) {
                List<Long> leaderIds = new ArrayList<>();
                while (leaderId != null) {
                    Role leader = roleService.selectById(leaderId);
                    leaderIds.add(leader.getId());
                    leaderId = leader.getLeader();
                }
                wrapper.notIn("id", leaderIds);
            }
            if (null != role.getLevel()) {
                wrapper.ge("level", role.getLevel());
            }
        }

        Page<Role> page = roleService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Role role : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", role.getId());
            map.put("value", role.getName());
            result.add(map);
        }
        return R.ok(result);
    }

}
