package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.BasCrnError;
import com.cool.demo.asrs.service.BasCrnErrorService;
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
public class BasCrnErrorController extends BaseController {

    @Autowired
    private BasCrnErrorService basCrnErrorService;

    @RequestMapping(value = "/basCrnError/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(basCrnErrorService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/basCrnError/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<BasCrnError> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
//        wrapper.orderBy("id", false);
        return R.ok(basCrnErrorService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/basCrnError/add/auth")
    @ManagerAuth
    public R add(BasCrnError basCrnError) {
        basCrnError.setModiUser(getUserId());
        basCrnError.setModiTime(new Date());
        basCrnError.setAppeUser(getUserId());
        basCrnError.setAppeTime(new Date());
        basCrnErrorService.insert(basCrnError);
        return R.ok();
    }

	@RequestMapping(value = "/basCrnError/update/auth")
    @ManagerAuth
    public R update(BasCrnError basCrnError){
        if (Cools.isEmpty(basCrnError) || null==basCrnError.getErrorCode()){
            return R.error();
        }
        basCrnError.setModiUser(getUserId());
        basCrnError.setModiTime(new Date());
        basCrnErrorService.updateById(basCrnError);
        return R.ok();
    }

    @RequestMapping(value = "/basCrnError/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<BasCrnError> list = JSONArray.parseArray(param, BasCrnError.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (BasCrnError entity : list){
            basCrnErrorService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/basCrnError/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<BasCrnError> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("basCrnError"));
        convert(map, wrapper);
        List<BasCrnError> list = basCrnErrorService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/basCrnErrorQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<BasCrnError> wrapper = new EntityWrapper<>();
        wrapper.like("errName", condition);
        Page<BasCrnError> page = basCrnErrorService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (BasCrnError basCrnError : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", basCrnError.getErrorCode());
            map.put("value", basCrnError.getErrName());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/basCrnError/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<BasCrnError> wrapper = new EntityWrapper<BasCrnError>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != basCrnErrorService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(BasCrnError.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
