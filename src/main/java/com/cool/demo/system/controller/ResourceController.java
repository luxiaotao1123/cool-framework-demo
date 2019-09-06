package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.common.utils.DateUtils;
import com.cool.demo.system.entity.Resource;
import com.cool.demo.system.service.ResourceService;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/resource")
    public String index(){
        return "resource/resource";
    }

    @RequestMapping("/resource_detail")
    public String detail(){
        return "resource/resource_detail";
    }

    @RequestMapping(value = "/resource/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(resourceService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/resource/list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Resource> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        return R.ok(resourceService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/resource/edit/auth")
    @ResponseBody
    public R edit(Resource resource) {
        if (Cools.isEmpty(resource)){
            return R.error();
        }
        if (null == resource.getId()){
            resourceService.insert(resource);
        } else {
            resourceService.updateById(resource);
        }
        return R.ok();
    }

    @RequestMapping(value = "/resource/add/auth")
    @ResponseBody
    public R add(Resource resource) {
        resourceService.insert(resource);
        return R.ok();
    }

	@RequestMapping(value = "/resource/update/auth")
    @ResponseBody
    public R update(Resource resource){
        if (Cools.isEmpty(resource) || null==resource.getId()){
            return R.error();
        }
        resourceService.updateById(resource);
        return R.ok();
    }

    @RequestMapping(value = "/resource/delete/auth")
    @ResponseBody
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        resourceService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/resource/export/auth")
    @ResponseBody
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Resource> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("resource"));
        convert(map, wrapper);
        List<Resource> list = resourceService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

}
