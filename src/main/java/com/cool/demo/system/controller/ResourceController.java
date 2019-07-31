package com.cool.demo.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.system.entity.Resource;
import com.cool.demo.system.service.ResourceService;
import com.core.common.Cools;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/resource")
    public String index(){
        return "resource/resource";
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
                  Resource resource){
        EntityWrapper<Resource> wrapper = new EntityWrapper<>();
        wrapper.setEntity(resource);
        return R.ok(resourceService.selectPage(new Page<>(curr, limit), wrapper));
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
}
