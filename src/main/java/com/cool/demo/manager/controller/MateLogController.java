package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.common.web.BaseController;
import com.cool.demo.manager.entity.MateLog;
import com.cool.demo.manager.service.MateLogService;
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
public class MateLogController extends BaseController {

    @Autowired
    private MateLogService mateLogService;

    @RequestMapping(value = "/mateLog/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(mateLogService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/mateLog/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam Map<String, Object> param){
        EntityWrapper<MateLog> wrapper = new EntityWrapper<>();
        excludeTrash(param);
        convert(param, wrapper);
        if (!Cools.isEmpty(orderByField)){wrapper.orderBy(humpToLine(orderByField), "asc".equals(orderByType));}
        return R.ok(mateLogService.selectPage(new Page<>(curr, limit), wrapper));
    }

    private void convert(Map<String, Object> map, EntityWrapper wrapper){
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

    @RequestMapping(value = "/mateLog/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<MateLog> list = JSONArray.parseArray(param, MateLog.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (MateLog entity : list){
            mateLogService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/mateLog/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        EntityWrapper<MateLog> wrapper = new EntityWrapper<>();
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        Map<String, Object> map = excludeTrash(param.getJSONObject("mateLog"));
        convert(map, wrapper);
        List<MateLog> list = mateLogService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }


    @RequestMapping(value = "/mateLog/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<MateLog> wrapper = new EntityWrapper<MateLog>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != mateLogService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(MateLog.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
