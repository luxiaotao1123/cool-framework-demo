package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.system.entity.Role;
import com.cool.demo.system.service.RoleService;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/role")
    public String index(){
        return "role/role";
    }

    @RequestMapping("/role_detail")
    public String detail(){
        return "role/role_detail";
    }

    @RequestMapping(value = "/role/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(roleService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/role/list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  Role role){
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        wrapper.setEntity(role);
        return R.ok(roleService.selectPage(new Page<>(curr, limit), wrapper));
    }

    @RequestMapping(value = "/role/edit/auth")
    @ResponseBody
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
    @ResponseBody
    public R add(Role role) {
        roleService.insert(role);
        return R.ok();
    }

	@RequestMapping(value = "/role/update/auth")
    @ResponseBody
    public R update(Role role){
        if (Cools.isEmpty(role) || null==role.getId()){
            return R.error();
        }
        roleService.updateById(role);
        return R.ok();
    }

    @RequestMapping(value = "/role/delete/auth")
    @ResponseBody
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        roleService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/role/export/auth")
    @ResponseBody
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        wrapper.setEntity(JSONObject.parseObject(param.getJSONObject("role").toJSONString(), Role.class));
        List<Role> list = roleService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

}
