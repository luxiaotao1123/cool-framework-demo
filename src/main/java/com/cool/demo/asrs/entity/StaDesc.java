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

@TableName("asr_sta_desc")
public class StaDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 路径ID
     */
    @ApiModelProperty(value= "路径ID")
    @TableField("type_id")
    private Long typeId;

    /**
     * 入出代号
     */
    @ApiModelProperty(value= "入出代号")
    @TableId(value = "type_no", type = IdType.INPUT)
    @TableField("type_no")
    private Integer typeNo;

    /**
     * 作业类型
     */
    @ApiModelProperty(value= "作业类型")
    @TableField("type_desc")
    private String typeDesc;

    /**
     * 作业站点
     */
    @ApiModelProperty(value= "作业站点")
    @TableId(value = "stn_no", type = IdType.INPUT)
    @TableField("stn_no")
    private Integer stnNo;

    /**
     * 站点名称
     */
    @ApiModelProperty(value= "站点名称")
    @TableField("stn_desc")
    private String stnDesc;

    /**
     * 堆垛机号
     */
    @ApiModelProperty(value= "堆垛机号")
    @TableId(value = "crn_no", type = IdType.INPUT)
    @TableField("crn_no")
    private Integer crnNo;

    /**
     * 堆垛机站点
     */
    @ApiModelProperty(value= "堆垛机站点")
    @TableField("crn_stn")
    private Integer crnStn;

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

    public StaDesc() {}

    public StaDesc(Long typeId,String typeDesc,String stnDesc,Integer crnStn,String memo,Long modiUser,Date modiTime,Long appeUser,Date appeTime) {
        this.typeId = typeId;
        this.typeDesc = typeDesc;
        this.stnDesc = stnDesc;
        this.crnStn = crnStn;
        this.memo = memo;
        this.modiUser = modiUser;
        this.modiTime = modiTime;
        this.appeUser = appeUser;
        this.appeTime = appeTime;
    }

//    StaDesc staDesc = new StaDesc(
//            null,    // 路径ID[非空]
//            null,    // 作业类型
//            null,    // 站点名称
//            null,    // 堆垛机站点
//            null,    // 备注
//            null,    // 修改人员
//            null,    // 修改时间
//            null,    // 创建者
//            null    // 添加时间
//    );

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Integer getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(Integer typeNo) {
        this.typeNo = typeNo;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public Integer getStnNo() {
        return stnNo;
    }

    public void setStnNo(Integer stnNo) {
        this.stnNo = stnNo;
    }

    public String getStnDesc() {
        return stnDesc;
    }

    public void setStnDesc(String stnDesc) {
        this.stnDesc = stnDesc;
    }

    public Integer getCrnNo() {
        return crnNo;
    }

    public void setCrnNo(Integer crnNo) {
        this.crnNo = crnNo;
    }

    public Integer getCrnStn() {
        return crnStn;
    }

    public void setCrnStn(Integer crnStn) {
        this.crnStn = crnStn;
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
