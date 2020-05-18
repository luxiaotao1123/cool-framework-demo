package com.cool.demo.asrs.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cool.demo.asrs.service.BasCrnErrorService;
import com.cool.demo.asrs.service.BasCrnStatusService;
import com.cool.demo.asrs.service.BasDevpService;
import com.cool.demo.asrs.service.LocMastService;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.SpringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("asr_bas_crnp")
public class BasCrnp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value= "编号")
    @TableId(value = "crn_no", type = IdType.INPUT)
    @TableField("crn_no")
    private Integer crnNo;

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
     * 状态
     */
    @ApiModelProperty(value= "状态")
    @TableField("crn_sts")
    private Integer crnSts;

    /**
     * 工作号
     */
    @ApiModelProperty(value= "工作号")
    @TableField("wrk_no")
    private Integer wrkNo;

    /**
     * 异常码
     */
    @ApiModelProperty(value= "异常码")
    @TableField("crn_err")
    private Long crnErr;

    /**
     * 源库位
     */
    @ApiModelProperty(value= "源库位")
    @TableField("frm_locno")
    private String frmLocno;

    /**
     * 源站
     */
    @ApiModelProperty(value= "源站")
    @TableField("frm_sta")
    private Integer frmSta;

    /**
     * 目标站
     */
    @ApiModelProperty(value= "目标站")
    @TableField("to_sta")
    private Integer toSta;

    /**
     * 目标库位
     */
    @ApiModelProperty(value= "目标库位")
    @TableField("to_locno")
    private String toLocno;

    /**
     * 创建者
     */
    @ApiModelProperty(value= "创建者")
    @TableField("appe_user")
    private Long appeUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value= "创建时间")
    @TableField("appe_time")
    private Date appeTime;

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

    @ApiModelProperty(value= "")
    @TableField("hp_mk")
    private String hpMk;

    @ApiModelProperty(value= "")
    @TableField("retrieve_mk")
    private String retrieveMk;

    @ApiModelProperty(value= "")
    @TableField("ctl_hp")
    private String ctlHp;

    @ApiModelProperty(value= "")
    @TableField("ctl_rest")
    private String ctlRest;

    @ApiModelProperty(value= "")
    @TableField("emp_in")
    private String empIn;

    @ApiModelProperty(value= "")
    @TableField("tank_qty")
    private Integer tankQty;

    @ApiModelProperty(value= "")
    @TableField("tank_qty1")
    private Integer tankQty1;

    public BasCrnp() {}

    public BasCrnp(String inEnable,String outEnable,Integer crnSts,Integer wrkNo,Long crnErr,String frmLocno,Integer frmSta,Integer toSta,String toLocno,Long appeUser,Date appeTime,Long modiUser,Date modiTime,String hpMk,String retrieveMk,String ctlHp,String ctlRest,String empIn,Integer tankQty,Integer tankQty1) {
        this.inEnable = inEnable;
        this.outEnable = outEnable;
        this.crnSts = crnSts;
        this.wrkNo = wrkNo;
        this.crnErr = crnErr;
        this.frmLocno = frmLocno;
        this.frmSta = frmSta;
        this.toSta = toSta;
        this.toLocno = toLocno;
        this.appeUser = appeUser;
        this.appeTime = appeTime;
        this.modiUser = modiUser;
        this.modiTime = modiTime;
        this.hpMk = hpMk;
        this.retrieveMk = retrieveMk;
        this.ctlHp = ctlHp;
        this.ctlRest = ctlRest;
        this.empIn = empIn;
        this.tankQty = tankQty;
        this.tankQty1 = tankQty1;
    }

//    BasCrnp basCrnp = new BasCrnp(
//            null,    // 可入
//            null,    // 可出
//            null,    // 状态
//            null,    // 工作号
//            null,    // 异常码
//            null,    // 源库位
//            null,    // 源站
//            null,    // 目标站
//            null,    // 目标库位
//            null,    // 创建者
//            null,    // 创建时间
//            null,    // 修改人员
//            null,    // 修改时间
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null,    // 
//            null    // 
//    );

    public Integer getCrnNo() {
        return crnNo;
    }

    public void setCrnNo(Integer crnNo) {
        this.crnNo = crnNo;
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

    public Integer getCrnSts() {
        return crnSts;
    }

    public String getCrnSts$(){
        BasCrnStatusService service = SpringUtils.getBean(BasCrnStatusService.class);
        BasCrnStatus basCrnStatus = service.selectById(this.crnSts);
        if (!Cools.isEmpty(basCrnStatus)){
            return String.valueOf(basCrnStatus.getStsDesc());
        }
        return null;
    }

    public void setCrnSts(Integer crnSts) {
        this.crnSts = crnSts;
    }

    public Integer getWrkNo() {
        return wrkNo;
    }

    public void setWrkNo(Integer wrkNo) {
        this.wrkNo = wrkNo;
    }

    public Long getCrnErr() {
        return crnErr;
    }

    public String getCrnErr$(){
        BasCrnErrorService service = SpringUtils.getBean(BasCrnErrorService.class);
        BasCrnError basCrnError = service.selectById(this.crnErr);
        if (!Cools.isEmpty(basCrnError)){
            return String.valueOf(basCrnError.getErrName());
        }
        return null;
    }

    public void setCrnErr(Long crnErr) {
        this.crnErr = crnErr;
    }

    public String getFrmLocno() {
        return frmLocno;
    }

    public String getFrmLocno$(){
        LocMastService service = SpringUtils.getBean(LocMastService.class);
        LocMast locMast = service.selectById(this.frmLocno);
        if (!Cools.isEmpty(locMast)){
            return String.valueOf(locMast.getLocNo());
        }
        return null;
    }

    public void setFrmLocno(String frmLocno) {
        this.frmLocno = frmLocno;
    }

    public Integer getFrmSta() {
        return frmSta;
    }

    public String getFrmSta$(){
        BasDevpService service = SpringUtils.getBean(BasDevpService.class);
        BasDevp basDevp = service.selectById(this.frmSta);
        if (!Cools.isEmpty(basDevp)){
            return String.valueOf(basDevp.getDevNo());
        }
        return null;
    }

    public void setFrmSta(Integer frmSta) {
        this.frmSta = frmSta;
    }

    public Integer getToSta() {
        return toSta;
    }

    public String getToSta$(){
        BasDevpService service = SpringUtils.getBean(BasDevpService.class);
        BasDevp basDevp = service.selectById(this.toSta);
        if (!Cools.isEmpty(basDevp)){
            return String.valueOf(basDevp.getDevNo());
        }
        return null;
    }

    public void setToSta(Integer toSta) {
        this.toSta = toSta;
    }

    public String getToLocno() {
        return toLocno;
    }

    public String getToLocno$(){
        LocMastService service = SpringUtils.getBean(LocMastService.class);
        LocMast locMast = service.selectById(this.toLocno);
        if (!Cools.isEmpty(locMast)){
            return String.valueOf(locMast.getLocNo());
        }
        return null;
    }

    public void setToLocno(String toLocno) {
        this.toLocno = toLocno;
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

    public String getHpMk() {
        return hpMk;
    }

    public void setHpMk(String hpMk) {
        this.hpMk = hpMk;
    }

    public String getRetrieveMk() {
        return retrieveMk;
    }

    public void setRetrieveMk(String retrieveMk) {
        this.retrieveMk = retrieveMk;
    }

    public String getCtlHp() {
        return ctlHp;
    }

    public void setCtlHp(String ctlHp) {
        this.ctlHp = ctlHp;
    }

    public String getCtlRest() {
        return ctlRest;
    }

    public void setCtlRest(String ctlRest) {
        this.ctlRest = ctlRest;
    }

    public String getEmpIn() {
        return empIn;
    }

    public void setEmpIn(String empIn) {
        this.empIn = empIn;
    }

    public Integer getTankQty() {
        return tankQty;
    }

    public void setTankQty(Integer tankQty) {
        this.tankQty = tankQty;
    }

    public Integer getTankQty1() {
        return tankQty1;
    }

    public void setTankQty1(Integer tankQty1) {
        this.tankQty1 = tankQty1;
    }


}
