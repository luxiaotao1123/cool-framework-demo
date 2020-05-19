package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.BasLocType;
import com.cool.demo.asrs.service.BasLocTypeService;
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
public class BasLocTypeController extends BaseController {

    @Autowired
    private BasLocTypeService basLocTypeService;

    @RequestMapping(value = "/basLocType/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(basLocTypeService.selectById(id));
    }

    @RequestMapping(value = "/basLocType/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<BasLocType> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
//        wrapper.orderBy("id", false);
        return R.ok(basLocTypeService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/basLocType/edit/auth")
    @ManagerAuth
    public R edit(BasLocType basLocType) {
        if (Cools.isEmpty(basLocType)){
            return R.error();
        }
        if (null == basLocType.getLocSts()){
            basLocTypeService.insert(basLocType);
        } else {
            basLocTypeService.updateById(basLocType);
        }
        return R.ok();
    }

    @RequestMapping(value = "/basLocType/add/auth")
    @ManagerAuth
    public R add(BasLocType basLocType) {
        basLocType.setModiUser(getUserId());
        basLocType.setModiTime(new Date());
        basLocType.setAppeUser(getUserId());
        basLocType.setAppeTime(new Date());
        basLocTypeService.insert(basLocType);
        return R.ok();
    }

	@RequestMapping(value = "/basLocType/update/auth")
    public R update(BasLocType basLocType){
        if (Cools.isEmpty(basLocType) || null==basLocType.getLocSts()){
            return R.error();
        }
        basLocType.setModiUser(getUserId());
        basLocType.setModiTime(new Date());
        basLocTypeService.updateById(basLocType);
        return R.ok();
    }

    @RequestMapping(value = "/basLocType/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<BasLocType> list = JSONArray.parseArray(param, BasLocType.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (BasLocType entity : list){
            basLocTypeService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/basLocType/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<BasLocType> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("basLocType"));
        convert(map, wrapper);
        List<BasLocType> list = basLocTypeService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/basLocTypeQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<BasLocType> wrapper = new EntityWrapper<>();
        wrapper.like("loc_desc", condition);
        Page<BasLocType> page = basLocTypeService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (BasLocType basLocType : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", basLocType.getLocSts());
            map.put("value", basLocType.getLocDesc());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/basLocType/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<BasLocType> wrapper = new EntityWrapper<BasLocType>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != basLocTypeService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(BasLocType.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
