package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.common.DateUtils;
import com.cool.demo.manager.entity.Risk;
import com.cool.demo.manager.service.RiskService;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class RiskController extends AbstractBaseController {

    @Autowired
    private RiskService riskService;

    @RequestMapping("/risk")
    public String index(){
        return "risk/risk";
    }

    @RequestMapping("/risk_detail")
    public String detail(){
        return "risk/risk_detail";
    }

    @RequestMapping(value = "/risk/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(riskService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/risk/list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Risk> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(riskService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/risk/edit/auth")
    @ResponseBody
    public R edit(Risk risk) {
        if (Cools.isEmpty(risk)){
            return R.error();
        }
        if (null == risk.getId()){
            riskService.insert(risk);
        } else {
            riskService.updateById(risk);
        }
        return R.ok();
    }

    @RequestMapping(value = "/risk/add/auth")
    @ResponseBody
    public R add(Risk risk) {
        riskService.insert(risk);
        return R.ok();
    }

	@RequestMapping(value = "/risk/update/auth")
    @ResponseBody
    public R update(Risk risk){
        if (Cools.isEmpty(risk) || null==risk.getId()){
            return R.error();
        }
        riskService.updateById(risk);
        return R.ok();
    }

    @RequestMapping(value = "/risk/delete/auth")
    @ResponseBody
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        riskService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/risk/export/auth")
    @ResponseBody
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Risk> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("risk"));
        convert(map, wrapper);
        List<Risk> list = riskService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/riskQuery/auth")
    @ResponseBody
    public R query(String condition) {
        EntityWrapper<Risk> wrapper = new EntityWrapper<>();
        wrapper.like("content", condition);
        Page<Risk> page = riskService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Risk risk : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", risk.getId());
            map.put("value", risk.getContent());
            result.add(map);
        }
        return R.ok(result);
    }

}
