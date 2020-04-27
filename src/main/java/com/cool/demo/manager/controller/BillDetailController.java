package com.cool.demo.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.common.CodeRes;
import com.cool.demo.common.entity.Parameter;
import com.cool.demo.common.model.OutStockDto;
import com.cool.demo.common.model.OutStockResult;
import com.cool.demo.manager.entity.Bill;
import com.cool.demo.manager.entity.BillDetail;
import com.cool.demo.manager.entity.BillDto;
import com.cool.demo.manager.entity.outStockDto;
import com.cool.demo.manager.service.BillDetailService;
import com.cool.demo.manager.service.BillService;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.entity.UserLogin;
import com.cool.demo.system.service.UserLoginService;
import com.cool.demo.system.service.UserService;
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

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class BillDetailController extends AbstractBaseController {

    @Autowired
    private BillService billService;
    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserService userService;

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

    @RequestMapping("/outStockPrint")
    public String outStockPrint() {
        return "billDetail/outStockPrint";
    }

    @RequestMapping("/spell_list")
    public String spell() {
        return "billDetail/spell_list";
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
        wrapper.eq("use_status", 1);
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
            billDetail.setUseStatus((short) 1);
            billDetailService.insert(billDetail);
        } else {
            billDetail.setUseStatus((short) 1);
            billDetailService.updateById(billDetail);
        }
        return R.ok();
    }

    @RequestMapping(value = "/billDetail/add/auth")
    @ResponseBody
    @ManagerAuth
    public R add(BillDetail billDetail) {
        billDetail.setUseStatus((short) 1);
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
        BillDto dto = new BillDto(bill.getId(), detail.getId(), bill.getCustomer(), bill.getColor(), detail.getCreateTime() == null ? null : DateUtils.convert(detail.getCreateTime(), DateUtils.yyyyMMdd_F), detail.getAmount(), bill.getModelType(), bill.getSeq(), null, bill.getBoxCheck(), detail.getBoxNumber(), bill.getRemark());
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
                BillDto dto = new BillDto(bill.getId(), details.get(i).getId(), bill.getCustomer(), bill.getColor(), details.get(i).getCreateTime() == null ? null : DateUtils.convert(details.get(i).getCreateTime(), DateUtils.yyyyMMdd_F), details.get(i).getAmount(), bill.getModelType(), bill.getSeq(), "/bill/qrcode?id=" + url, bill.getBoxCheck(), details.get(i).getBoxNumber(), bill.getRemark());
                list.add(dto);
            }
        }
        return R.ok(Cools.add("list", list));
    }
//
//    @ManagerAuth
//    @Transactional
//    @RequestMapping(value = "/billDetail/boxingQuery")
//    @ResponseBody
//    public R boxingQuery(String url, String boxer) {
//
//        if ("".equals(url)) {
//            return R.error("箱号或者订单编号为空");
//        }
//        String id = null;
//        try {
//            url = "{" + (url.substring(url.indexOf("?") + 1, url.length())).replace("=", ":") + "}";
//            JSONObject jsonObject = JSONObject.parseObject(url);
//            id = jsonObject.getString("id");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return R.error("二维码信息错误");
//        }
//        BillDetail detail = billDetailService.selectById(id);
//
//        if (!Cools.isEmpty(detail)) {
//            detail.setBoxer(boxer);
//            detail.setBoxingTime(new Date());
//            detail.setStatus((short) 2);
//            boolean b = billDetailService.updateById(detail);
//            if (b) {
//                //根据出入人员名称查询出库数量
//                int count = billDetailService.getStatistics(detail.getBoxer());
//                Bill bill = billService.selectById(detail.getBillId());
//                BillDto dto = new BillDto(bill.getId(), detail.getId(), bill.getCustomer(), bill.getColor(), detail.getCreateTime() == null ? null : DateUtils.convert(detail.getCreateTime(), DateUtils.yyyyMMdd_F), detail.getAmount(), bill.getModelType(), bill.getSeq(), bill.getBoxCheck(), "", detail.getBoxNumber(), detail.getBoxer(), count, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getBoxingTime()));
//                //根据订单ID查询装箱明细信息
//                List<BillDetail> list = billDetailService.selectByBillId(bill.getId());
//                if (list.size() > 0) {
//                    //计数器
//                    int counts = 0;
//                    for (BillDetail billDetail : list) {
//                        if (billDetail.getStatus() == 2) {
//                            counts = counts + 1;
//
//                        } else {
//                            break;
//                        }
//                    }
//                    if (counts != 0 && counts == list.size()) {
//                        bill.setStatus((short) 2);
//                        billService.updateById(bill);
//                    }
//
//                }
//                return R.ok(dto);
//            }
//
//        }
//        return R.error("未查询到信息！");
//    }

    @Transactional
    @ManagerAuth
    @RequestMapping(value = "/billDetail/outStock")
    @ResponseBody
    public R outStock(String url, String outStocker, String amount) {
        if ("".equals(url)) {
            return R.error("箱号或者订单编号为空");
        }
        if ("".equals(amount) || amount == null) {
            return R.error("数量不能为空");
        }
        if (amount.equals("0")) {
            amount = "0";
        }
        //拆箱数量
        int parseInt = Integer.parseInt(amount);
        String id = null;

        //拼接url
        try {
            url = "{" + (url.substring(url.indexOf("?") + 1, url.length())).replace("=", ":") + "}";
            JSONObject jsonObject = JSONObject.parseObject(url);
            id = jsonObject.getString("id");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("二维码信息错误");
        }
        BillDetail detail = billDetailService.selectById(id);
        if (parseInt > detail.getAmount()) {
            return R.error("需要拆箱的数量大于本箱的数量");
        }
        //判断输入的数量是否小于本箱数量，若小于，则拆箱。新增一个新的箱子
        if (parseInt < detail.getAmount() && parseInt != 0) {

            BillDetail detail1 = new BillDetail(detail.getBillId(), detail.getAmount() - parseInt, detail.getBoxNumber(), "", new Date(), (short) 1, (short) 1);
            detail1.setUnpacking((short) 2);
            billDetailService.insert(detail1);
            //设置拆箱数量
            detail.setAmount(parseInt);
        }
        if (!Cools.isEmpty(detail)) {
            detail.setOutStocker(outStocker); //出库人员
            detail.setOutStockTime(new Date()); //出库时间
            detail.setStatus((short) 3);//状态
            detail.setUnpacking((short) 1); //拆箱状态
            boolean b = billDetailService.updateById(detail);
            if (b) {
                //根据出入人员名称查询出库数量
                int count = billDetailService.getOutStockStatistics(detail.getOutStocker());
                Bill bill = billService.selectById(detail.getBillId());//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getOutStockTime())
                BillDto dto = new BillDto(bill.getId(), detail.getId(), bill.getCustomer(), bill.getColor(), detail.getCreateTime() == null ? null : DateUtils.convert(detail.getCreateTime(), DateUtils.yyyyMMdd_F), detail.getAmount(), bill.getModelType(), bill.getSeq(), bill.getBoxCheck(), "", detail.getBoxNumber(), detail.getBoxer(), count, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getOutStockTime()));
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
    public R spellList(String ids) throws ParseException {

        if (Cools.isEmpty(ids)) {
            return R.error("请选择装箱信息!");
        }
        String[] split = ids.split(",");
        ArrayList<Object> list = new ArrayList<>();
        //查询箱子详情根据ID在数组中的记录
        List<BillDetail> details = billDetailService.selectList(new EntityWrapper<BillDetail>().in("id", split));
        //拼箱方法
        BillDto merge = merge(details);
        //根据详情ID查询相亲记录
        BillDetail detail = billDetailService.selectById(merge.getBillDetailId());
        detail.setAmount(merge.getAmount());
        //修改改变后箱子的数量
        boolean b = billDetailService.updateById(detail);

        if (b) {
            list.add(merge);
            return R.ok(Cools.add("list", list));

        }
        return R.error();

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

    public BillDto merge(List<BillDetail> details) {
        if (Cools.isEmpty(details)) {
            return null;
        }

        String seq = null;
        BillDto dto = null;
        // 初始化数量
        int initAmount = 0;
        for (BillDetail detail : details) {
            Bill bill = billService.selectById(detail.getBillId());
            boolean isCompareable = true;
            if (null == seq) {
                // 初始化数量
                initAmount = bill.getUnit();
                seq = bill.getSeq();
                isCompareable = false;
            }
            // 批次号不同不能拼单
            if (!seq.equals(bill.getSeq()) && isCompareable) {
                throw new CoolException(CodeRes.BILL_20001);
            }
            // init
            if (dto == null) {
                String billQrCodeUrl = Parameter.get().getBillQrCodeUrl();
                String url = billQrCodeUrl.concat("?id=").concat(String.valueOf(detail.getId()));

                dto = new BillDto(bill.getId(), detail.getId(), bill.getCustomer(), bill.getColor(), detail.getCreateTime() == null ? null : DateUtils.convert(detail.getCreateTime(), DateUtils.yyyyMMdd_F), detail.getAmount(), bill.getModelType(), bill.getSeq(), "/bill/qrcode?id=" + url, bill.getBoxCheck(), detail.getBoxNumber(), bill.getRemark());
                dto.setAmount(0);
            }
            //当前这一箱子的数量
            int cacheAmount = dto.getAmount() + detail.getAmount();

//            detail.setStatus(isCompareable?(short) 1:(short)0);
            detail.setUseStatus(isCompareable ? (short) 0 : (short) 1);
            detail.setSpellStatus((short) 1);
            /**
             * detail.set拼单状态(已拼单）
             * detail.update();
             *
             */

            billDetailService.updateById(detail);

            if (cacheAmount > initAmount) {
                // 拼单多余数量重新生成新的一箱（要显示的）
                BillDetail billDetail = new BillDetail(
                        bill.getId(),    // 所属订单[非空]
                        cacheAmount - initAmount,    // 数量[非空]
                        0,    // 箱号[非空]
                        null,    // 装箱员
                        new Date(),    // 添加时间[非空]
                        (short) 1,    // 状态[非空]
                        (short) 1,   //装箱状态
                        (short) 1   //使用状态
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

    /**
     * 修改拆箱状态
     *
     * @param
     * @return
     */
    @RequestMapping(value = "billDetail/unpacking")
    @ResponseBody
    public R unpacking(String id, String unpacking) {
        if (Cools.isEmpty(id)) {
            return R.error("请选择您需要修改的记录");
        }
        int detailId = Integer.parseInt(id);
        BillDetail detail = billDetailService.selectById(detailId);
        if (unpacking.equals("0")) {
            detail.setUnpacking((short) 2);
        } else {
            detail.setUnpacking((short) 0);
        }

        boolean b = billDetailService.updateById(detail);
        if (b) {
            return R.ok("修改成功");
        } else {
            return R.error("修改失败");
        }
    }

    /**
     * 判断是否需要拆箱
     *
     * @param url
     * @return
     */
    @RequestMapping(value = "billDetail/isUnpacking")
    @ResponseBody
    public R isUnpacking(String url) {
        if (Cools.isEmpty(url)) {
            return R.error("传入为空");
        }
        String id = null;
        //拼接url
        try {
            url = "{" + (url.substring(url.indexOf("?") + 1, url.length())).replace("=", ":") + "}";
            JSONObject jsonObject = JSONObject.parseObject(url);
            id = jsonObject.getString("id");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("二维码信息错误");
        }

        BillDetail detail = billDetailService.selectById(Long.parseLong(id));
        if (detail.getUnpacking() == (short) 0) {
            return R.ok();
        }
        return R.error("不可拆箱");
    }


    @RequestMapping(value = "/billDetail/outStockPrintList")
    @ResponseBody
    public R outStockPrint(String ids, HttpServletRequest request) {
///
        if (Cools.isEmpty(ids)) {
            return R.error("传入ID为空");
        }
        //字符串转数组 ID
        String[] split = ids.split(",");
        //根据ID 查询装箱记录集合
        List<BillDetail> details = billDetailService.selectBatchIds(Arrays.asList(split));
        //获取登陆用户token
        String token = request.getHeader("token");
        UserLogin userLogin = userLoginService.selectOne(new EntityWrapper<UserLogin>().eq("token", token));
        User user = userService.selectById(userLogin.getUserId());
        ArrayList<outStockDto> outStockDtos = new ArrayList<>();
        //单据号码  当前日期
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        format = format.replace("-", "");
        for (BillDetail detail : details) {
            if (detail.getStatus() == (short) 3) {
                Bill bill = billService.selectById(detail.getBillId());
                outStockDto outStockDto = new outStockDto(
                        bill.getId(),
                        detail.getId(),
                        format,
                        bill.getCustomer(),
                        "",
                        bill.getModelType() + "   " + bill.getColor(),
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                        detail.getAmount(),
                        0.0,
                        bill.getSeq(),
                        detail.getBoxNumber(),
                         user.getUsername(),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getOutStockTime()),
                        bill.getRemark());
                outStockDtos.add(outStockDto);
            }
        }
        if (outStockDtos.size() == 0) {
            return R.error("尚未出库!");
        }

        int singleSize = 10;
        int pageCount = outStockDtos.size() / singleSize;

        OutStockResult result = new OutStockResult();

        if (outStockDtos.size() <=singleSize) {
            OutStockDto dto0 = new OutStockDto();
            dto0.setIndex(1);
            dto0.setSize(1);
            dto0.setStockDtos(outStockDtos);
            result.getOutStockDtos().add(dto0);
        } else {
            int size=outStockDtos.size();
            int s=0;
            for (int i = 0; i < size; i++) {
                if ((i+1) % singleSize == 0 && i != 0) {

                    OutStockDto dto = new OutStockDto();
                    dto.setIndex((i + 1) / singleSize);
                    dto.setSize((size/ singleSize)+1);
                    dto.setStockDtos(outStockDtos.subList(s, i+1));
                    s+=singleSize;
                    result.getOutStockDtos().add(dto);

                } else if(i==size-1){
                    OutStockDto dto1 = new OutStockDto();
                    dto1.setIndex((i + 1) / singleSize +1);
                    dto1.setSize((size/ singleSize)+1);
                    dto1.setStockDtos(outStockDtos.subList(i, outStockDtos.size() ));
                   // result.getOutStockDtos().clear();
                    result.getOutStockDtos().add(dto1);
                }
                if (i % singleSize != 0 || i == 0) {

                   continue;
                }
            }
        }
        return R.ok(result);
    }



}
