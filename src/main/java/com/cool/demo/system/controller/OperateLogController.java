package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import com.cool.demo.common.web.BaseController;
import com.cool.demo.system.entity.OperateLog;
import com.cool.demo.system.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OperateLogController extends BaseController {

    @Autowired
    private OperateLogService operateLogService;

    @RequestMapping(value = "/operateLog/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(operateLogService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/operateLog/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<OperateLog> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(operateLogService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/operateLog/edit/auth")
    @ManagerAuth
    public R edit(OperateLog operateLog) {
        if (Cools.isEmpty(operateLog)){
            return R.error();
        }
        if (null == operateLog.getId()){
            operateLogService.insert(operateLog);
        } else {
            operateLogService.updateById(operateLog);
        }
        return R.ok();
    }

    @RequestMapping(value = "/operateLog/add/auth")
    @ManagerAuth
    public R add(OperateLog operateLog) {
        operateLogService.insert(operateLog);
        return R.ok();
    }

	@RequestMapping(value = "/operateLog/update/auth")
    @ManagerAuth
    public R update(OperateLog operateLog){
        if (Cools.isEmpty(operateLog) || null==operateLog.getId()){
            return R.error();
        }
        operateLogService.updateById(operateLog);
        return R.ok();
    }

    @RequestMapping(value = "/operateLog/delete/auth")
    @ManagerAuth
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        operateLogService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/operateLog/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<OperateLog> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("operateLog"));
        convert(map, wrapper);
        List<OperateLog> list = operateLogService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/operateLogQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<OperateLog> wrapper = new EntityWrapper<>();
        wrapper.like("id", condition);
        Page<OperateLog> page = operateLogService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (OperateLog operateLog : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", operateLog.getId());
            map.put("value", operateLog.getId());
            result.add(map);
        }
        return R.ok(result);
    }

}
