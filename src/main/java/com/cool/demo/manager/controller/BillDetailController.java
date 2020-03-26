package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.common.DateUtils;
import com.cool.demo.manager.entity.BillDetail;
import com.cool.demo.manager.service.BillDetailService;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.R;
import com.core.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class BillDetailController extends AbstractBaseController {

    @Autowired
    private BillDetailService billDetailService;

    @RequestMapping("/billDetail")
    public String index(){
        return "billDetail/billDetail";
    }

    @RequestMapping("/billDetail_detail")
    public String detail(){
        return "billDetail/billDetail_detail";
    }

    @RequestMapping(value = "/billDetail/{id}/auth")
    @ResponseBody
    @ManagerAuth
    public R get(@PathVariable("id") Long id) {
        return R.ok(billDetailService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/billDetail/list/auth")
    @ResponseBody
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam Map<String, Object> param){
        excludeTrash(param);
        EntityWrapper<BillDetail> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(billDetailService.selectPage(new Page<>(curr, limit), wrapper));
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

    @RequestMapping(value = "/billDetail/edit/auth")
    @ResponseBody
    @ManagerAuth
    public R edit(BillDetail billDetail) {
        if (Cools.isEmpty(billDetail)){
            return R.error();
        }
        if (null == billDetail.getId()){
            billDetailService.insert(billDetail);
        } else {
            billDetailService.updateById(billDetail);
        }
        return R.ok();
    }

    @RequestMapping(value = "/billDetail/add/auth")
    @ResponseBody
    @ManagerAuth
    public R add(BillDetail billDetail) {
        billDetailService.insert(billDetail);
        return R.ok();
    }

	@RequestMapping(value = "/billDetail/update/auth")
    @ResponseBody
    public R update(BillDetail billDetail){
        if (Cools.isEmpty(billDetail) || null==billDetail.getId()){
            return R.error();
        }
        billDetailService.updateById(billDetail);
        return R.ok();
    }

    @RequestMapping(value = "/billDetail/delete/auth")
    @ResponseBody
    @ManagerAuth
    public R delete(Integer[] ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        billDetailService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/billDetail/export/auth")
    @ResponseBody
    @ManagerAuth
    public R export(@RequestBody JSONObject param){
        List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
        EntityWrapper<BillDetail> wrapper = new EntityWrapper<>();
        Map<String, Object> map = excludeTrash(param.getJSONObject("billDetail"));
        convert(map, wrapper);
        List<BillDetail> list = billDetailService.selectList(wrapper);
        return R.ok(exportSupport(list, fields));
    }

    @RequestMapping(value = "/billDetailQuery/auth")
    @ResponseBody
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<BillDetail> wrapper = new EntityWrapper<>();
        wrapper.like("id", condition);
        Page<BillDetail> page = billDetailService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (BillDetail billDetail : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", billDetail.getId());
            map.put("value", billDetail.getId());
            result.add(map);
        }
        return R.ok(result);
    }

}
