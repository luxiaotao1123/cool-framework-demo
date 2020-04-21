package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.common.DateUtils;
import com.cool.demo.manager.entity.Spell;
import com.cool.demo.manager.service.SpellService;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class SpellController extends AbstractBaseController {

    @Autowired
    private SpellService spellService;

    @RequestMapping("/spell")
    public String index(){
        return "spell/spell";
    }

    @RequestMapping("/spell_detail")
    public String detail(){
        return "spell/spell_detail";
    }

    @RequestMapping(value = "/spell/{id}/auth")
    @ResponseBody
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(spellService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/spell/list/auth")
    @ResponseBody
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Spell> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(spellService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/spell/edit/auth")
    @ResponseBody
    @ManagerAuth
    public R edit(Spell spell) {
        if (Cools.isEmpty(spell)){
            return R.error();
        }
        if (null == spell.getId()){
            spellService.insert(spell);
        } else {
            spellService.updateById(spell);
        }
        return R.ok();
    }

    @RequestMapping(value = "/spell/add/auth")
    @ResponseBody
    @ManagerAuth
    public R add(Spell spell) {
        spellService.insert(spell);
        return R.ok();
    }

	@RequestMapping(value = "/spell/update/auth")
    @ResponseBody
    public R update(Spell spell){
        if (Cools.isEmpty(spell) || null==spell.getId()){
            return R.error();
        }
        spellService.updateById(spell);
        return R.ok();
    }

    @RequestMapping(value = "/spell/delete/auth")
    @ResponseBody
    @ManagerAuth
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        spellService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/spell/export/auth")
    @ResponseBody
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Spell> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("spell"));
        convert(map, wrapper);
        List<Spell> list = spellService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/spellQuery/auth")
    @ResponseBody
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<Spell> wrapper = new EntityWrapper<>();
        wrapper.like("id", condition);
        Page<Spell> page = spellService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Spell spell : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", spell.getId());
            map.put("value", spell.getId());
            result.add(map);
        }
        return R.ok(result);
    }

}
