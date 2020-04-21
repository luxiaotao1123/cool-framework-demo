package com.cool.demo.manager.entity;

import com.core.common.Cools;import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

@TableName("man_spell")
public class Spell implements Serializable {

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
     * 所属详情
     */
    @TableField("bill_detail_id")
    private Long billDetailId;

    /**
     * 客户
     */
    private String customer;

    /**
     * 颜色
     */
    private String color;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 型号打字
     */
    private String modelType;

    /**
     * 批次号
     */
    private String seq;

    /**
     * 二维码地址
     */
    private String qrCodeUrl;

    /**
     * 装箱检验号
     */
    private String boxCheck;

    /**
     * 箱号
     */
    private Integer boxNumber;

    public Spell() {}

    public Spell(Long billId,Long billDetailId,String customer,String color,String createTime,Integer amount,String modelType,String seq,String qrCodeUrl,String boxCheck,Integer boxNumber) {
        this.billId = billId;
        this.billDetailId = billDetailId;
        this.customer = customer;
        this.color = color;
        this.createTime = createTime;
        this.amount = amount;
        this.modelType = modelType;
        this.seq = seq;
        this.qrCodeUrl = qrCodeUrl;
        this.boxCheck = boxCheck;
        this.boxNumber = boxNumber;
    }



//    Spell spell = new Spell(
//            null,    // 所属订单[非空]
//            null,    // 所属详情[非空]
//            null,    // 客户
//            null,    // 颜色
//            null,    // 创建时间
//            null,    // 数量
//            null,    // 型号打字
//            null,    // 批次号
//            null,    // 二维码地址
//            null,    // 装箱检验号
//            null    // 箱号
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

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(Long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

//    public String getCreateTime() {
//        return createTime;
//    }
//
//    public String getCreateTime$(){
//        if (Cools.isEmpty(this.createTime)){
//            return "";
//        }
//        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.createTime);
//    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getBoxCheck() {
        return boxCheck;
    }

    public void setBoxCheck(String boxCheck) {
        this.boxCheck = boxCheck;
    }

    public Integer getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(Integer boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getCreateTime() {
        return createTime;
    }
}
