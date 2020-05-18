package com.cool.demo.asrs.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.SpringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("asr_bas_devp")
public class BasDevp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value= "编号")
    @TableId(value = "dev_no", type = IdType.INPUT)
    @TableField("dev_no")
    private Integer devNo;

    /**
     * 设备描述
     */
    @ApiModelProperty(value= "设备描述")
    @TableField("dec_desc")
    private String decDesc;

    /**
     * 备注
     */
    @ApiModelProperty(value= "备注")
    @TableField("dev_mk")
    private String devMk;

    /**
     * 可入
     */
    @ApiModelProperty(value= "可入")
    @TableField("in_enable")
    private String inEnable;

    /**
     * 可出
     */
    @ApiModelProperty(value= "可出")
    @TableField("out_enable")
    private String outEnable;

    /**
     * 自动
     */
    @ApiModelProperty(value= "自动")
    private String autoing;

    /**
     * 有物
     */
    @ApiModelProperty(value= "有物")
    private String loading;

    /**
     * 能入
     */
    @ApiModelProperty(value= "能入")
    private String canining;

    /**
     * 能出
     */
    @ApiModelProperty(value= "能出")
    private String canouting;

    @ApiModelProperty(value= "")
    private String fronting;

    @ApiModelProperty(value= "")
    private String rearing;

    @ApiModelProperty(value= "")
    private String uping;

    @ApiModelProperty(value= "")
    private String downing;

    /**
     * 需求1
     */
    @ApiModelProperty(value= "需求1")
    private String inreq1;

    /**
     * 需求2
     */
    @ApiModelProperty(value= "需求2")
    private String inreq2;

    /**
     * 工作号
     */
    @ApiModelProperty(value= "工作号")
    @TableField("wrk_no")
    private Integer wrkNo;

    @ApiModelProperty(value= "")
    @TableField("wrk_no1")
    private Integer wrkNo1;

    /**
     * 容器类型
     */
    @ApiModelProperty(value= "容器类型")
    @TableField("ctn_type")
    private Integer ctnType;

    /**
     * 条形码
     */
    @ApiModelProperty(value= "条形码")
    private String barcode;

    @ApiModelProperty(value= "")
    @TableField("in_qty")
    private Integer inQty;

    @ApiModelProperty(value= "")
    private Integer row1;

    @ApiModelProperty(value= "")
    @TableField("io_time")
    private Date ioTime;

    @ApiModelProperty(value= "")
    private String area;

    @ApiModelProperty(value= "")
    @TableField("in_ok")
    private String inOk;

    @ApiModelProperty(value= "")
    @TableField("out_ok")
    private String outOk;

    /**
     * 修改人员
     */
    @ApiModelProperty(value= "修改人员")
    @TableField("modi_user")
    private Long modiUser;

    /**
     * 修改时间
     */
    @ApiModelProperty(value= "修改时间")
    @TableField("modi_time")
    private Date modiTime;

    /**
     * 创建者
     */
    @ApiModelProperty(value= "创建者")
    @TableField("appe_user")
    private Long appeUser;

    /**
     * 添加时间
     */
    @ApiModelProperty(value= "添加时间")
    @TableField("appe_time")
    private Date appeTime;

    @ApiModelProperty(value= "")
    @TableField("std_qty")
    private Double stdQty;

    @ApiModelProperty(value= "")
    @TableField("min_wt")
    private Double minWt;

    @ApiModelProperty(value= "")
    @TableField("max_wt")
    private Double maxWt;

    /**
     * 重量
     */
    @ApiModelProperty(value= "重量")
    @TableField("gross_wt")
    private Double grossWt;

    @ApiModelProperty(value= "")
    @TableField("cart_pos")
    private Integer cartPos;

    public BasDevp() {}

    public BasDevp(String decDesc,String devMk,String inEnable,String outEnable,String autoing,String loading,String canining,String canouting,String fronting,String rearing,String uping,String downing,String inreq1,String inreq2,Integer wrkNo,Integer wrkNo1,Integer ctnType,String barcode,Integer inQty,Integer row1,Date ioTime,String area,String inOk,String outOk,Long modiUser,Date modiTime,Long appeUser,Date appeTime,Double stdQty,Double minWt,Double maxWt,Double grossWt,Integer cartPos) {
        this.decDesc = decDesc;
        this.devMk = devMk;
        this.inEnable = inEnable;
        this.outEnable = outEnable;
        this.autoing = autoing;
        this.loading = loading;
        this.canining = canining;
        this.canouting = canouting;
        this.fronting = fronting;
        this.rearing = rearing;
        this.uping = uping;
        this.downing = downing;
        this.inreq1 = inreq1;
        this.inreq2 = inreq2;
        this.wrkNo = wrkNo;
        this.wrkNo1 = wrkNo1;
        this.ctnType = ctnType;
        this.barcode = barcode;
        this.inQty = inQty;
        this.row1 = row1;
        this.ioTime = ioTime;
        this.area = area;
        this.inOk = inOk;
        this.outOk = outOk;
        this.modiUser = modiUser;
        this.modiTime = modiTime;
        this.appeUser = appeUser;
        this.appeTime = appeTime;
        this.stdQty = stdQty;
        this.minWt = minWt;
        this.maxWt = maxWt;
        this.grossWt = grossWt;
        this.cartPos = cartPos;
    }

//    BasDevp basDevp = new BasDevp(
//            null,    // 设备描述
//            null,    // 备注
//            null,    // 可入
//            null,    // 可出
//            null,    // 自动
//            null,    // 有物
//            null,    // 能入
//            null,    // 能出
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 需求1
//            null,    // 需求2
//            null,    // 工作号
//            null,    // 
//            null,    // 容器类型
//            null,    // 条形码
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 修改人员
//            null,    // 修改时间
//            null,    // 创建者
//            null,    // 添加时间
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 重量
//            null    // 
//    );

    public Integer getDevNo() {
        return devNo;
    }

    public void setDevNo(Integer devNo) {
        this.devNo = devNo;
    }

    public String getDecDesc() {
        return decDesc;
    }

    public void setDecDesc(String decDesc) {
        this.decDesc = decDesc;
    }

    public String getDevMk() {
        return devMk;
    }

    public void setDevMk(String devMk) {
        this.devMk = devMk;
    }

    public String getInEnable() {
        return inEnable;
    }

    public void setInEnable(String inEnable) {
        this.inEnable = inEnable;
    }

    public String getOutEnable() {
        return outEnable;
    }

    public void setOutEnable(String outEnable) {
        this.outEnable = outEnable;
    }

    public String getAutoing() {
        return autoing;
    }

    public void setAutoing(String autoing) {
        this.autoing = autoing;
    }

    public String getLoading() {
        return loading;
    }

    public void setLoading(String loading) {
        this.loading = loading;
    }

    public String getCanining() {
        return canining;
    }

    public void setCanining(String canining) {
        this.canining = canining;
    }

    public String getCanouting() {
        return canouting;
    }

    public void setCanouting(String canouting) {
        this.canouting = canouting;
    }

    public String getFronting() {
        return fronting;
    }

    public void setFronting(String fronting) {
        this.fronting = fronting;
    }

    public String getRearing() {
        return rearing;
    }

    public void setRearing(String rearing) {
        this.rearing = rearing;
    }

    public String getUping() {
        return uping;
    }

    public void setUping(String uping) {
        this.uping = uping;
    }

    public String getDowning() {
        return downing;
    }

    public void setDowning(String downing) {
        this.downing = downing;
    }

    public String getInreq1() {
        return inreq1;
    }

    public void setInreq1(String inreq1) {
        this.inreq1 = inreq1;
    }

    public String getInreq2() {
        return inreq2;
    }

    public void setInreq2(String inreq2) {
        this.inreq2 = inreq2;
    }

    public Integer getWrkNo() {
        return wrkNo;
    }

    public void setWrkNo(Integer wrkNo) {
        this.wrkNo = wrkNo;
    }

    public Integer getWrkNo1() {
        return wrkNo1;
    }

    public void setWrkNo1(Integer wrkNo1) {
        this.wrkNo1 = wrkNo1;
    }

    public Integer getCtnType() {
        return ctnType;
    }

    public void setCtnType(Integer ctnType) {
        this.ctnType = ctnType;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getInQty() {
        return inQty;
    }

    public void setInQty(Integer inQty) {
        this.inQty = inQty;
    }

    public Integer getRow1() {
        return row1;
    }

    public void setRow1(Integer row1) {
        this.row1 = row1;
    }

    public Date getIoTime() {
        return ioTime;
    }

    public String getIoTime$(){
        if (Cools.isEmpty(this.ioTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.ioTime);
    }

    public void setIoTime(Date ioTime) {
        this.ioTime = ioTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInOk() {
        return inOk;
    }

    public void setInOk(String inOk) {
        this.inOk = inOk;
    }

    public String getOutOk() {
        return outOk;
    }

    public void setOutOk(String outOk) {
        this.outOk = outOk;
    }

    public Long getModiUser() {
        return modiUser;
    }

    public String getModiUser$(){
        UserService service = SpringUtils.getBean(UserService.class);
        User user = service.selectById(this.modiUser);
        if (!Cools.isEmpty(user)){
            return String.valueOf(user.getUsername());
        }
        return null;
    }

    public void setModiUser(Long modiUser) {
        this.modiUser = modiUser;
    }

    public Date getModiTime() {
        return modiTime;
    }

    public String getModiTime$(){
        if (Cools.isEmpty(this.modiTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.modiTime);
    }

    public void setModiTime(Date modiTime) {
        this.modiTime = modiTime;
    }

    public Long getAppeUser() {
        return appeUser;
    }

    public String getAppeUser$(){
        UserService service = SpringUtils.getBean(UserService.class);
        User user = service.selectById(this.appeUser);
        if (!Cools.isEmpty(user)){
            return String.valueOf(user.getUsername());
        }
        return null;
    }

    public void setAppeUser(Long appeUser) {
        this.appeUser = appeUser;
    }

    public Date getAppeTime() {
        return appeTime;
    }

    public String getAppeTime$(){
        if (Cools.isEmpty(this.appeTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.appeTime);
    }

    public void setAppeTime(Date appeTime) {
        this.appeTime = appeTime;
    }

    public Double getStdQty() {
        return stdQty;
    }

    public void setStdQty(Double stdQty) {
        this.stdQty = stdQty;
    }

    public Double getMinWt() {
        return minWt;
    }

    public void setMinWt(Double minWt) {
        this.minWt = minWt;
    }

    public Double getMaxWt() {
        return maxWt;
    }

    public void setMaxWt(Double maxWt) {
        this.maxWt = maxWt;
    }

    public Double getGrossWt() {
        return grossWt;
    }

    public void setGrossWt(Double grossWt) {
        this.grossWt = grossWt;
    }

    public Integer getCartPos() {
        return cartPos;
    }

    public void setCartPos(Integer cartPos) {
        this.cartPos = cartPos;
    }


}
