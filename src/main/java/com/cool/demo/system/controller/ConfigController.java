package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import com.cool.demo.common.entity.Parameter;
import com.cool.demo.common.web.BaseController;
import com.cool.demo.system.entity.Config;
import com.cool.demo.system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ConfigController extends BaseController {

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/config/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(configService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/config/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Config> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(configService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/config/edit/auth")
    @ManagerAuth
    public R edit(Config config) {
        if (Cools.isEmpty(config)){
            return R.error();
        }
        if (config.getType() == 2){
            if (!checkJson(config.getValue())){
                return R.error("json解析失败");
            }
        }
        if (null == config.getId()){
            configService.insert(config);
        } else {
            configService.updateById(config);
            Parameter.reset();
        }
        return R.ok();
    }

    @RequestMapping(value = "/config/add/auth")
    @ManagerAuth
    public R add(Config config) {
        if (config.getType() == 2){
            if (!checkJson(config.getValue())){
                return R.error("json解析失败");
            }
        }
        configService.insert(config);
        return R.ok();
    }

	@RequestMapping(value = "/config/update/auth")
    @ManagerAuth
    public R update(Config config){
        if (Cools.isEmpty(config) || null==config.getId()){
            return R.error();
        }
        if (config.getType() == 2){
            if (!checkJson(config.getValue())){
                return R.error("json解析失败");
            }
        }
        configService.updateById(config);
        Parameter.reset();
        return R.ok();
    }

    @RequestMapping(value = "/config/delete/auth")
    @ManagerAuth
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        configService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/config/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Config> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("config"));
        convert(map, wrapper);
        List<Config> list = configService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/configQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<Config> wrapper = new EntityWrapper<>();
        wrapper.like("id", condition);
        Page<Config> page = configService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Config config : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", config.getId());
            map.put("value", config.getId());
            result.add(map);
        }
        return R.ok(result);
    }

    /**
     * 刷新配置
     */
    @RequestMapping(value = "/config/refresh/auth")
    @ManagerAuth
    public R refresh(){
        Parameter parameter;
        try {
            parameter = Parameter.reset();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
        if (Cools.isEmpty(parameter)){
            return R.error();
        }
        return R.ok();
    }


    private static boolean checkJson(String val){
        Object parse = null;
        try {
            parse = JSON.parse(val);
        } catch (Exception ignore){
        }
        return parse != null;
    }

}
