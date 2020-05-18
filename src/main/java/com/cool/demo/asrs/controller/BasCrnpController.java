package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.BasCrnp;
import com.cool.demo.asrs.service.BasCrnpService;
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
public class BasCrnpController extends BaseController {

    @Autowired
    private BasCrnpService basCrnpService;

    @RequestMapping(value = "/basCrnp/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(basCrnpService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/basCrnp/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<BasCrnp> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
//        wrapper.orderBy("id", false);
        return R.ok(basCrnpService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/basCrnp/add/auth")
    @ManagerAuth
    public R add(BasCrnp basCrnp) {
        basCrnp.setModiUser(getUserId());
        basCrnp.setModiTime(new Date());
        basCrnp.setAppeUser(getUserId());
        basCrnp.setAppeTime(new Date());
        basCrnpService.insert(basCrnp);
        return R.ok();
    }

	@RequestMapping(value = "/basCrnp/update/auth")
	@ManagerAuth
    public R update(BasCrnp basCrnp){
        if (Cools.isEmpty(basCrnp) || null==basCrnp.getCrnNo()){
            return R.error();
        }
        basCrnp.setModiUser(getUserId());
        basCrnp.setModiTime(new Date());
        basCrnpService.updateById(basCrnp);
        return R.ok();
    }

    @RequestMapping(value = "/basCrnp/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<BasCrnp> list = JSONArray.parseArray(param, BasCrnp.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (BasCrnp entity : list){
            basCrnpService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/basCrnp/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<BasCrnp> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("basCrnp"));
        convert(map, wrapper);
        List<BasCrnp> list = basCrnpService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/basCrnpQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<BasCrnp> wrapper = new EntityWrapper<>();
        wrapper.like("crnNo", condition);
        Page<BasCrnp> page = basCrnpService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (BasCrnp basCrnp : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", basCrnp.getCrnNo());
            map.put("value", basCrnp.getCrnNo());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/basCrnp/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<BasCrnp> wrapper = new EntityWrapper<BasCrnp>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != basCrnpService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(BasCrnp.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
