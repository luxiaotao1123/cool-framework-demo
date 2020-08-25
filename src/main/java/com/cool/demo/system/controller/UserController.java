package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import com.cool.demo.common.web.BaseController;
import com.cool.demo.system.entity.Role;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.RoleService;
import com.cool.demo.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/user/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(userService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/user/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        if (9527 == getUserId()) {
            return R.ok(userService.selectPage(new Page<>(curr, limit), wrapper));
        }

        Long roleId = getUser().getRoleId();
        Role role = roleService.selectById(roleId);
        Long leaderId = role.getLeader();
        if (null != leaderId) {
            List<Long> leaderIds = new ArrayList<>();
            leaderIds.add(role.getId());
            while (leaderId != null) {
                Role leader = roleService.selectById(leaderId);
                leaderIds.add(leader.getId());
                leaderId = leader.getLeader();
            }
            wrapper.notIn("role_id", leaderIds);
        }

        return R.ok(userService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/user/edit/auth")
    @ManagerAuth(memo = "系统用户编辑")
    public R edit(User user) {
        if (Cools.isEmpty(user)){
            return R.error();
        }
        if (null == user.getId()){
            user.setStatus(1);
            user.setCreateTime(new Date());
            userService.insert(user);
        } else {
            userService.updateById(user);
        }
        return R.ok();
    }

    @RequestMapping(value = "/user/add/auth")
    @ManagerAuth(memo = "系统用户添加")
    public R add(User user) {
        user.setStatus(1);
        user.setCreateTime(new Date());
        userService.insert(user);
        return R.ok();
    }

	@RequestMapping(value = "/user/update/auth")
    @ManagerAuth(memo = "系统用户修改")
    public R update(User user){
        if (Cools.isEmpty(user) || null==user.getId()){
            return R.error();
        }
        int count = userService.selectCount(new EntityWrapper<User>().ne("id", user.getId()).eq("username", user.getUsername()));
        if (count > 0) {
            return R.error("账号已存在");
        }
        userService.updateById(user);
        return R.ok();
    }

    @RequestMapping(value = "/user/delete/auth")
    @ManagerAuth(memo = "系统用户删除")
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        userService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/user/export/auth")
    @ManagerAuth(memo = "系统用户导出")
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("user"));
        convert(map, wrapper);
        List<User> list = userService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/userQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.like("username", condition);
        Page<User> page = userService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (User user : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("value", user.getNickname());
            result.add(map);
        }
        return R.ok(result);
    }

}
