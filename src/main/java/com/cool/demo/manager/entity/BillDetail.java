package com.cool.demo.manager.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cool.demo.manager.service.BillService;
import com.core.common.Cools;
import com.core.common.SpringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("man_bill_detail")
public class BillDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属订单
     */
    @TableField("bill_id")
    private Long billId;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 箱号
     */
    @TableField("box_number")
    private Integer boxNumber;

    /**
     * 装箱员
     */
    private String boxer;

    /**
     * 出库员
     */
    private String outStocker;
    /**
     * 添加时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
         * 状态 1: 已录入  2: 已装箱 3:已出库
     */
    private Short status;

    /**
     * 装箱时间
     */
    @TableField("boxing_time")
    private Date boxingTime;

    /**
     * 出库时间
     */
    @TableField("out_stock_time")
    private Date outStockTime;

    /**
     * 拼单状态
     */
    @TableField("spell_status")
    private Short spellStatus;

    /**
     * 状态 1: 正常  0: 禁用
     */
    @TableField("use_status")
    private Short useStatus;

    @TableField("unpacking")
    private Short unpacking;

    public String getUnpacking$(){
        if (null == this.unpacking)
        { return null;
        }
        switch (this.unpacking){
            case 0:
                return "可拆箱";
            case 1:
                return "已拆箱";
            case 2:  //未拆箱
                return "";

            default:
                return String.valueOf(this.unpacking);
        }
    }


    public Short getUnpacking() {
        return unpacking;
    }

    public void setUnpacking(Short unpacking) {
        this.unpacking = unpacking;
    }

    public Short getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Short useStatus) {
        this.useStatus = useStatus;
    }

    public Short getSpellStatus() {
        return spellStatus;
    }

    public void setSpellStatus(Short spellStatus) {
        this.spellStatus = spellStatus;
    }

    public String getUseStatus$(){
        if (null == this.useStatus){ return null; }
        switch (this.useStatus){
            case 1:
                return "正常";
            case 0:
                return "禁用";

            default:
                return String.valueOf(this.useStatus);
        }
    }
    public String getSpellStatus$(){
        if (null == this.spellStatus){ return null; }
        switch (this.spellStatus){
            case 1:
                return "已拼单";

            default:
                return String.valueOf(this.spellStatus);
        }
    }

    public BillDetail() {}

    public BillDetail(Long billId,Integer amount,Integer boxNumber,String boxer,Date createTime,Short status,Short useStatus ) {
        this.billId = billId;
        this.amount = amount;
        this.boxNumber = boxNumber;
        this.boxer = boxer;
        this.createTime = createTime;
        this.status = status;
        this.useStatus=useStatus;

    }
    public BillDetail(Long billId,Integer amount,Integer boxNumber,String boxer,Date createTime,Short status, Short spellStatus,Short useStatus) {
        this.billId = billId;
        this.amount = amount;
        this.boxNumber = boxNumber;
        this.boxer = boxer;
        this.createTime = createTime;
        this.status = status;
        this.spellStatus = spellStatus;
        this.useStatus=useStatus;
    }

//    BillDetail billDetail = new BillDetail(
//            null,    // 所属订单[非空]
//            null,    // 数量[非空]
//            null,    // 箱号[非空]
//            null,    // 装箱员
//            null,    // 添加时间[非空]
//            null    // 状态[非空]
//    );

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillId() {
        return billId;
    }

    public String getBillId$(){
        BillService service = SpringUtils.getBean(BillService.class);
        Bill bill = service.selectById(this.billId);
        if (!Cools.isEmpty(bill)){
            return String.valueOf(bill.getSeq());
        }
        return null;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(Integer boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getBoxer() {
        return boxer;
    }

    public void setBoxer(String boxer) {
        this.boxer = boxer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCreateTime$(){
        if (Cools.isEmpty(this.createTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(this.createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getStatus() {
        return status;
    }

    public String getStatus$(){
        if (null == this.status){ return null; }
        switch (this.status){
            case 1:
                return "已录入";
            case 2:
                return "已装箱";
            case 3:
                return "已出库";
            default:
                return String.valueOf(this.status);
        }
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getBoxingTime() {
        return boxingTime;
    }

    public void setBoxingTime(Date boxingTime) {
        this.boxingTime = boxingTime;
    }

    public String getOutStocker() {
        return outStocker;
    }

    public void setOutStocker(String outStocker) {
        this.outStocker = outStocker;
    }

    public Date getOutStockTime() {
        return outStockTime;
    }

    public void setOutStockTime(Date outStockTime) {
        this.outStockTime = outStockTime;
    }


}
