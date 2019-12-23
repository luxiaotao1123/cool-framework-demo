package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.common.DateUtils;
import com.cool.demo.manager.entity.Area;
import com.cool.demo.manager.service.AreaService;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class AreaController extends AbstractBaseController {

    @Autowired
    private AreaService areaService;

    @RequestMapping("/area")
    public String index(){
        return "area/area";
    }

    @RequestMapping("/area_detail")
    public String detail(){
        return "area/area_detail";
    }

    @RequestMapping(value = "/area/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(areaService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/area/list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Area> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(areaService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/area/edit/auth")
    @ResponseBody
    public R edit(Area area) {
        if (Cools.isEmpty(area)){
            return R.error();
        }
        if (null == area.getId()){
            areaService.insert(area);
        } else {
            areaService.updateById(area);
        }
        return R.ok();
    }

    @RequestMapping(value = "/area/add/auth")
    @ResponseBody
    public R add(Area area) {
        areaService.insert(area);
        return R.ok();
    }

	@RequestMapping(value = "/area/update/auth")
    @ResponseBody
    public R update(Area area){
        if (Cools.isEmpty(area) || null==area.getId()){
            return R.error();
        }
        areaService.updateById(area);
        return R.ok();
    }

    @RequestMapping(value = "/area/delete/auth")
    @ResponseBody
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        areaService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/area/export/auth")
    @ResponseBody
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Area> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("area"));
        convert(map, wrapper);
        List<Area> list = areaService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/areaQuery/auth")
    @ResponseBody
    public R query(String condition) {
        EntityWrapper<Area> wrapper = new EntityWrapper<>();
        wrapper.like("name", condition);
        Page<Area> page = areaService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Area area : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", area.getId());
            map.put("value", area.getName());
            result.add(map);
        }
        return R.ok(result);
    }

}
