package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.StaDesc;
import com.cool.demo.asrs.service.StaDescService;
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
public class StaDescController extends BaseController {

    @Autowired
    private StaDescService staDescService;

    @RequestMapping(value = "/staDesc/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(staDescService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/staDesc/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<StaDesc> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
//        wrapper.orderBy("id", false);
        return R.ok(staDescService.selectPage(new Page<>(curr, limit), wrapper));
    }

    private void convert(Map<String, Object> map, EntityWrapper wrapper){
        for (Map.Entry<String, Object> entry : map.entrySet()){
            if (entry.getKey().endsWith(">")) {
                wrapper.ge(Cools.deleteChar(entry.getKey()), DateUtils.convert(String.valueOf(entry.getValue())));
            } else if (entry.getKey().endsWith("<")) {
                wrapper.le(Cools.deleteChar(entry.getKey()), DateUtils.convert(String.valueOf(entry.getValue())));
            } else {
                wrapper.eq(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
    }

    @RequestMapping(value = "/staDesc/add/auth")
    @ManagerAuth
    public R add(StaDesc staDesc) {
        staDesc.setModiUser(getUserId());
        staDesc.setModiTime(new Date());
        staDesc.setAppeUser(getUserId());
        staDesc.setAppeTime(new Date());
        staDescService.insert(staDesc);
        return R.ok();
    }

	@RequestMapping(value = "/staDesc/update/auth")
	@ManagerAuth
    public R update(StaDesc staDesc){
        if (Cools.isEmpty(staDesc) || null==staDesc.getCrnNo()){
            return R.error();
        }
        staDesc.setModiUser(getUserId());
        staDesc.setModiTime(new Date());
        staDescService.updateById(staDesc);
        return R.ok();
    }

    @RequestMapping(value = "/staDesc/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<StaDesc> list = JSONArray.parseArray(param, StaDesc.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (StaDesc entity : list){
            staDescService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/staDesc/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<StaDesc> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("staDesc"));
        convert(map, wrapper);
        List<StaDesc> list = staDescService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/staDescQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<StaDesc> wrapper = new EntityWrapper<>();
        wrapper.like("crnNo", condition);
        Page<StaDesc> page = staDescService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (StaDesc staDesc : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", staDesc.getCrnNo());
            map.put("value", staDesc.getCrnNo());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/staDesc/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<StaDesc> wrapper = new EntityWrapper<StaDesc>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != staDescService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(StaDesc.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
