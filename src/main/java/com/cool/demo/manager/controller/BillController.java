package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.common.DateUtils;
import com.cool.demo.manager.entity.Bill;
import com.cool.demo.manager.service.BillService;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class BillController extends AbstractBaseController {

    @Autowired
    private BillService billService;

    @RequestMapping("/bill")
    public String index(){
        return "bill/bill";
    }

    @RequestMapping("/bill_detail")
    public String detail(){
        return "bill/bill_detail";
    }

    @RequestMapping(value = "/bill/{id}/auth")
    @ResponseBody
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(billService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/bill/list/auth")
    @ResponseBody
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<Bill> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(billService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/bill/edit/auth")
    @ResponseBody
    @ManagerAuth
    public R edit(Bill bill) {
        if (Cools.isEmpty(bill)){
            return R.error();
        }
        if (null == bill.getId()){
            billService.insert(bill);
        } else {
            billService.updateById(bill);
        }
        return R.ok();
    }

    @RequestMapping(value = "/bill/add/auth")
    @ResponseBody
    @ManagerAuth
    public R add(Bill bill) {
        billService.insert(bill);
        return R.ok();
    }

	@RequestMapping(value = "/bill/update/auth")
    @ResponseBody
    public R update(Bill bill){
        if (Cools.isEmpty(bill) || null==bill.getId()){
            return R.error();
        }
        billService.updateById(bill);
        return R.ok();
    }

    @RequestMapping(value = "/bill/delete/auth")
    @ResponseBody
    @ManagerAuth
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        billService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/bill/export/auth")
    @ResponseBody
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<Bill> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("bill"));
        convert(map, wrapper);
        List<Bill> list = billService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/billQuery/auth")
    @ResponseBody
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<Bill> wrapper = new EntityWrapper<>();
        wrapper.like("seq", condition);
        Page<Bill> page = billService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Bill bill : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", bill.getId());
            map.put("value", bill.getSeq());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "bill/print")
    @ResponseBody
    @ManagerAuth
    public R print() {


        return R.ok();
    }

}
