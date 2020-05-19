package com.cool.demo.asrs.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cool.demo.asrs.service.*;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.SpringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("asr_wrk_mast")
public class WrkMast implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工作号
     */
    @ApiModelProperty(value= "工作号")
    @TableId(value = "wrk_no", type = IdType.INPUT)
    @TableField("wrk_no")
    private Integer wrkNo;

    @ApiModelProperty(value= "")
    @TableField("inv_wh")
    private String invWh;

    @ApiModelProperty(value= "")
    private Date ymd;

    @ApiModelProperty(value= "")
    private String mk;

    @ApiModelProperty(value= "")
    @TableField("whs_type")
    private Integer whsType;

    /**
     * 工作状态
     */
    @ApiModelProperty(value= "工作状态")
    @TableField("wrk_sts")
    private Long wrkSts;

    /**
     * 入出库类型
     */
    @ApiModelProperty(value= "入出库类型")
    @TableField("io_type")
    private Integer ioType;

    /**
     * 堆垛机
     */
    @ApiModelProperty(value= "堆垛机")
    @TableField("crn_no")
    private Integer crnNo;

    @ApiModelProperty(value= "")
    @TableField("sheet_no")
    private String sheetNo;

    /**
     * 优先级
     */
    @ApiModelProperty(value= "优先级")
    @TableField("io_pri")
    private Double ioPri;

    @ApiModelProperty(value= "")
    @TableField("wrk_date")
    private Date wrkDate;

    /**
     * 目标库位
     */
    @ApiModelProperty(value= "目标库位")
    @TableField("loc_no")
    private String locNo;

    /**
     * 目标站
     */
    @ApiModelProperty(value= "目标站")
    @TableField("sta_no")
    private Integer staNo;

    /**
     * 源站
     */
    @ApiModelProperty(value= "源站")
    @TableField("source_sta_no")
    private Integer sourceStaNo;

    /**
     * 源库位
     */
    @ApiModelProperty(value= "源库位")
    @TableField("source_loc_no")
    private String sourceLocNo;

    @ApiModelProperty(value= "")
    @TableField("loc_sts")
    private String locSts;

    /**
     * 拣料
     */
    @ApiModelProperty(value= "拣料")
    private String picking;

    @ApiModelProperty(value= "")
    @TableField("link_mis")
    private String linkMis;

    @ApiModelProperty(value= "")
    @TableField("online_yn")
    private String onlineYn;

    @ApiModelProperty(value= "")
    @TableField("upd_mk")
    private String updMk;

    /**
     * 退出
     */
    @ApiModelProperty(value= "退出")
    @TableField("exit_mk")
    private String exitMk;

    @ApiModelProperty(value= "")
    @TableField("plt_type")
    private Integer pltType;

    /**
     * 空板
     */
    @ApiModelProperty(value= "空板")
    @TableField("empty_mk")
    private String emptyMk;

    /**
     * 工作时间
     */
    @ApiModelProperty(value= "工作时间")
    @TableField("io_time")
    private Date ioTime;

    @ApiModelProperty(value= "")
    @TableField("ctn_type")
    private Integer ctnType;

    @ApiModelProperty(value= "")
    private String packed;

    @ApiModelProperty(value= "")
    @TableField("ove_mk")
    private String oveMk;

    @ApiModelProperty(value= "")
    @TableField("mtn_type")
    private Double mtnType;

    @ApiModelProperty(value= "")
    @TableField("user_no")
    private String userNo;

    /**
     * 堆垛机启动时间
     */
    @ApiModelProperty(value= "堆垛机启动时间")
    @TableField("crn_str_time")
    private Date crnStrTime;

    /**
     * 堆垛机停止时间
     */
    @ApiModelProperty(value= "堆垛机停止时间")
    @TableField("crn_end_time")
    private Date crnEndTime;

    @ApiModelProperty(value= "")
    @TableField("plc_str_time")
    private Date plcStrTime;

    @ApiModelProperty(value= "")
    @TableField("crn_pos_time")
    private Date crnPosTime;

    @ApiModelProperty(value= "")
    @TableField("load_time")
    private Double loadTime;

    @ApiModelProperty(value= "")
    @TableField("exp_time")
    private Double expTime;

    @ApiModelProperty(value= "")
    @TableField("ref_wrkno")
    private Double refWrkno;

    /**
     * 拣料时间
     */
    @ApiModelProperty(value= "拣料时间")
    @TableField("ref_iotime")
    private Date refIotime;

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
    @TableField("pause_mk")
    private String pauseMk;

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
    @TableField("manu_type")
    private String manuType;

    /**
     * 备注
     */
    @ApiModelProperty(value= "备注")
    private String memo;

    @ApiModelProperty(value= "")
    @TableField("sc_weight")
    private Double scWeight;

    @ApiModelProperty(value= "")
    @TableField("log_mk")
    private String logMk;

    @ApiModelProperty(value= "")
    @TableField("log_err_time")
    private Date logErrTime;

    @ApiModelProperty(value= "")
    @TableField("log_err_memo")
    private String logErrMemo;

    /**
     * 条码
     */
    @ApiModelProperty(value= "条码")
    private String barcode;

    @ApiModelProperty(value= "")
    @TableField("Pdc_type")
    private String PdcType;

    @ApiModelProperty(value= "")
    @TableField("ctn_no")
    private String ctnNo;

    /**
     * 满板
     */
    @ApiModelProperty(value= "满板")
    @TableField("full_plt")
    private String fullPlt;

    public WrkMast() {}

    public WrkMast(String invWh,Date ymd,String mk,Integer whsType,Long wrkSts,Integer ioType,Integer crnNo,String sheetNo,Double ioPri,Date wrkDate,String locNo,Integer staNo,Integer sourceStaNo,String sourceLocNo,String locSts,String picking,String linkMis,String onlineYn,String updMk,String exitMk,Integer pltType,String emptyMk,Date ioTime,Integer ctnType,String packed,String oveMk,Double mtnType,String userNo,Date crnStrTime,Date crnEndTime,Date plcStrTime,Date crnPosTime,Double loadTime,Double expTime,Double refWrkno,Date refIotime,Long modiUser,Date modiTime,Long appeUser,Date appeTime,String pauseMk,Date errorTime,String errorMemo,Integer ctnKind,String manuType,String memo,Double scWeight,String logMk,Date logErrTime,String logErrMemo,String barcode,String PdcType,String ctnNo,String fullPlt) {
        this.invWh = invWh;
        this.ymd = ymd;
        this.mk = mk;
        this.whsType = whsType;
        this.wrkSts = wrkSts;
        this.ioType = ioType;
        this.crnNo = crnNo;
        this.sheetNo = sheetNo;
        this.ioPri = ioPri;
        this.wrkDate = wrkDate;
        this.locNo = locNo;
        this.staNo = staNo;
        this.sourceStaNo = sourceStaNo;
        this.sourceLocNo = sourceLocNo;
        this.locSts = locSts;
        this.picking = picking;
        this.linkMis = linkMis;
        this.onlineYn = onlineYn;
        this.updMk = updMk;
        this.exitMk = exitMk;
        this.pltType = pltType;
        this.emptyMk = emptyMk;
        this.ioTime = ioTime;
        this.ctnType = ctnType;
        this.packed = packed;
        this.oveMk = oveMk;
        this.mtnType = mtnType;
        this.userNo = userNo;
        this.crnStrTime = crnStrTime;
        this.crnEndTime = crnEndTime;
        this.plcStrTime = plcStrTime;
        this.crnPosTime = crnPosTime;
        this.loadTime = loadTime;
        this.expTime = expTime;
        this.refWrkno = refWrkno;
        this.refIotime = refIotime;
        this.modiUser = modiUser;
        this.modiTime = modiTime;
        this.appeUser = appeUser;
        this.appeTime = appeTime;
        this.pauseMk = pauseMk;
        this.errorTime = errorTime;
        this.errorMemo = errorMemo;
        this.ctnKind = ctnKind;
        this.manuType = manuType;
        this.memo = memo;
        this.scWeight = scWeight;
        this.logMk = logMk;
        this.logErrTime = logErrTime;
        this.logErrMemo = logErrMemo;
        this.barcode = barcode;
        this.PdcType = PdcType;
        this.ctnNo = ctnNo;
        this.fullPlt = fullPlt;
    }

//    WrkMast wrkMast = new WrkMast(
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 工作状态
//            null,    // 入出库类型
//            null,    // 堆垛机
//            null,    // 
//            null,    // 优先级
//            null,    // 
//            null,    // 目标库位
//            null,    // 目标站
//            null,    // 源站
//            null,    // 源库位
//            null,    // 
//            null,    // 拣料
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 退出
//            null,    // 
//            null,    // 空板
//            null,    // 工作时间
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 堆垛机启动时间
//            null,    // 堆垛机停止时间
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 拣料时间
//            null,    // 修改人员
//            null,    // 修改时间
//            null,    // 创建者
//            null,    // 添加时间
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 备注
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 条码
//            null,    // 
//            null,    // 
//            null    // 满板
//    );

    public Integer getWrkNo() {
        return wrkNo;
    }

    public void setWrkNo(Integer wrkNo) {
        this.wrkNo = wrkNo;
    }

    public String getInvWh() {
        return invWh;
    }

    public void setInvWh(String invWh) {
        this.invWh = invWh;
    }

    public Date getYmd() {
        return ymd;
    }

    public String getYmd$(){
        if (Cools.isEmpty(this.ymd)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.ymd);
    }

    public void setYmd(Date ymd) {
        this.ymd = ymd;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }

    public Integer getWhsType() {
        return whsType;
    }

    public void setWhsType(Integer whsType) {
        this.whsType = whsType;
    }

    public Long getWrkSts() {
        return wrkSts;
    }

    public String getWrkSts$(){
        BasWrkStatusService service = SpringUtils.getBean(BasWrkStatusService.class);
        BasWrkStatus basWrkStatus = service.selectById(this.wrkSts);
        if (!Cools.isEmpty(basWrkStatus)){
            return String.valueOf(basWrkStatus.getWrkDesc());
        }
        return null;
    }

    public void setWrkSts(Long wrkSts) {
        this.wrkSts = wrkSts;
    }

    public Integer getIoType() {
        return ioType;
    }

    public String getIoType$(){
        BasWrkIotypeService service = SpringUtils.getBean(BasWrkIotypeService.class);
        BasWrkIotype basWrkIotype = service.selectById(this.ioType);
        if (!Cools.isEmpty(basWrkIotype)){
            return String.valueOf(basWrkIotype.getIoDesc());
        }
        return null;
    }

    public void setIoType(Integer ioType) {
        this.ioType = ioType;
    }

    public Integer getCrnNo() {
        return crnNo;
    }

    public String getCrnNo$(){
        BasCrnpService service = SpringUtils.getBean(BasCrnpService.class);
        BasCrnp basCrnp = service.selectById(this.crnNo);
        if (!Cools.isEmpty(basCrnp)){
            return String.valueOf(basCrnp.getCrnNo());
        }
        return null;
    }

    public void setCrnNo(Integer crnNo) {
        this.crnNo = crnNo;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public Double getIoPri() {
        return ioPri;
    }

    public void setIoPri(Double ioPri) {
        this.ioPri = ioPri;
    }

    public Date getWrkDate() {
        return wrkDate;
    }

    public String getWrkDate$(){
        if (Cools.isEmpty(this.wrkDate)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.wrkDate);
    }

    public void setWrkDate(Date wrkDate) {
        this.wrkDate = wrkDate;
    }

    public String getLocNo() {
        return locNo;
    }

    public String getLocNo$(){
        LocMastService service = SpringUtils.getBean(LocMastService.class);
        LocMast locMast = service.selectById(this.locNo);
        if (!Cools.isEmpty(locMast)){
            return String.valueOf(locMast.getLocNo());
        }
        return null;
    }

    public void setLocNo(String locNo) {
        this.locNo = locNo;
    }

    public Integer getStaNo() {
        return staNo;
    }

    public String getStaNo$(){
        BasDevpService service = SpringUtils.getBean(BasDevpService.class);
        BasDevp basDevp = service.selectById(this.staNo);
        if (!Cools.isEmpty(basDevp)){
            return String.valueOf(basDevp.getDevNo());
        }
        return null;
    }

    public void setStaNo(Integer staNo) {
        this.staNo = staNo;
    }

    public Integer getSourceStaNo() {
        return sourceStaNo;
    }

    public String getSourceStaNo$(){
        BasDevpService service = SpringUtils.getBean(BasDevpService.class);
        BasDevp basDevp = service.selectById(this.sourceStaNo);
        if (!Cools.isEmpty(basDevp)){
            return String.valueOf(basDevp.getDevNo());
        }
        return null;
    }

    public void setSourceStaNo(Integer sourceStaNo) {
        this.sourceStaNo = sourceStaNo;
    }

    public String getSourceLocNo() {
        return sourceLocNo;
    }

    public String getSourceLocNo$(){
        LocMastService service = SpringUtils.getBean(LocMastService.class);
        LocMast locMast = service.selectById(this.sourceLocNo);
        if (!Cools.isEmpty(locMast)){
            return String.valueOf(locMast.getLocNo());
        }
        return null;
    }

    public void setSourceLocNo(String sourceLocNo) {
        this.sourceLocNo = sourceLocNo;
    }

    public String getLocSts() {
        return locSts;
    }

    public void setLocSts(String locSts) {
        this.locSts = locSts;
    }

    public String getPicking() {
        return picking;
    }

    public void setPicking(String picking) {
        this.picking = picking;
    }

    public String getLinkMis() {
        return linkMis;
    }

    public void setLinkMis(String linkMis) {
        this.linkMis = linkMis;
    }

    public String getOnlineYn() {
        return onlineYn;
    }

    public void setOnlineYn(String onlineYn) {
        this.onlineYn = onlineYn;
    }

    public String getUpdMk() {
        return updMk;
    }

    public void setUpdMk(String updMk) {
        this.updMk = updMk;
    }

    public String getExitMk() {
        return exitMk;
    }

    public void setExitMk(String exitMk) {
        this.exitMk = exitMk;
    }

    public Integer getPltType() {
        return pltType;
    }

    public void setPltType(Integer pltType) {
        this.pltType = pltType;
    }

    public String getEmptyMk() {
        return emptyMk;
    }

    public void setEmptyMk(String emptyMk) {
        this.emptyMk = emptyMk;
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

    public Integer getCtnType() {
        return ctnType;
    }

    public void setCtnType(Integer ctnType) {
        this.ctnType = ctnType;
    }

    public String getPacked() {
        return packed;
    }

    public void setPacked(String packed) {
        this.packed = packed;
    }

    public String getOveMk() {
        return oveMk;
    }

    public void setOveMk(String oveMk) {
        this.oveMk = oveMk;
    }

    public Double getMtnType() {
        return mtnType;
    }

    public void setMtnType(Double mtnType) {
        this.mtnType = mtnType;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Date getCrnStrTime() {
        return crnStrTime;
    }

    public String getCrnStrTime$(){
        if (Cools.isEmpty(this.crnStrTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.crnStrTime);
    }

    public void setCrnStrTime(Date crnStrTime) {
        this.crnStrTime = crnStrTime;
    }

    public Date getCrnEndTime() {
        return crnEndTime;
    }

    public String getCrnEndTime$(){
        if (Cools.isEmpty(this.crnEndTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.crnEndTime);
    }

    public void setCrnEndTime(Date crnEndTime) {
        this.crnEndTime = crnEndTime;
    }

    public Date getPlcStrTime() {
        return plcStrTime;
    }

    public String getPlcStrTime$(){
        if (Cools.isEmpty(this.plcStrTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.plcStrTime);
    }

    public void setPlcStrTime(Date plcStrTime) {
        this.plcStrTime = plcStrTime;
    }

    public Date getCrnPosTime() {
        return crnPosTime;
    }

    public String getCrnPosTime$(){
        if (Cools.isEmpty(this.crnPosTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.crnPosTime);
    }

    public void setCrnPosTime(Date crnPosTime) {
        this.crnPosTime = crnPosTime;
    }

    public Double getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Double loadTime) {
        this.loadTime = loadTime;
    }

    public Double getExpTime() {
        return expTime;
    }

    public void setExpTime(Double expTime) {
        this.expTime = expTime;
    }

    public Double getRefWrkno() {
        return refWrkno;
    }

    public void setRefWrkno(Double refWrkno) {
        this.refWrkno = refWrkno;
    }

    public Date getRefIotime() {
        return refIotime;
    }

    public String getRefIotime$(){
        if (Cools.isEmpty(this.refIotime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.refIotime);
    }

    public void setRefIotime(Date refIotime) {
        this.refIotime = refIotime;
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

    public String getPauseMk() {
        return pauseMk;
    }

    public void setPauseMk(String pauseMk) {
        this.pauseMk = pauseMk;
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

    public String getManuType() {
        return manuType;
    }

    public void setManuType(String manuType) {
        this.manuType = manuType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getScWeight() {
        return scWeight;
    }

    public void setScWeight(Double scWeight) {
        this.scWeight = scWeight;
    }

    public String getLogMk() {
        return logMk;
    }

    public void setLogMk(String logMk) {
        this.logMk = logMk;
    }

    public Date getLogErrTime() {
        return logErrTime;
    }

    public String getLogErrTime$(){
        if (Cools.isEmpty(this.logErrTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.logErrTime);
    }

    public void setLogErrTime(Date logErrTime) {
        this.logErrTime = logErrTime;
    }

    public String getLogErrMemo() {
        return logErrMemo;
    }

    public void setLogErrMemo(String logErrMemo) {
        this.logErrMemo = logErrMemo;
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

    public String getFullPlt() {
        return fullPlt;
    }

    public void setFullPlt(String fullPlt) {
        this.fullPlt = fullPlt;
    }


}
