package com.cool.demo.manager.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cool.demo.system.service.UserService;
import com.cool.demo.system.entity.User;
import com.core.common.Cools;
import com.core.common.SpringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("man_mate")
public class Mate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value= "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 商品行备注
     */
    @ApiModelProperty(value= "商品行备注")
    private String content;

    /**
     * 交货地址
     */
    @ApiModelProperty(value= "交货地址")
    @TableField("lead_addr")
    private String leadAddr;

    /**
     * 交货时间
     */
    @ApiModelProperty(value= "交货时间")
    @TableField("lead_time")
    private Date leadTime;

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

    public Mate() {}

    public Mate(String supplier,String code,String name,Double amount,String content,String leadAddr,Date leadTime,Long createBy,Date createTime,Long updateBy,Date updateTime,String memo) {
        this.supplier = supplier;
        this.code = code;
        this.name = name;
        this.amount = amount;
        this.content = content;
        this.leadAddr = leadAddr;
        this.leadTime = leadTime;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.memo = memo;
    }

//    Mate mate = new Mate(
//            null,    // 供应商
//            null,    // 商品编码[非空]
//            null,    // 商品名称[非空]
//            null,    // 数量
//            null,    // 商品行备注
//            null,    // 交货地址
//            null,    // 交货时间
//            null,    // 创建者
//            null,    // 创建时间
//            null,    // 修改人员
//            null,    // 修改时间
//            null    // 备注
//    );

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLeadAddr() {
        return leadAddr;
    }

    public void setLeadAddr(String leadAddr) {
        this.leadAddr = leadAddr;
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
