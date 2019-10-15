package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.common.DateUtils;
import com.cool.demo.manager.entity.Remark;
import com.cool.demo.manager.service.RemarkService;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class RemarkController extends AbstractBaseController {

    @Autowired
    private RemarkService remarkService;

    @RequestMapping("/remark")
    public String index(){
        return "remark/remark";
    }

    @RequestMapping("/remark_detail")
    public String detail(){
        return "remark/remark_detail";
    }

    @RequestMapping(value = "/remark/{id}/auth")
    @ResponseBody
    public R get(@PathVariable("id") Long id) {
        return R.ok(remarkService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/remark/list/auth")
    @ResponseBody
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Remark> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        return R.ok(remarkService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/remark/edit/auth")
    @ResponseBody
    public R edit(Remark remark) {
        if (Cools.isEmpty(remark)){
            return R.error();
        }
        if (null == remark.getId()){
            remarkService.insert(remark);
        } else {
            remarkService.updateById(remark);
        }
        return R.ok();
    }

    @RequestMapping(value = "/remark/add/auth")
    @ResponseBody
    public R add(Remark remark) {
        remarkService.insert(remark);
        return R.ok();
    }

	@RequestMapping(value = "/remark/update/auth")
    @ResponseBody
    public R update(Remark remark){
        if (Cools.isEmpty(remark) || null==remark.getId()){
            return R.error();
        }
        remarkService.updateById(remark);
        return R.ok();
    }

    @RequestMapping(value = "/remark/delete/auth")
    @ResponseBody
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        remarkService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/remark/export/auth")
    @ResponseBody
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Remark> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("remark"));
        convert(map, wrapper);
        List<Remark> list = remarkService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/remarkQuery/auth")
    @ResponseBody
    public R query(String condition) {
        EntityWrapper<Remark> wrapper = new EntityWrapper<>();
        wrapper.like("content", condition);
        List<Remark> list = remarkService.selectList(wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Remark remark : list){
            Map<String, Object> map = new HashMap<>();
            map.put("id", remark.getId());
            map.put("value", remark.getContent());
            result.add(map);
        }
        return R.ok(result);
    }

}
