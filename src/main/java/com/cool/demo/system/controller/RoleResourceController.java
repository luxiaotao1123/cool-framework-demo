package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.common.DateUtils;
import com.cool.demo.system.entity.RoleResource;
import com.cool.demo.system.service.RoleResourceService;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class RoleResourceController extends AbstractBaseController {

    @Autowired
    private RoleResourceService roleResourceService;

    @RequestMapping("/roleResource")
    public String index(){
        return "roleResource/roleResource";
    }

    @RequestMapping("/roleResource_detail")
    public String detail(){
        return "roleResource/roleResource_detail";
    }

    @RequestMapping(value = "/roleResource/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(roleResourceService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/roleResource/list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<RoleResource> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        return R.ok(roleResourceService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/roleResource/edit/auth")
    @ResponseBody
    public R edit(RoleResource roleResource) {
        if (Cools.isEmpty(roleResource)){
            return R.error();
        }
        if (null == roleResource.getId()){
            roleResourceService.insert(roleResource);
        } else {
            roleResourceService.updateById(roleResource);
        }
        return R.ok();
    }

    @RequestMapping(value = "/roleResource/add/auth")
    @ResponseBody
    public R add(RoleResource roleResource) {
        roleResourceService.insert(roleResource);
        return R.ok();
    }

	@RequestMapping(value = "/roleResource/update/auth")
    @ResponseBody
    public R update(RoleResource roleResource){
        if (Cools.isEmpty(roleResource) || null==roleResource.getId()){
            return R.error();
        }
        roleResourceService.updateById(roleResource);
        return R.ok();
    }

    @RequestMapping(value = "/roleResource/delete/auth")
    @ResponseBody
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        roleResourceService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/roleResource/export/auth")
    @ResponseBody
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<RoleResource> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("roleResource"));
        convert(map, wrapper);
        List<RoleResource> list = roleResourceService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/roleResourceQuery/auth")
    @ResponseBody
    public R query(String condition) {
        EntityWrapper<RoleResource> wrapper = new EntityWrapper<>();
        wrapper.like("id", condition);
        Page<RoleResource> page = roleResourceService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (RoleResource roleResource : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", roleResource.getId());
            map.put("value", roleResource.getId());
            result.add(map);
        }
        return R.ok(result);
    }

}
