package com.cool.demo.manager.entity;

/**
 * Created by vincent on 2020-03-26
 */
public class outStockDto {

    /**
     * 订单ID
     */
    private Long billId;

    /**
     * 明细ID
     */
    private Long billDetailId;


    /**
     * 单据号码
     */
    private String billNumber;
    /**
     * 客户
     */
    private String customer;

    /**
     * 产品名称
     */
    private String billName;
    /**
     * 规格
     */
    private String specifications;

    /**
     * 单据时间
     */
    private String billCreateTime;

    /**
     * 打印日期
     */
    private String printTime;

    /**
     * 数量
     */
    private int amount;

    /**
     * 单价
     */
    private double price;



    /**
     * 批次号
     */
    private String seq;

//    private String modelType;
//
//    private String qrCodeUrl;
//
//    private String boxCheck;

    /**
     * 箱号
     */
    private Integer boxNumber;

    /**
     * 制单人员
     */
    private String orderMakinger;


    /**
     * 出库时间
     */
    private String outStockTime;

    /**
     * 备注
     */
    private String remark;


    /**
     * 件数
     * @return
     */
    private int piece;

    /**
     * 总价
     */
    private double totalPrice;

    /**
     * 客户订单号
     */
    private String customerOrder;

    public String getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(String customerOrder) {
        this.customerOrder = customerOrder;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
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

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getBillCreateTime() {
        return billCreateTime;
    }

    public void setBillCreateTime(String billCreateTime) {
        this.billCreateTime = billCreateTime;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public Integer getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(Integer boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getOrderMakinger() {
        return orderMakinger;
    }

    public void setOrderMakinger(String orderMakinger) {
        this.orderMakinger = orderMakinger;
    }

    public String getOutStockTime() {
        return outStockTime;
    }

    public void setOutStockTime(String outStockTime) {
        this.outStockTime = outStockTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * 无参构造
     */
    public outStockDto(){

    }
    public outStockDto(Long billId, Long billDetailId, String billNumber, String customer, String billName, String specifications, String billCreateTime, String printTime, int amount, double price, String seq, Integer boxNumber, String orderMakinger, String outStockTime, String remark) {
        this.billId = billId;
        this.billDetailId = billDetailId;
        this.billNumber = billNumber;
        this.customer = customer;
        this.billName = billName;
        this.specifications = specifications;
        this.billCreateTime = billCreateTime;
        this.printTime = printTime;
        this.amount = amount;
        this.price = price;
        this.seq = seq;
        this.boxNumber = boxNumber;
        this.orderMakinger = orderMakinger;
        this.outStockTime = outStockTime;
        this.remark = remark;
    }
}
