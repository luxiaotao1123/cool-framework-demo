package com.cool.demo.manager.entity;

import com.core.common.Cools;import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

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
     * 数量
     */
    private Integer amount;

    /**
     * 颜色
     */
    private String color;

    /**
     * 装箱员
     */
    private String boxer;

    /**
     * 状态 1: 已录入  2: 已装箱  3: 已出库  
     */
    private Short status;

    public Bill() {}

    public Bill(String seq,String number,Integer amount,String color,String boxer,Short status) {
        this.seq = seq;
        this.number = number;
        this.amount = amount;
        this.color = color;
        this.boxer = boxer;
        this.status = status;
    }

//    Bill bill = new Bill(
//            null,    // 批次号[非空]
//            null,    // 顺序号[非空]
//            null,    // 数量[非空]
//            null,    // 颜色[非空]
//            null,    // 装箱员[非空]
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBoxer() {
        return boxer;
    }

    public void setBoxer(String boxer) {
        this.boxer = boxer;
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
