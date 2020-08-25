package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import com.cool.demo.common.web.BaseController;
import com.cool.demo.system.entity.Resource;
import com.cool.demo.system.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/resource/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(resourceService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/resource/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Resource> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(resourceService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/resource/edit/auth")
    @ManagerAuth(memo = "菜单编辑")
    public R edit(Resource resource) {
        if (Cools.isEmpty(resource)){
            return R.error();
        }
        if (null == resource.getId()){
            if (resource.getSort() == null){
                resource.setSort(999);
            }
            resourceService.insert(resource);
        } else {
            resourceService.updateById(resource);
        }
        return R.ok();
    }

    @RequestMapping(value = "/resource/add/auth")
    @ManagerAuth(memo = "菜单添加")
    public R add(Resource resource) {
        resourceService.insert(resource);
        return R.ok();
    }

	@RequestMapping(value = "/resource/update/auth")
    @ManagerAuth(memo = "菜单修改")
    public R update(Resource resource){
        if (Cools.isEmpty(resource) || null==resource.getId()){
            return R.error();
        }
        resourceService.updateById(resource);
        return R.ok();
    }

    @RequestMapping(value = "/resource/delete/auth")
    @ManagerAuth(memo = "菜单删除")
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        resourceService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/resource/export/auth")
    @ManagerAuth(memo = "菜单导出")
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Resource> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("resource"));
        convert(map, wrapper);
        List<Resource> list = resourceService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/resourceQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<Resource> wrapper = new EntityWrapper<>();
        wrapper.like("name", condition);
        Page<Resource> page = resourceService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Resource resource : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", resource.getId());
            map.put("value", resource.getName().concat("(").concat(resource.getLevel$().substring(0, 2).concat(")")));
            result.add(map);
        }
        return R.ok(result);
    }

}
