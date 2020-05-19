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

@TableName("asr_row_lastno")
public class RowLastno implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类别
     */
    @ApiModelProperty(value= "类别")
    @TableId(value = "whs_type", type = IdType.INPUT)
    @TableField("whs_type")
    private Integer whsType;

    /**
     * 当前工作号
     */
    @ApiModelProperty(value= "当前工作号")
    @TableField("wrk_mk")
    private String wrkMk;

    /**
     * 当前排号
     */
    @ApiModelProperty(value= "当前排号")
    @TableField("current_row")
    private Integer currentRow;

    /**
     * 起始排号
     */
    @ApiModelProperty(value= "起始排号")
    @TableField("s_row")
    private Integer sRow;

    /**
     * 终止排号
     */
    @ApiModelProperty(value= "终止排号")
    @TableField("e_row")
    private Integer eRow;

    /**
     * 堆垛机数量
     */
    @ApiModelProperty(value= "堆垛机数量")
    @TableField("crn_qty")
    private Integer crnQty;

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

    @ApiModelProperty(value= "")
    @TableField("limint_loc")
    private Integer limintLoc;

    public RowLastno() {}

    public RowLastno(String wrkMk,Integer currentRow,Integer sRow,Integer eRow,Integer crnQty,String memo,Long modiUser,Date modiTime,Long appeUser,Date appeTime,Integer limintLoc) {
        this.wrkMk = wrkMk;
        this.currentRow = currentRow;
        this.sRow = sRow;
        this.eRow = eRow;
        this.crnQty = crnQty;
        this.memo = memo;
        this.modiUser = modiUser;
        this.modiTime = modiTime;
        this.appeUser = appeUser;
        this.appeTime = appeTime;
        this.limintLoc = limintLoc;
    }

//    RowLastno rowLastno = new RowLastno(
//            null,    // 当前工作号[非空]
//            null,    // 当前排号
//            null,    // 起始排号
//            null,    // 终止排号
//            null,    // 堆垛机数量
//            null,    // 备注
//            null,    // 修改人员
//            null,    // 修改时间
//            null,    // 创建者
//            null,    // 添加时间
//            null    // 
//    );

    public Integer getWhsType() {
        return whsType;
    }

    public void setWhsType(Integer whsType) {
        this.whsType = whsType;
    }

    public String getWrkMk() {
        return wrkMk;
    }

    public void setWrkMk(String wrkMk) {
        this.wrkMk = wrkMk;
    }

    public Integer getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(Integer currentRow) {
        this.currentRow = currentRow;
    }

    public Integer getSRow() {
        return sRow;
    }

    public void setSRow(Integer sRow) {
        this.sRow = sRow;
    }

    public Integer getERow() {
        return eRow;
    }

    public void setERow(Integer eRow) {
        this.eRow = eRow;
    }

    public Integer getCrnQty() {
        return crnQty;
    }

    public void setCrnQty(Integer crnQty) {
        this.crnQty = crnQty;
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

    public Integer getLimintLoc() {
        return limintLoc;
    }

    public void setLimintLoc(Integer limintLoc) {
        this.limintLoc = limintLoc;
    }


}
