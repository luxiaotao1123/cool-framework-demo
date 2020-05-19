package com.cool.demo.asrs.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cool.demo.asrs.service.BasLocTypeService;
import com.cool.demo.asrs.service.BasWhsService;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.SpringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("asr_loc_mast")
public class LocMast implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 库位号
     */
    @ApiModelProperty(value= "库位号")
    @TableId(value = "loc_no", type = IdType.INPUT)
    @TableField("loc_no")
    private String locNo;

    /**
     * 库位类型
     */
    @ApiModelProperty(value= "库位类型")
    @TableField("whs_type")
    private Long whsType;

    @ApiModelProperty(value= "")
    @TableField("plt_type")
    private Integer pltType;

    @ApiModelProperty(value= "")
    @TableField("ctn_type")
    private Integer ctnType;

    @ApiModelProperty(value= "")
    @TableField("loc_sts")
    private String locSts;

    @ApiModelProperty(value= "")
    @TableField("sheet_no")
    private String sheetNo;

    /**
     * 堆垛机号(asr_bas_crnp)
     */
    @ApiModelProperty(value= "堆垛机号")
    @TableField("crn_no")
    private Integer crnNo;

    /**
     * 排
     */
    @ApiModelProperty(value= "排")
    private Integer row1;

    /**
     * 列
     */
    @ApiModelProperty(value= "列")
    private Integer bay1;

    /**
     * 层
     */
    @ApiModelProperty(value= "层")
    private Integer lev1;

    /**
     * 满板
     */
    @ApiModelProperty(value= "满板")
    @TableField("full_plt")
    private String fullPlt;

    /**
     * 库位状态
     */
    @ApiModelProperty(value= "库位状态")
    @TableField("loc_type")
    private String locType;

    @ApiModelProperty(value= "")
    @TableField("out_enable")
    private String outEnable;

    @ApiModelProperty(value= "")
    @TableField("io_time")
    private Date ioTime;

    @ApiModelProperty(value= "")
    @TableField("first_time")
    private Date firstTime;

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
    @TableField("error_time")
    private Date errorTime;

    @ApiModelProperty(value= "")
    @TableField("error_memo")
    private String errorMemo;

    @ApiModelProperty(value= "")
    @TableField("ctn_kind")
    private Integer ctnKind;

    @ApiModelProperty(value= "")
    @TableField("sc_weight")
    private Double scWeight;

    @ApiModelProperty(value= "")
    @TableField("inv_wh")
    private String invWh;

    @ApiModelProperty(value= "")
    private String mk;

    @ApiModelProperty(value= "")
    private String barcode;

    @ApiModelProperty(value= "")
    @TableField("Pdc_type")
    private String PdcType;

    @ApiModelProperty(value= "")
    @TableField("ctn_no")
    private String ctnNo;

    public LocMast() {}

    public LocMast(Long whsType,Integer pltType,Integer ctnType,String locSts,String sheetNo,Integer crnNo,Integer row1,Integer bay1,Integer lev1,String fullPlt,String locType,String outEnable,Date ioTime,Date firstTime,Long modiUser,Date modiTime,Long appeUser,Date appeTime,Date errorTime,String errorMemo,Integer ctnKind,Double scWeight,String invWh,String mk,String barcode,String PdcType,String ctnNo) {
        this.whsType = whsType;
        this.pltType = pltType;
        this.ctnType = ctnType;
        this.locSts = locSts;
        this.sheetNo = sheetNo;
        this.crnNo = crnNo;
        this.row1 = row1;
        this.bay1 = bay1;
        this.lev1 = lev1;
        this.fullPlt = fullPlt;
        this.locType = locType;
        this.outEnable = outEnable;
        this.ioTime = ioTime;
        this.firstTime = firstTime;
        this.modiUser = modiUser;
        this.modiTime = modiTime;
        this.appeUser = appeUser;
        this.appeTime = appeTime;
        this.errorTime = errorTime;
        this.errorMemo = errorMemo;
        this.ctnKind = ctnKind;
        this.scWeight = scWeight;
        this.invWh = invWh;
        this.mk = mk;
        this.barcode = barcode;
        this.PdcType = PdcType;
        this.ctnNo = ctnNo;
    }

//    LocMast locMast = new LocMast(
//            null,    // 库位类型
//            null,    // 
//            null,    // 
//            null,    // [非空]
//            null,    // 
//            null,    // 堆垛机号(asr_bas_crnp)
//            null,    // 排
//            null,    // 列
//            null,    // 层
//            null,    // 满板
//            null,    // 库位状态
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
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null    // 
//    );

    public String getLocNo() {
        return locNo;
    }

    public void setLocNo(String locNo) {
        this.locNo = locNo;
    }

    public Long getWhsType() {
        return whsType;
    }

    public String getWhsType$(){
        BasWhsService service = SpringUtils.getBean(BasWhsService.class);
        BasWhs basWhs = service.selectById(this.whsType);
        if (!Cools.isEmpty(basWhs)){
            return String.valueOf(basWhs.getWhsDesc());
        }
        return null;
    }

    public void setWhsType(Long whsType) {
        this.whsType = whsType;
    }

    public Integer getPltType() {
        return pltType;
    }

    public void setPltType(Integer pltType) {
        this.pltType = pltType;
    }

    public Integer getCtnType() {
        return ctnType;
    }

    public void setCtnType(Integer ctnType) {
        this.ctnType = ctnType;
    }

    public String getLocSts() {
        return locSts;
    }

    public void setLocSts(String locSts) {
        this.locSts = locSts;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public Integer getCrnNo() {
        return crnNo;
    }

    public void setCrnNo(Integer crnNo) {
        this.crnNo = crnNo;
    }

    public Integer getRow1() {
        return row1;
    }

    public void setRow1(Integer row1) {
        this.row1 = row1;
    }

    public Integer getBay1() {
        return bay1;
    }

    public void setBay1(Integer bay1) {
        this.bay1 = bay1;
    }

    public Integer getLev1() {
        return lev1;
    }

    public void setLev1(Integer lev1) {
        this.lev1 = lev1;
    }

    public String getFullPlt() {
        return fullPlt;
    }

    public void setFullPlt(String fullPlt) {
        this.fullPlt = fullPlt;
    }

    public String getLocType() {
        return locType;
    }

    public String getLocType$(){
        BasLocTypeService service = SpringUtils.getBean(BasLocTypeService.class);
        BasLocType basLocType = service.selectById(this.locType);
        if (!Cools.isEmpty(basLocType)){
            return String.valueOf(basLocType.getLocDesc());
        }
        return null;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getOutEnable() {
        return outEnable;
    }

    public void setOutEnable(String outEnable) {
        this.outEnable = outEnable;
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

    public Date getFirstTime() {
        return firstTime;
    }

    public String getFirstTime$(){
        if (Cools.isEmpty(this.firstTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.firstTime);
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
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

    public Date getErrorTime() {
        return errorTime;
    }

    public String getErrorTime$(){
        if (Cools.isEmpty(this.errorTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.errorTime);
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    public String getErrorMemo() {
        return errorMemo;
    }

    public void setErrorMemo(String errorMemo) {
        this.errorMemo = errorMemo;
    }

    public Integer getCtnKind() {
        return ctnKind;
    }

    public void setCtnKind(Integer ctnKind) {
        this.ctnKind = ctnKind;
    }

    public Double getScWeight() {
        return scWeight;
    }

    public void setScWeight(Double scWeight) {
        this.scWeight = scWeight;
    }

    public String getInvWh() {
        return invWh;
    }

    public void setInvWh(String invWh) {
        this.invWh = invWh;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPdcType() {
        return PdcType;
    }

    public void setPdcType(String PdcType) {
        this.PdcType = PdcType;
    }

    public String getCtnNo() {
        return ctnNo;
    }

    public void setCtnNo(String ctnNo) {
        this.ctnNo = ctnNo;
    }


}
