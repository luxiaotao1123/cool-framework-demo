package com.cool.demo.manager.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.SpringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("man_mate_log")
public class MateLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 转储编号
     */
    @ApiModelProperty(value= "转储编号")
    private String uuid;

    /**
     * 转储日期
     */
    @ApiModelProperty(value= "转储日期")
    @TableField("log_time")
    private Date logTime;

    /**
     * 单据编号
     */
    @ApiModelProperty(value= "单据编号")
    @TableField("bill_id")
    private String billId;

    /**
     * 单据日期
     */
    @ApiModelProperty(value= "单据日期")
    @TableField("bill_time")
    private Date billTime;

    /**
     * 供应商
     */
    @ApiModelProperty(value= "供应商")
    private String supplier;

    /**
     * 商品编码
     */
    @ApiModelProperty(value= "商品编码")
    private String code;

    /**
     * 商品名称
     */
    @ApiModelProperty(value= "商品名称")
    private String name;

    /**
     * 数量
     */
    @ApiModelProperty(value= "数量")
    private Double amount;

    /**
     * 交货时间
     */
    @ApiModelProperty(value= "交货时间")
    @TableField("lead_time")
    private Date leadTime;

    /**
     * 已入库数量
     */
    @ApiModelProperty(value= "已入库数量")
    @TableField("pakin_amount")
    private Double pakinAmount;

    /**
     * 未入库数量
     */
    @ApiModelProperty(value= "未入库数量")
    @TableField("notpak_amount")
    private Double notpakAmount;

    /**
     * 商品行备注
     */
    @ApiModelProperty(value= "商品行备注")
    private String content;

    /**
     * 审核状态
     */
    @ApiModelProperty(value= "审核状态")
    private String state;

    /**
     * 关闭状态
     */
    @ApiModelProperty(value= "关闭状态")
    private String status;

    /**
     * 入库状态
     */
    @ApiModelProperty(value= "入库状态")
    @TableField("pak_status")
    private String pakStatus;

    /**
     * 单据备注
     */
    @ApiModelProperty(value= "单据备注")
    @TableField("bill_memo")
    private String billMemo;

    /**
     * 单位
     */
    @ApiModelProperty(value= "单位")
    private String unit;

    /**
     * 创建者
     */
    @ApiModelProperty(value= "创建者")
    @TableField("create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value= "创建时间")
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改人员
     */
    @ApiModelProperty(value= "修改人员")
    @TableField("update_by")
    private Long updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value= "修改时间")
    @TableField("update_time")
    private Date updateTime;

    /**
     * 备注
     */
    @ApiModelProperty(value= "备注")
    private String memo;

    public MateLog() {}

    public MateLog(String uuid,Date logTime,String billId,Date billTime,String supplier,String code,String name,Double amount,Date leadTime,Double pakinAmount,Double notpakAmount,String content,String state,String status,String pakStatus,String billMemo,String unit,Long createBy,Date createTime,Long updateBy,Date updateTime,String memo) {
        this.uuid = uuid;
        this.logTime = logTime;
        this.billId = billId;
        this.billTime = billTime;
        this.supplier = supplier;
        this.code = code;
        this.name = name;
        this.amount = amount;
        this.leadTime = leadTime;
        this.pakinAmount = pakinAmount;
        this.notpakAmount = notpakAmount;
        this.content = content;
        this.state = state;
        this.status = status;
        this.pakStatus = pakStatus;
        this.billMemo = billMemo;
        this.unit = unit;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.memo = memo;
    }

//    MateLog mateLog = new MateLog(
//            null,    // 转储编号
//            null,    // 转储日期
//            null,    // 单据编号
//            null,    // 单据日期
//            null,    // 供应商
//            null,    // 商品编码[非空]
//            null,    // 商品名称[非空]
//            null,    // 数量
//            null,    // 交货时间
//            null,    // 已入库数量
//            null,    // 未入库数量
//            null,    // 商品行备注
//            null,    // 审核状态
//            null,    // 关闭状态
//            null,    // 入库状态
//            null,    // 单据备注
//            null,    // 单位
//            null,    // 创建者
//            null,    // 创建时间
//            null,    // 修改人员
//            null,    // 修改时间
//            null    // 备注
//    );

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getLogTime() {
        return logTime;
    }

    public String getLogTime$(){
        if (Cools.isEmpty(this.logTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.logTime);
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Date getBillTime() {
        return billTime;
    }

    public String getBillTime$(){
        if (Cools.isEmpty(this.billTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.billTime);
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getLeadTime() {
        return leadTime;
    }

    public String getLeadTime$(){
        if (Cools.isEmpty(this.leadTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.leadTime);
    }

    public void setLeadTime(Date leadTime) {
        this.leadTime = leadTime;
    }

    public Double getPakinAmount() {
        return pakinAmount;
    }

    public void setPakinAmount(Double pakinAmount) {
        this.pakinAmount = pakinAmount;
    }

    public Double getNotpakAmount() {
        return notpakAmount;
    }

    public void setNotpakAmount(Double notpakAmount) {
        this.notpakAmount = notpakAmount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPakStatus() {
        return pakStatus;
    }

    public void setPakStatus(String pakStatus) {
        this.pakStatus = pakStatus;
    }

    public String getBillMemo() {
        return billMemo;
    }

    public void setBillMemo(String billMemo) {
        this.billMemo = billMemo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public String getCreateBy$(){
        UserService service = SpringUtils.getBean(UserService.class);
        User user = service.selectById(this.createBy);
        if (!Cools.isEmpty(user)){
            return String.valueOf(user.getNickname());
        }
        return null;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
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

    public Long getUpdateBy() {
        return updateBy;
    }

    public String getUpdateBy$(){
        UserService service = SpringUtils.getBean(UserService.class);
        User user = service.selectById(this.updateBy);
        if (!Cools.isEmpty(user)){
            return String.valueOf(user.getNickname());
        }
        return null;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getUpdateTime$(){
        if (Cools.isEmpty(this.updateTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.updateTime);
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


}
