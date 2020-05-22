package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.StkPlcm;
import com.cool.demo.asrs.service.StkPlcmService;
import com.cool.demo.common.web.BaseController;
import com.core.annotations.ManagerAuth;
import com.core.common.BaseRes;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class StkPlcmController extends BaseController {

    @Autowired
    private StkPlcmService stkPlcmService;

    @RequestMapping(value = "/stkPlcm/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(stkPlcmService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/stkPlcm/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<StkPlcm> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        if (!Cools.isEmpty(orderByField)){wrapper.orderBy(humpToLine(orderByField), "asc".equals(orderByType));}
        return R.ok(stkPlcmService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/stkPlcm/add/auth")
    @ManagerAuth
    public R add(StkPlcm stkPlcm) {
        stkPlcmService.insert(stkPlcm);
        return R.ok();
    }

    @RequestMapping(value = "/stkPlcm/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<StkPlcm> list = JSONArray.parseArray(param, StkPlcm.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (StkPlcm entity : list){
            stkPlcmService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/stkPlcm/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<StkPlcm> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("stkPlcm"));
        convert(map, wrapper);
        List<StkPlcm> list = stkPlcmService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/stkPlcm/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<StkPlcm> wrapper = new EntityWrapper<StkPlcm>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != stkPlcmService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(StkPlcm.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
