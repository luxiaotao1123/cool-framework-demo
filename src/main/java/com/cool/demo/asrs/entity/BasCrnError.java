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

@TableName("asr_bas_crn_error")
public class BasCrnError implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 异常代号
     */
    @ApiModelProperty(value= "异常代号")
    @TableId(value = "error_code", type = IdType.AUTO)
    @TableField("error_code")
    private Long errorCode;

    /**
     * 异常描述
     */
    @ApiModelProperty(value= "异常描述")
    @TableField("err_name")
    private String errName;

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

    public BasCrnError() {}

    public BasCrnError(String errName,Long modiUser,Date modiTime,Long appeUser,Date appeTime) {
        this.errName = errName;
        this.modiUser = modiUser;
        this.modiTime = modiTime;
        this.appeUser = appeUser;
        this.appeTime = appeTime;
    }

//    BasCrnError basCrnError = new BasCrnError(
//            null,    // 异常描述
//            null,    // 修改人员
//            null,    // 修改时间
//            null,    // 创建者
//            null    // 添加时间
//    );

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrName() {
        return errName;
    }

    public void setErrName(String errName) {
        this.errName = errName;
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
