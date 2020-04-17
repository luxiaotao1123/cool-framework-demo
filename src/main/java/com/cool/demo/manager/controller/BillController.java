package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.common.CodeRes;
import com.cool.demo.common.entity.Parameter;
import com.cool.demo.common.utils.QrCode;
import com.cool.demo.manager.entity.Bill;
import com.cool.demo.manager.entity.BillDetail;
import com.cool.demo.manager.entity.BillDto;
import com.cool.demo.manager.service.BillDetailService;
import com.cool.demo.manager.service.BillService;
import com.core.annotations.ManagerAuth;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import com.core.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

@Controller
public class BillController extends AbstractBaseController {

    @Autowired
    private BillService billService;
    @Autowired
    private BillDetailService billDetailService;

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
            // 剩余数量
            int residue = 0;
            int i = bill.getTotal() / bill.getUnit();
            if (bill.getTotal() > bill.getUnit() * i) {
                residue = bill.getTotal() - bill.getUnit() * i;
                i += 1;
            }
            for (int j = 1; j<=i; j++){
                BillDetail billDetail;
                // 最后一箱
                if (residue > 0 && j==i) {
                    billDetail = new BillDetail(
                            bill.getId(),    // 所属订单[非空]
                            residue,    // 数量[非空]
                            j,    // 箱号[非空]
                            null,    // 装箱员
                            new Date(),    // 添加时间[非空]
                            (short) 1    // 状态[非空]
                    );

                } else {
                    billDetail = new BillDetail(
                            bill.getId(),    // 所属订单[非空]
                            bill.getUnit(),    // 数量[非空]
                            j,    // 箱号[非空]
                            null,    // 装箱员
                            new Date(),    // 添加时间[非空]
                            (short) 1    // 状态[非空]
                    );
                }
                billDetailService.insert(billDetail);
            }
        } else {
            billService.updateById(bill);
            billDetailService.delete(new EntityWrapper<BillDetail>().eq("bill_id", bill.getId()));
            // 剩余数量
            int residue = 0;
            int i = bill.getTotal() / bill.getUnit();
            if (bill.getTotal() > bill.getUnit() * i) {
                residue = bill.getTotal() - bill.getUnit() * i;
                i += 1;
            }
            for (int j = 1; j<=i; j++){
                BillDetail billDetail;
                // 最后一箱
                if (residue > 0 && j==i) {
                    billDetail = new BillDetail(
                            bill.getId(),    // 所属订单[非空]
                            residue,    // 数量[非空]
                            j,    // 箱号[非空]
                            null,    // 装箱员
                            new Date(),    // 添加时间[非空]
                            (short) 1    // 状态[非空]
                    );

                } else {
                    billDetail = new BillDetail(
                            bill.getId(),    // 所属订单[非空]
                            bill.getUnit(),    // 数量[非空]
                            j,    // 箱号[非空]
                            null,    // 装箱员
                            new Date(),    // 添加时间[非空]
                            (short) 1    // 状态[非空]
                    );
                }
                billDetailService.insert(billDetail);
            }

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
        for (Integer id : ids) {
            billDetailService.delete(new EntityWrapper<BillDetail>().eq("bill_id", id));
        }
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
    public R print(@RequestParam("id")String id) {
        Bill bill = billService.selectById(id);
        if (null == bill) {
            return R.parse(CodeRes.EMPTY);
        }
        if (bill.getUnit() <= 0) {
            return R.error("当前订单每箱数量错误");
        }
        List<Object> list = new ArrayList<>();
        List<BillDetail> details = billDetailService.selectList(new EntityWrapper<BillDetail>().eq("bill_id", bill.getId()));
        String billQrCodeUrl = Parameter.get().getBillQrCodeUrl();
        for (BillDetail detail : details) {
            String url = billQrCodeUrl.concat("?id=").concat(String.valueOf(detail.getId()));
            BillDto dto = new BillDto(bill.getId(), detail.getId(), bill.getCustomer(), bill.getColor(), detail.getCreateTime() == null ? null : DateUtils.convert(detail.getCreateTime(), DateUtils.yyyyMMdd_F), detail.getAmount(), bill.getModelType(), bill.getSeq(), "/bill/qrcode?id=" + url, bill.getBoxCheck(), bill.getBoxPrefix().concat(String.valueOf(detail.getBoxNumber())));
            list.add(dto);
        }
//        bill.setStatus((short) 2);
        billService.updateById(bill);
        return R.ok(Cools.add("list", list));
    }


    @RequestMapping(value = "bill/qrcode")
    @ResponseBody
    public R qrcode(@RequestParam("id")String id, HttpServletResponse response) throws Exception {
        BufferedImage img = QrCode.createImg(String.valueOf(id));
        if (!ImageIO.write(img, "jpg", response.getOutputStream())) {
            throw new IOException("Could not write an image of format jpg");
        }
        response.getOutputStream().flush();
        response.getOutputStream().close();
        return R.ok();
    }

}
