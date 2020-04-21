package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.common.CodeRes;
import com.cool.demo.common.entity.Parameter;
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
import com.core.exception.CoolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class BillDetailController extends AbstractBaseController {

    @Autowired
    private BillService billService;
    @Autowired
    private BillDetailService billDetailService;

    @RequestMapping("/billDetail")
    public String index() {
        return "billDetail/billDetail";
    }

    @RequestMapping("/billDetail_detail")
    public String detail() {
        return "billDetail/billDetail_detail";
    }

    @RequestMapping("/repairPrint")
    public String repairPrint() {
        return "billDetail/repairPrint";
    }

    @RequestMapping("/boxing")
    public String boxing() {
        return "billDetail/boxing";
    }

    @RequestMapping("/outStock")
    public String outStock() {
        return "billDetail/outStock";
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
    public R list(@RequestParam(defaultValue = "1") Integer curr,
                  @RequestParam(defaultValue = "10") Integer limit,
                  @RequestParam Map<String, Object> param) {
        excludeTrash(param);
        EntityWrapper<BillDetail> wrapper = new EntityWrapper<>();
        convert(param, wrapper);
        wrapper.orderBy("id", false);
        return R.ok(billDetailService.selectPage(new Page<>(curr, limit), wrapper));
    }

    private void convert(Map<String, Object> map, EntityWrapper wrapper) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        if (Cools.isEmpty(billDetail)) {
            return R.error();
        }
        if (null == billDetail.getId()) {
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
    public R update(BillDetail billDetail) {
        if (Cools.isEmpty(billDetail) || null == billDetail.getId()) {
            return R.error();
        }
        billDetailService.updateById(billDetail);
        return R.ok();
    }

    @RequestMapping(value = "/billDetail/delete/auth")
    @ResponseBody
    @ManagerAuth
    public R delete(Integer[] ids) {
        if (Cools.isEmpty(ids)) {
            return R.error();
        }
        billDetailService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping(value = "/billDetail/export/auth")
    @ResponseBody
    @ManagerAuth
    public R export(@RequestBody JSONObject param) {
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
        for (BillDetail billDetail : page.getRecords()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", billDetail.getId());
            map.put("value", billDetail.getId());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/billDetailQuery/qrcode/get/{id}")
    @ResponseBody
    public R qrcodeGet(@PathVariable("id") Integer id) {
        BillDetail detail = billDetailService.selectById(id);
        if (null == detail) {
            return R.parse(CodeRes.EMPTY);
        }
        Bill bill = billService.selectById(detail.getBillId());
        if (null == bill) {
            return R.parse(CodeRes.EMPTY);
        }
        if (bill.getUnit() <= 0) {
            return R.error("当前订单每箱数量错误");
        }
        List<Object> list = new ArrayList<>();
        BillDto dto = new BillDto(bill.getId(), detail.getId(), bill.getCustomer(), bill.getColor(), detail.getCreateTime() == null ? null : DateUtils.convert(detail.getCreateTime(), DateUtils.yyyyMMdd_F), detail.getAmount(), bill.getModelType(), bill.getSeq(), null, bill.getBoxCheck(),bill.getNumber() );
        list.add(dto);
        return R.ok(Cools.add("list", list));
    }

    @Transactional
    @RequestMapping(value = "/billDetail/updateCreate")
    @ResponseBody
    public R updateCreate(String ids, Date createTime) {
        //判断前端传入端ids是否为空
        if (Cools.isEmpty(ids)) {
            return R.error("传入ID为空");
        }
        String[] split = ids.split(",");
        List<BillDetail> details = new ArrayList<>();
        //根据","将字符串分割成数组
        for (String id : ids.split(",")
        ) {
            //遍历数组根据ID查询订单详情 返回对象
            BillDetail billDetail = billDetailService.selectById(id);
            details.add(billDetail);
        }
        //判断每个订单详情是否为同一个订单下的
        for (int i = 0; i < details.size(); i++) {
            for (int j = 0; j < details.size(); j++) {
                if (!(details.get(i).getBillId().equals(details.get(j).getBillId()))) {
                    return R.error("请选择同一订单");
                } else {
                    //若为同一个订单下的详情,修改生产日期
                    BillDetail billDetail = billDetailService.selectById(details.get(i).getId());
                    billDetail.setCreateTime(createTime);
                    boolean b = billDetailService.updateById(billDetail);
                    if (!b) {
                        return R.error("生产日期修改失败");
                    }
                }
            }
        }

        return R.ok();
    }

    @RequestMapping(value = "/billDetail/print")
    @ResponseBody
    @ManagerAuth
    public R print(@RequestParam("id") String ids) {
        List<BillDetail> details = new ArrayList<>();
        ArrayList<Object> list = new ArrayList<>();
        if (Cools.isEmpty("ids")) {
            return R.error("传入ID为空");
        } else {
            String billQrCodeUrl = Parameter.get().getBillQrCodeUrl();
            String[] split = ids.split(",");
            //根据","将字符串分割成数组
            for (int i = 0; i < split.length; i++) {
                //遍历数组根据ID查询订单详情 返回对象
                BillDetail billDetail = billDetailService.selectById(split[i]);
                details.add(billDetail);
            }
            //生成打印列表
            for (int i = 0; i < details.size(); i++) {
                Bill bill = billService.selectById(details.get(0).getBillId());
                String url = billQrCodeUrl.concat("?id=").concat(String.valueOf(details.get(i).getId()));
                BillDto dto = new BillDto(bill.getId(), details.get(i).getId(), bill.getCustomer(), bill.getColor(), details.get(i).getCreateTime() == null ? null : DateUtils.convert(details.get(i).getCreateTime(), DateUtils.yyyyMMdd_F), details.get(i).getAmount(), bill.getModelType(), bill.getSeq(), "/bill/qrcode?id=" + url, bill.getBoxCheck(),bill.getNumber());
                list.add(dto);
            }
        }
        return R.ok(Cools.add("list", list));
    }

    @ManagerAuth
    @Transactional
    @RequestMapping(value = "/billDetail/boxingQuery")
    @ResponseBody
    public R boxingQuery(String url, String boxer) {

        if ("".equals(url)) {
            return R.error("箱号或者订单编号为空");
        }
        String id = null;
        try {
            url = "{" + (url.substring(url.indexOf("?") + 1, url.length())).replace("=", ":") + "}";
            JSONObject jsonObject = JSONObject.parseObject(url);
            id = jsonObject.getString("id");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("二维码信息错误");
        }
        BillDetail detail = billDetailService.selectById(id);

        if (!Cools.isEmpty(detail)) {
            detail.setBoxer(boxer);
            detail.setBoxingTime(new Date());
            detail.setStatus((short) 2);
            boolean b = billDetailService.updateById(detail);
            if (b) {
                //根据出入人员名称查询出库数量
                int count = billDetailService.getStatistics(detail.getBoxer());
                Bill bill = billService.selectById(detail.getBillId());
                BillDto dto = new BillDto(bill.getId(), detail.getId(), bill.getCustomer(), bill.getColor(), detail.getCreateTime() == null ? null : DateUtils.convert(detail.getCreateTime(), DateUtils.yyyyMMdd_F), detail.getAmount(), bill.getModelType(), bill.getSeq(), bill.getBoxCheck(),"", bill.getNumber(), detail.getBoxer(), count, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getBoxingTime()));
                //根据订单ID查询装箱明细信息
                List<BillDetail> list = billDetailService.selectByBillId(bill.getId());
                if (list.size() > 0) {
                    //计数器
                    int counts = 0;
                    for (BillDetail billDetail : list) {
                        if (billDetail.getStatus() == 2) {
                            counts = counts + 1;

                        } else {
                            break;
                        }
                    }
                    if (counts != 0 && counts == list.size()) {
                        bill.setStatus((short) 2);
                        billService.updateById(bill);
                    }

                }
                return R.ok(dto);
            }

        }
        return R.error("未查询到信息！");
    }

    @ManagerAuth
    @Transactional
    @RequestMapping(value = "/billDetail/outStock")
    @ResponseBody
    public R outStock(String url, String outStocker) {

        if ("".equals(url)) {
            return R.error("箱号或者订单编号为空");
        }
        String id = null;
        try {
            url = "{" + (url.substring(url.indexOf("?") + 1, url.length())).replace("=", ":") + "}";
            JSONObject jsonObject = JSONObject.parseObject(url);
            id = jsonObject.getString("id");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("二维码信息错误");
        }
        BillDetail detail = billDetailService.selectById(id);

        if (!Cools.isEmpty(detail)) {
            detail.setOutStocker(outStocker);
            detail.setOutStockTime(new Date());
            detail.setStatus((short) 3);
            boolean b = billDetailService.updateById(detail);
            if (b) {
                //根据出入人员名称查询出库数量
                int count = billDetailService.getOutStockStatistics(detail.getOutStocker());
                Bill bill = billService.selectById(detail.getBillId());
                BillDto dto = new BillDto(bill.getId(), detail.getId(), bill.getCustomer(), bill.getColor(), detail.getCreateTime() == null ? null : DateUtils.convert(detail.getCreateTime(), DateUtils.yyyyMMdd_F), detail.getAmount(), bill.getModelType(), bill.getSeq(), bill.getBoxCheck(),"", bill.getNumber(), detail.getBoxer(), count, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getBoxingTime()));

                //根据订单ID查询装箱明细信息
                List<BillDetail> list = billDetailService.selectByBillId(bill.getId());

                if (list.size() > 0) {
                    //计数器
                    int counts = 0;
                    for (BillDetail billDetail : list) {
                        if (billDetail.getStatus() == 3) {
                            counts = counts + 1;

                        } else {
                            break;
                        }
                    }
                    if (counts != 0 && counts == list.size()) {
                        bill.setStatus((short) 3);
                        billService.updateById(bill);
                    }

                }
                return R.ok(dto);
            }

        }
        return R.error("未查询到信息！");
    }

    @Transactional
    @ResponseBody
    @RequestMapping(value = "/billDetail/spellList")
    public R spellList(String ids) {

        if (Cools.isEmpty(ids)) {
            return R.error("请选择装箱信息!");
        }
        String[] split = ids.split(",");
        ArrayList<Object> list = new ArrayList<>();

        List<BillDetail> details = billDetailService.selectList(new EntityWrapper<BillDetail>().in("id", split));
        BillDto merge = merge(details);
        list.add(merge);
        return R.ok(Cools.add("list", list));

//
//
//        //根据装箱明细ID查询订单列表
//        List<Bill> list =  billService.selectByDetialId(split);
//        //遍历比对查询是否未同一个订单
//        for (int i = 0; i <list.size() ; i++) {
//            for (int j = 0; j <list.size() ; j++) {
//                if(!list.get(0).getSeq().equals(list.get(j).getSeq())){
//                    return  R.error("请选择同一订单");
//                }
//            }
//        }
//
//        ArrayList<BillDetail> billDetails = new ArrayList<>();
//        for (int i = 0; i < split.length; i++) {
//
//            BillDetail billDetail = billDetailService.selectById(Long.parseLong(split[i]));
//            billDetails.add(billDetail);
//        }
//        //拼单开始
//        Integer util = list.get(0).getUnit();
//        for (int i = 0; i < billDetails.size(); i++) {
//
//            if(billDetails.get(i).getAmount()==0)
//            {
//                billDetailService.deleteById(billDetails.get(i).getId());
//                billDetails.remove(i);
//                --i;
//            }
//            //当前数据的数量
//            billDetails.get(i).getAmount();
//            BillDetail billDetail ;
//            if(!billDetails.get(i).getAmount().equals(util)){
//                //需要补齐的数量=总数-当前数量
//                Integer difference = util-billDetails.get(i).getAmount();
//                //判断需要被拼箱 是否比差数大，如果大于差数 减去被拼箱的数量，负责设置为0
//                if(billDetails.get(i+1).getAmount()>=difference){
//
//                    billDetail=billDetails.get(i);
//                    BillDetail  billDetail2=billDetails.get(i+1);
//                    billDetail.setAmount(billDetail.getAmount()+);
//                    billDetailService.updateById(billDetail);
//                    billDetail2.setAmount(billDetails.get(i+1).getAmount());
//                    billDetailService.updateById(billDetail2);
//
//                    billDetails.get(i+1).setAmount(billDetails.get(i+1).getAmount()-difference);
//                }else
//                {
//                    billDetails.get(i+1).setAmount(0);
//
//                    billDetail=billDetails.get(i);
//                    BillDetail  billDetail2=billDetails.get(i+1);
//                    billDetail.setAmount(billDetail.getAmount()+);
//                    billDetailService.updateById(billDetail);
//                    billDetail2.setAmount(billDetails.get(i+1).getAmount());
//                    billDetailService.updateById(billDetail2);
//                }
//
//            }
//        }
//        return R.ok(billDetails);
    }

    public BillDto merge(List<BillDetail> details){
        if (Cools.isEmpty(details)){
            return null;
        }
        String seq = null;
        BillDto dto = null;
        // 初始化数量
        int initAmount=0;
        for (BillDetail detail : details){
            Bill bill = billService.selectById(detail.getBillId());
            boolean isCompareable = true;
            if (null == seq){
                // 初始化数量
                initAmount = bill.getUnit();
                seq = bill.getSeq();
                isCompareable = false;
            }
            // 批次号不同不能拼单
            if (!seq.equals(bill.getSeq()) && isCompareable){
                throw new CoolException(CodeRes.BILL_20001);
            }
            // init
            if (dto == null) {
                String billQrCodeUrl = Parameter.get().getBillQrCodeUrl();
                String url = billQrCodeUrl.concat("?id=").concat(String.valueOf(detail.getId()));

                dto = new BillDto(bill.getId(), detail.getId(), bill.getCustomer(), bill.getColor(), detail.getCreateTime() == null ? null : DateUtils.convert(detail.getCreateTime(), DateUtils.yyyyMMdd_F), detail.getAmount(), bill.getModelType(), bill.getSeq(), "/bill/qrcode?id=" + url, bill.getBoxCheck(),bill.getNumber());
                dto.setAmount(0);
            }
            //当前这一箱子的数量
            int cacheAmount = dto.getAmount() + detail.getAmount();
            /**
             * detail.set拼单状态(已拼单）
             * detail.update();
             *
             */
            detail.setSpellStatus((short) 1);
            billDetailService.updateById(detail);

            if (cacheAmount > initAmount){
                // 拼单多余数量重新生成新的一箱
                BillDetail billDetail = new BillDetail(
                        bill.getId(),    // 所属订单[非空]
                        cacheAmount - initAmount,    // 数量[非空]
                        0,    // 箱号[非空]
                        null,    // 装箱员
                        new Date(),    // 添加时间[非空]
                        (short) 1    // 状态[非空]
                );
                billDetailService.insert(billDetail);
                dto.setAmount(initAmount);
                return dto;
            } else if (cacheAmount == initAmount) {
                dto.setAmount(initAmount);
                return dto;
            }
            dto.setAmount(cacheAmount);
        }
        return dto;

    }
}
