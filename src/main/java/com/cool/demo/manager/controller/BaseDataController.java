package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.common.DateUtils;
import com.cool.demo.manager.entity.BaseData;
import com.cool.demo.manager.service.BaseDataService;
import com.core.common.BaseRes;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class BaseDataController extends AbstractBaseController {

    @Autowired
    private BaseDataService baseDataService;

    @RequestMapping("/baseData")
    public String index(){
        return "baseData/baseData";
    }

    @RequestMapping("/baseData_detail")
    public String detail(){
        return "baseData/baseData_detail";
    }

    @RequestMapping(value = "/baseData/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(baseDataService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/baseData/list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<BaseData> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        return R.ok(baseDataService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/baseData/edit/auth")
    @ResponseBody
    public R edit(BaseData baseData) {
        if (Cools.isEmpty(baseData)){
            return R.error();
        }
        if (null == baseData.getId()){
            baseDataService.insert(baseData);
        } else {
            baseDataService.updateById(baseData);
        }
        return R.ok();
    }

    @RequestMapping(value = "/baseData/add/auth")
    @ResponseBody
    public R add(BaseData baseData) {
        baseDataService.insert(baseData);
        return R.ok();
    }

	@RequestMapping(value = "/baseData/update/auth")
    @ResponseBody
    public R update(BaseData baseData){
        if (Cools.isEmpty(baseData) || null==baseData.getId()){
            return R.error();
        }
        baseDataService.updateById(baseData);
        return R.ok();
    }

    @RequestMapping(value = "/baseData/delete/auth")
    @ResponseBody
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        baseDataService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/baseData/export/auth")
    @ResponseBody
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<BaseData> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("baseData"));
        convert(map, wrapper);
        List<BaseData> list = baseDataService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/baseDataQuery/auth")
    @ResponseBody
    public R query(String condition) {
        if (Cools.isEmpty(condition)){
            return R.parse(BaseRes.EMPTY);
        }
        EntityWrapper<BaseData> wrapper = new EntityWrapper<>();
//       wrapper.like("condition", condition).or().like("condition", condition);
        List<BaseData> list = baseDataService.selectList(wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (BaseData baseData : list){
            Map<String, Object> map = new HashMap<>();
            map.put("id", baseData.getId());
//            map.put("value", baseData.getId());
            result.add(map);
        }
        return R.ok(result);
    }

}
