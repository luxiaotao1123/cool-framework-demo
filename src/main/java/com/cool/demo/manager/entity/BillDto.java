package com.cool.demo.manager.entity;

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

    private String boxNumber;

    public BillDto() {
    }

    public BillDto(Long billId, Long billDetailId, String customer, String color, String createTime$, int amount, String modelType, String seq, String qrCodeUrl, String boxCheck, String boxNumber) {
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

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }
}
