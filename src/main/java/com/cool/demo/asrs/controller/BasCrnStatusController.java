package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.BasCrnStatus;
import com.cool.demo.asrs.service.BasCrnStatusService;
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
public class BasCrnStatusController extends BaseController {

    @Autowired
    private BasCrnStatusService basCrnStatusService;

    @RequestMapping(value = "/basCrnStatus/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(basCrnStatusService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/basCrnStatus/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<BasCrnStatus> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
//        wrapper.orderBy("id", false);
        return R.ok(basCrnStatusService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/basCrnStatus/add/auth")
    @ManagerAuth
    public R add(BasCrnStatus basCrnStatus) {
        basCrnStatus.setModiUser(getUserId());
        basCrnStatus.setModiTime(new Date());
        basCrnStatus.setAppeUser(getUserId());
        basCrnStatus.setAppeTime(new Date());
        basCrnStatusService.insert(basCrnStatus);
        return R.ok();
    }

	@RequestMapping(value = "/basCrnStatus/update/auth")
	@ManagerAuth
    public R update(BasCrnStatus basCrnStatus){
        if (Cools.isEmpty(basCrnStatus) || null==basCrnStatus.getStsNo()){
            return R.error();
        }
        basCrnStatus.setModiUser(getUserId());
        basCrnStatus.setModiTime(new Date());
        basCrnStatusService.updateById(basCrnStatus);
        return R.ok();
    }

    @RequestMapping(value = "/basCrnStatus/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<BasCrnStatus> list = JSONArray.parseArray(param, BasCrnStatus.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (BasCrnStatus entity : list){
            basCrnStatusService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/basCrnStatus/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<BasCrnStatus> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("basCrnStatus"));
        convert(map, wrapper);
        List<BasCrnStatus> list = basCrnStatusService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/basCrnStatusQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<BasCrnStatus> wrapper = new EntityWrapper<>();
        wrapper.like("sts_desc", condition);
        Page<BasCrnStatus> page = basCrnStatusService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (BasCrnStatus basCrnStatus : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", basCrnStatus.getStsNo());
            map.put("value", basCrnStatus.getStsDesc());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/basCrnStatus/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<BasCrnStatus> wrapper = new EntityWrapper<BasCrnStatus>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != basCrnStatusService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(BasCrnStatus.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
