package com.cool.demo.asrs.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.cool.demo.asrs.service.BasCrnErrorService;
import com.cool.demo.asrs.service.BasCrnpService;
import com.cool.demo.asrs.service.LocMastService;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.SpringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("asr_stk_plcm")
public class StkPlcm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    @ApiModelProperty(value= "日期")
    @TableField("io_time")
    private Date ioTime;

    /**
     * 工作号
     */
    @ApiModelProperty(value= "工作号")
    @TableField("wrk_no")
    private Integer wrkNo;

    @ApiModelProperty(value= "")
    @TableField("wrk_type")
    private String wrkType;

    @ApiModelProperty(value= "")
    @TableField("s_station")
    private String sStation;

    /**
     * 库位号
     */
    @ApiModelProperty(value= "库位号")
    @TableField("loc_no")
    private String locNo;

    /**
     * 异常码
     */
    @ApiModelProperty(value= "异常码")
    @TableField("crn_error")
    private Long crnError;

    @ApiModelProperty(value= "")
    @TableField("o_station")
    private String oStation;

    /**
     * 修改人员
     */
    @ApiModelProperty(value= "修改人员")
    @TableField("modi_user")
    private Long modiUser;

    /**
     * 异常时间
     */
    @ApiModelProperty(value= "异常时间")
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

    /**
     * 堆垛机号
     */
    @ApiModelProperty(value= "堆垛机号")
    @TableField("dev_no")
    private Integer devNo;

    @ApiModelProperty(value= "")
    @TableField("input_type")
    private Integer inputType;

    /**
     * 工作档时间
     */
    @ApiModelProperty(value= "工作档时间")
    @TableField("wrk_time")
    private Date wrkTime;

    @ApiModelProperty(value= "")
    private Integer tag;

    public StkPlcm() {}

    public StkPlcm(Date ioTime,Integer wrkNo,String wrkType,String sStation,String locNo,Long crnError,String oStation,Long modiUser,Date modiTime,Long appeUser,Date appeTime,Integer devNo,Integer inputType,Date wrkTime,Integer tag) {
        this.ioTime = ioTime;
        this.wrkNo = wrkNo;
        this.wrkType = wrkType;
        this.sStation = sStation;
        this.locNo = locNo;
        this.crnError = crnError;
        this.oStation = oStation;
        this.modiUser = modiUser;
        this.modiTime = modiTime;
        this.appeUser = appeUser;
        this.appeTime = appeTime;
        this.devNo = devNo;
        this.inputType = inputType;
        this.wrkTime = wrkTime;
        this.tag = tag;
    }

//    StkPlcm stkPlcm = new StkPlcm(
//            null,    // 日期
//            null,    // 工作号
//            null,    // 
//            null,    // 
//            null,    // 库位号
//            null,    // 异常码
//            null,    // 
//            null,    // 修改人员
//            null,    // 异常时间
//            null,    // 创建者
//            null,    // 添加时间
//            null,    // 堆垛机号
//            null,    // 
//            null,    // 工作档时间
//            null    // 
//    );

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

    public Integer getWrkNo() {
        return wrkNo;
    }

    public void setWrkNo(Integer wrkNo) {
        this.wrkNo = wrkNo;
    }

    public String getWrkType() {
        return wrkType;
    }

    public void setWrkType(String wrkType) {
        this.wrkType = wrkType;
    }

    public String getSStation() {
        return sStation;
    }

    public void setSStation(String sStation) {
        this.sStation = sStation;
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

    public Long getCrnError() {
        return crnError;
    }

    public String getCrnError$(){
        BasCrnErrorService service = SpringUtils.getBean(BasCrnErrorService.class);
        BasCrnError basCrnError = service.selectById(this.crnError);
        if (!Cools.isEmpty(basCrnError)){
            return String.valueOf(basCrnError.getErrName());
        }
        return null;
    }

    public void setCrnError(Long crnError) {
        this.crnError = crnError;
    }

    public String getOStation() {
        return oStation;
    }

    public void setOStation(String oStation) {
        this.oStation = oStation;
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

    public Integer getDevNo() {
        return devNo;
    }

    public String getDevNo$(){
        BasCrnpService service = SpringUtils.getBean(BasCrnpService.class);
        BasCrnp basCrnp = service.selectById(this.devNo);
        if (!Cools.isEmpty(basCrnp)){
            return String.valueOf(basCrnp.getCrnNo());
        }
        return null;
    }

    public void setDevNo(Integer devNo) {
        this.devNo = devNo;
    }

    public Integer getInputType() {
        return inputType;
    }

    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    public Date getWrkTime() {
        return wrkTime;
    }

    public String getWrkTime$(){
        if (Cools.isEmpty(this.wrkTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.wrkTime);
    }

    public void setWrkTime(Date wrkTime) {
        this.wrkTime = wrkTime;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }


}
