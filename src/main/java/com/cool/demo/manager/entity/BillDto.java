package com.cool.demo.manager.entity;

import java.net.Inet4Address;

/**
 * Created by vincent on 2020-03-26
 */
public class BillDto {

    private Long billId;

    private Long billDetailId;

    private String customer;

    private String color;

    private String createTime$;

    private int amount;

    private String modelType;

    private String seq;

    private String qrCodeUrl;

    private String boxCheck;

    private Integer boxNumber;

    private  String boxer;

    private Integer count;

   // private String boxingTime;

    private  String  outStockTime;


    private  String remark;

    public String getRemark() {

        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BillDto() {
    }

    public BillDto(Long billId, Long billDetailId, String customer, String color, String createTime$, int amount, String modelType, String seq, String qrCodeUrl,  String boxCheck,Integer boxNumber,String remark) {
        this.billId = billId;
        this.billDetailId = billDetailId;
        this.customer = customer;
        this.color = color;
        this.createTime$ = createTime$;
        this.amount = amount;
        this.modelType = modelType;
        this.seq = seq;
        this.qrCodeUrl = qrCodeUrl;
        this.boxCheck = boxCheck;
        this.boxNumber = boxNumber;
        this.remark=remark;
    }
    public BillDto(Long billId, Long billDetailId, String customer, String color, String createTime$, int amount, String modelType, String seq,
                   String boxCheck,String qrCodeUrl,  Integer boxNumber,String boxer,int count, String outStockTime) {
        this.billId = billId;
        this.billDetailId = billDetailId;
        this.customer = customer;
        this.color = color;
        this.createTime$ = createTime$;
        this.amount = amount;
        this.modelType = modelType;
        this.seq = seq;
        this.boxCheck = boxCheck;
        this.qrCodeUrl = qrCodeUrl;

        this.boxNumber = boxNumber;
        this.boxer=boxer;
        this.count=count;
         //this.boxingTime=boxingTime;
        this.outStockTime=outStockTime;
    }

    public String getOutStockTime() {
        return outStockTime;
    }

    public void setOutStockTime(String outStockTime) {
        this.outStockTime = outStockTime;
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

    public String getCreateTime$() {
        return createTime$;
    }

    public void setCreateTime$(String createTime$) {
        this.createTime$ = createTime$;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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

    public String getBoxer() {
        return boxer;
    }

    public void setBoxer(String boxer) {
        this.boxer = boxer;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

//    public String getBoxingTime() {
//        return boxingTime;
//    }
//
//    public void setBoxingTime(String boxingTime) {
//        this.boxingTime = boxingTime;
//    }
}
