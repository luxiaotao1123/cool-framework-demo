package com.cool.demo.manager.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.core.common.Cools;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("man_enterprise")
public class Enterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 企业名称
     */
    private String name;

    /**
     * 注册号
     */
    private String no;

    /**
     * 统一社会信用代码
     */
    private String code;

    /**
     * 法人代表
     */
    private String frname;

    /**
     * 注册地址
     */
    @TableField("reg_address")
    private String regAddress;

    /**
     * 注册时间
     */
    @TableField("reg_time")
    private Date regTime;

    /**
     * 注册资本
     */
    @TableField("reg_cap")
    private String regCap;

    /**
     * 纳税规模（万元）
     */
    @TableField("taxes_cap")
    private Double taxesCap;

    /**
     * 主管纳税机关
     */
    @TableField("taxes_organ")
    private String taxesOrgan;

    /**
     * 纳税状态 0: 非正常  1: 正常  2: 报验  3: 核销报验  4: 注销  
     */
    @TableField("taxes_status")
    private Short taxesStatus;

    /**
     * 添加时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 状态 1: 有效  0: 无效  
     */
    private Short status;

    public Enterprise() {}

    public Enterprise(String name, String no, String code, String frname, String regAddress, Date regTime, String regCap, Double taxesCap, String taxesOrgan, Short taxesStatus, Date createTime, Short status) {
        this.name = name;   // 企业名称[非空]
        this.no = no;   // 注册号[非空]
        this.code = code;   // 统一社会信用代码
        this.frname = frname;   // 法人代表[非空]
        this.regAddress = regAddress;   // 注册地址[非空]
        this.regTime = regTime;   // 注册时间[非空]
        this.regCap = regCap;   // 注册资本[非空]
        this.taxesCap = taxesCap;   // 纳税规模（万元）
        this.taxesOrgan = taxesOrgan;   // 主管纳税机关
        this.taxesStatus = taxesStatus;   // 纳税状态
        this.createTime = createTime;   // 添加时间[非空]
        this.status = status;   // 状态[非空]
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFrname() {
        return frname;
    }

    public void setFrname(String frname) {
        this.frname = frname;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public Date getRegTime() {
        return regTime;
    }

    public String getRegTime$(){
        if (Cools.isEmpty(this.regTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.regTime);
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getRegCap() {
        return regCap;
    }

    public void setRegCap(String regCap) {
        this.regCap = regCap;
    }

    public Double getTaxesCap() {
        return taxesCap;
    }

    public void setTaxesCap(Double taxesCap) {
        this.taxesCap = taxesCap;
    }

    public String getTaxesOrgan() {
        return taxesOrgan;
    }

    public void setTaxesOrgan(String taxesOrgan) {
        this.taxesOrgan = taxesOrgan;
    }

    public Short getTaxesStatus() {
        return taxesStatus;
    }

    public String getTaxesStatus$(){
        if (null == this.taxesStatus){ return null; }
        switch (this.taxesStatus){
            case 0:
                return "非正常";
            case 1:
                return "正常";
            case 2:
                return "报验";
            case 3:
                return "核销报验";
            case 4:
                return "注销";
            default:
                return String.valueOf(this.taxesStatus);
        }
    }

    public void setTaxesStatus(Short taxesStatus) {
        this.taxesStatus = taxesStatus;
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
                return "有效";
            case 0:
                return "无效";
            default:
                return String.valueOf(this.status);
        }
    }

    public void setStatus(Short status) {
        this.status = status;
    }


}
