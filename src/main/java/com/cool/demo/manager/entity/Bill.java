package com.cool.demo.manager.entity;

import com.core.common.Cools;import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

@TableName("man_bill")
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 批次号
     */
    private String seq;

    /**
     * 顺序号
     */
    private String number;

    /**
     * 客户
     */
    private String customer;

    /**
     * 型号打字
     */
    @TableField("model_type")
    private String modelType;

    /**
     * 总数量
     */
    private Integer total;

    /**
     * 每箱数量
     */
    private Integer unit;

    /**
     * 颜色
     */
    private String color;

    /**
     * 装箱检验号
     */
    @TableField("box_check")
    private String boxCheck;

    /**
     * 箱号前缀
     */
    @TableField("box_prefix")
    private String boxPrefix;

    /**
     * 生产日期
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 状态 1: 已录入  2: 已装箱  3: 已出库  
     */
    private Short status;

    public Bill() {}

    public Bill(String seq,String number,String customer,String modelType,Integer total,Integer unit,String color,String boxCheck,String boxPrefix,Date createTime,Short status) {
        this.seq = seq;
        this.number = number;
        this.customer = customer;
        this.modelType = modelType;
        this.total = total;
        this.unit = unit;
        this.color = color;
        this.boxCheck = boxCheck;
        this.boxPrefix = boxPrefix;
        this.createTime = createTime;
        this.status = status;
    }

//    Bill bill = new Bill(
//            null,    // 批次号[非空]
//            null,    // 顺序号[非空]
//            null,    // 客户
//            null,    // 型号打字
//            null,    // 总数量[非空]
//            null,    // 每箱数量[非空]
//            null,    // 颜色[非空]
//            null,    // 装箱检验号[非空]
//            null,    // 箱号前缀
//            null,    // 生产日期
//            null    // 状态[非空]
//    );

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBoxCheck() {
        return boxCheck;
    }

    public void setBoxCheck(String boxCheck) {
        this.boxCheck = boxCheck;
    }

    public String getBoxPrefix() {
        return boxPrefix;
    }

    public void setBoxPrefix(String boxPrefix) {
        this.boxPrefix = boxPrefix;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCreateTime$(){
        if (Cools.isEmpty(this.createTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.createTime);
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


}
