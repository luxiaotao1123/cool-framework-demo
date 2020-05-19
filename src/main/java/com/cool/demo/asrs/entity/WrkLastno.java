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

@TableName("asr_wrk_lastno")
public class WrkLastno implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    @ApiModelProperty(value= "类型")
    @TableId(value = "wrk_mk", type = IdType.INPUT)
    @TableField("wrk_mk")
    private Integer wrkMk;

    /**
     * 当前ID
     */
    @ApiModelProperty(value= "当前ID")
    @TableField("wrk_no")
    private Integer wrkNo;

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

    /**
     * 起始ID
     */
    @ApiModelProperty(value= "起始ID")
    @TableField("s_no")
    private Integer sNo;

    /**
     * 终止ID
     */
    @ApiModelProperty(value= "终止ID")
    @TableField("e_no")
    private Integer eNo;

    /**
     * 备注
     */
    @ApiModelProperty(value= "备注")
    @TableField("memo_m")
    private String memoM;

    public WrkLastno() {}

    public WrkLastno(Integer wrkNo,Long modiUser,Date modiTime,Long appeUser,Date appeTime,Integer sNo,Integer eNo,String memoM) {
        this.wrkNo = wrkNo;
        this.modiUser = modiUser;
        this.modiTime = modiTime;
        this.appeUser = appeUser;
        this.appeTime = appeTime;
        this.sNo = sNo;
        this.eNo = eNo;
        this.memoM = memoM;
    }

//    WrkLastno wrkLastno = new WrkLastno(
//            null,    // 当前ID[非空]
//            null,    // 修改人员
//            null,    // 修改时间
//            null,    // 创建者
//            null,    // 添加时间
//            null,    // 起始ID[非空]
//            null,    // 终止ID[非空]
//            null    // 备注
//    );

    public Integer getWrkMk() {
        return wrkMk;
    }

    public void setWrkMk(Integer wrkMk) {
        this.wrkMk = wrkMk;
    }

    public Integer getWrkNo() {
        return wrkNo;
    }

    public void setWrkNo(Integer wrkNo) {
        this.wrkNo = wrkNo;
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

    public Integer getSNo() {
        return sNo;
    }

    public void setSNo(Integer sNo) {
        this.sNo = sNo;
    }

    public Integer getENo() {
        return eNo;
    }

    public void setENo(Integer eNo) {
        this.eNo = eNo;
    }

    public String getMemoM() {
        return memoM;
    }

    public void setMemoM(String memoM) {
        this.memoM = memoM;
    }


}
