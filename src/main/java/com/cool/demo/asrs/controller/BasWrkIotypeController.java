package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.BasWrkIotype;
import com.cool.demo.asrs.service.BasWrkIotypeService;
import com.cool.demo.common.web.BaseController;
import com.core.annotations.ManagerAuth;
import com.core.common.BaseRes;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BasWrkIotypeController extends BaseController {

    @Autowired
    private BasWrkIotypeService basWrkIotypeService;

    @RequestMapping(value = "/basWrkIotype/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(basWrkIotypeService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/basWrkIotype/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<BasWrkIotype> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
//        wrapper.orderBy("id", false);
        return R.ok(basWrkIotypeService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/basWrkIotype/edit/auth")
    @ManagerAuth
    public R edit(BasWrkIotype basWrkIotype) {
        if (Cools.isEmpty(basWrkIotype)){
            return R.error();
        }
        if (null == basWrkIotype.getIoType()){
            basWrkIotypeService.insert(basWrkIotype);
        } else {
            basWrkIotypeService.updateById(basWrkIotype);
        }
        return R.ok();
    }

    @RequestMapping(value = "/basWrkIotype/add/auth")
    @ManagerAuth
    public R add(BasWrkIotype basWrkIotype) {
        basWrkIotype.setModiUser(getUserId());
        basWrkIotype.setModiTime(new Date());
        basWrkIotype.setAppeUser(getUserId());
        basWrkIotype.setAppeTime(new Date());
        basWrkIotypeService.insert(basWrkIotype);
        return R.ok();
    }

	@RequestMapping(value = "/basWrkIotype/update/auth")
    public R update(BasWrkIotype basWrkIotype){
        if (Cools.isEmpty(basWrkIotype) || null==basWrkIotype.getIoType()){
            return R.error();
        }
        basWrkIotype.setModiUser(getUserId());
        basWrkIotype.setModiTime(new Date());
        basWrkIotypeService.updateById(basWrkIotype);
        return R.ok();
    }

    @RequestMapping(value = "/basWrkIotype/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<BasWrkIotype> list = JSONArray.parseArray(param, BasWrkIotype.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (BasWrkIotype entity : list){
            basWrkIotypeService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/basWrkIotype/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<BasWrkIotype> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("basWrkIotype"));
        convert(map, wrapper);
        List<BasWrkIotype> list = basWrkIotypeService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/basWrkIotypeQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<BasWrkIotype> wrapper = new EntityWrapper<>();
        wrapper.like("io_desc", condition);
        Page<BasWrkIotype> page = basWrkIotypeService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (BasWrkIotype basWrkIotype : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", basWrkIotype.getIoType());
            map.put("value", basWrkIotype.getIoDesc());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/basWrkIotype/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<BasWrkIotype> wrapper = new EntityWrapper<BasWrkIotype>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != basWrkIotypeService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(BasWrkIotype.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
