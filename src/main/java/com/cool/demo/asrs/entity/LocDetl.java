package com.cool.demo.asrs.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cool.demo.asrs.service.LocMastService;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.SpringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("asr_loc_detl")
public class LocDetl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 库位号
     */
    @ApiModelProperty(value= "库位号")
    @TableId(value = "loc_no", type = IdType.INPUT)
    @TableField("loc_no")
    private String locNo;

    /**
     * 物料
     */
    @ApiModelProperty(value= "物料")
    @TableId(value = "matnr", type = IdType.INPUT)
    private String matnr;

    /**
     * 仓库号
     */
    @ApiModelProperty(value= "仓库号")
    private String lgnum;

    /**
     * 转储请求编号
     */
    @ApiModelProperty(value= "转储请求编号")
    private Integer tbnum;

    /**
     * 行项目
     */
    @ApiModelProperty(value= "行项目")
    private Integer tbpos;

    /**
     * 物料标签ID
     */
    @ApiModelProperty(value= "物料标签ID")
    private String zmatid;

    /**
     * 物料描述
     */
    @ApiModelProperty(value= "物料描述")
    private String maktx;

    /**
     * 工厂
     */
    @ApiModelProperty(value= "工厂")
    private String werks;

    /**
     * 数量
     */
    @ApiModelProperty(value= "数量")
    private Double anfme;

    /**
     * 单位
     */
    @ApiModelProperty(value= "单位")
    private String altme;

    /**
     * 托盘条码
     */
    @ApiModelProperty(value= "托盘条码")
    private String zpallet;

    /**
     * 用户ID
     */
    @ApiModelProperty(value= "用户ID")
    private String bname;

    /**
     * 备注
     */
    @ApiModelProperty(value= "备注")
    private String memo;

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

    public LocDetl() {}

    public LocDetl(String lgnum,Integer tbnum,Integer tbpos,String zmatid,String maktx,String werks,Double anfme,String altme,String zpallet,String bname,String memo,Long modiUser,Date modiTime,Long appeUser,Date appeTime) {
        this.lgnum = lgnum;
        this.tbnum = tbnum;
        this.tbpos = tbpos;
        this.zmatid = zmatid;
        this.maktx = maktx;
        this.werks = werks;
        this.anfme = anfme;
        this.altme = altme;
        this.zpallet = zpallet;
        this.bname = bname;
        this.memo = memo;
        this.modiUser = modiUser;
        this.modiTime = modiTime;
        this.appeUser = appeUser;
        this.appeTime = appeTime;
    }

//    LocDetl locDetl = new LocDetl(
//            null,    // 仓库号[非空]
//            null,    // 转储请求编号[非空]
//            null,    // 行项目[非空]
//            null,    // 物料标签ID[非空]
//            null,    // 物料描述
//            null,    // 工厂
//            null,    // 数量
//            null,    // 单位
//            null,    // 托盘条码
//            null,    // 用户ID
//            null,    // 备注
//            null,    // 修改人员
//            null,    // 修改时间
//            null,    // 创建者
//            null    // 添加时间
//    );

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

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getLgnum() {
        return lgnum;
    }

    public void setLgnum(String lgnum) {
        this.lgnum = lgnum;
    }

    public Integer getTbnum() {
        return tbnum;
    }

    public void setTbnum(Integer tbnum) {
        this.tbnum = tbnum;
    }

    public Integer getTbpos() {
        return tbpos;
    }

    public void setTbpos(Integer tbpos) {
        this.tbpos = tbpos;
    }

    public String getZmatid() {
        return zmatid;
    }

    public void setZmatid(String zmatid) {
        this.zmatid = zmatid;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = maktx;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public Double getAnfme() {
        return anfme;
    }

    public void setAnfme(Double anfme) {
        this.anfme = anfme;
    }

    public String getAltme() {
        return altme;
    }

    public void setAltme(String altme) {
        this.altme = altme;
    }

    public String getZpallet() {
        return zpallet;
    }

    public void setZpallet(String zpallet) {
        this.zpallet = zpallet;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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


}
