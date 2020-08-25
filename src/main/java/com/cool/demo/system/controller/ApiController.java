package com.cool.demo.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import com.cool.demo.common.web.BaseController;
import com.cool.demo.system.entity.Api;
import com.cool.demo.system.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ApiController extends BaseController {

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "/api/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(apiService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/api/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Api> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(apiService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/api/edit/auth")
    @ManagerAuth
    public R edit(Api api) {
        if (Cools.isEmpty(api)){
            return R.error();
        }
        if (null == api.getId()){
            apiService.insert(api);
        } else {
            apiService.updateById(api);
        }
        return R.ok();
    }

    @RequestMapping(value = "/api/add/auth")
    @ManagerAuth
    public R add(Api api) {
        apiService.insert(api);
        return R.ok();
    }

	@RequestMapping(value = "/api/update/auth")
    @ManagerAuth
    public R update(Api api){
        if (Cools.isEmpty(api) || null==api.getId()){
            return R.error();
        }
        apiService.updateById(api);
        return R.ok();
    }

    @RequestMapping(value = "/api/delete/auth")
    @ManagerAuth
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        apiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/api/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Api> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("api"));
        convert(map, wrapper);
        List<Api> list = apiService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/apiQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<Api> wrapper = new EntityWrapper<>();
        wrapper.like("id", condition);
        Page<Api> page = apiService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Api api : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", api.getId());
            map.put("value", api.getId());
            result.add(map);
        }
        return R.ok(result);
    }

}
