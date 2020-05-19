package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.RowLastno;
import com.cool.demo.asrs.service.RowLastnoService;
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
public class RowLastnoController extends BaseController {

    @Autowired
    private RowLastnoService rowLastnoService;

    @RequestMapping(value = "/rowLastno/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(rowLastnoService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/rowLastno/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<RowLastno> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
//        wrapper.orderBy("id", false);
        return R.ok(rowLastnoService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/rowLastno/add/auth")
    @ManagerAuth
    public R add(RowLastno rowLastno) {
        rowLastno.setModiUser(getUserId());
        rowLastno.setModiTime(new Date());
        rowLastno.setAppeUser(getUserId());
        rowLastno.setAppeTime(new Date());
        rowLastnoService.insert(rowLastno);
        return R.ok();
    }

	@RequestMapping(value = "/rowLastno/update/auth")
	@ManagerAuth
    public R update(RowLastno rowLastno){
        if (Cools.isEmpty(rowLastno) || null==rowLastno.getWhsType()){
            return R.error();
        }
        rowLastno.setModiUser(getUserId());
        rowLastno.setModiTime(new Date());
        rowLastnoService.updateById(rowLastno);
        return R.ok();
    }

    @RequestMapping(value = "/rowLastno/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<RowLastno> list = JSONArray.parseArray(param, RowLastno.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (RowLastno entity : list){
            rowLastnoService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/rowLastno/export/auth")
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<RowLastno> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("rowLastno"));
        convert(map, wrapper);
        List<RowLastno> list = rowLastnoService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/rowLastnoQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<RowLastno> wrapper = new EntityWrapper<>();
        wrapper.like("whsType", condition);
        Page<RowLastno> page = rowLastnoService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (RowLastno rowLastno : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", rowLastno.getWhsType());
            map.put("value", rowLastno.getWhsType());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/rowLastno/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<RowLastno> wrapper = new EntityWrapper<RowLastno>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != rowLastnoService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(RowLastno.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

}
