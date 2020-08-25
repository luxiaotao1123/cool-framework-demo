package com.cool.demo.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.core.common.Cools;
import com.core.common.SpringUtils;
import com.cool.demo.system.service.DeptService;
import com.cool.demo.system.service.UserService;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("sys_dept")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门编号
     */
    @ApiModelProperty(value= "部门编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父部门编号
     */
    @ApiModelProperty(value= "父部门编号")
    @TableField("parent_id")
    private Long parentId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value= "部门名称")
    private String name;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value= "显示顺序")
    private Integer sort;

    /**
     * 负责人
     */
    @ApiModelProperty(value= "负责人")
    private Long leader;

    /**
     * 联系电话
     */
    @ApiModelProperty(value= "联系电话")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value= "邮箱")
    private String email;

    /**
     * 部门状态 1: 正常;0  
     */
    @ApiModelProperty(value= "部门状态 1: 正常;0  ")
    private Short status;

    /**
     * 创建者
     */
    @ApiModelProperty(value= "创建者")
    @TableField("create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value= "创建时间")
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value= "更新者")
    @TableField("update_by")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value= "更新时间")
    @TableField("update_time")
    private Date updateTime;

    public Dept() {}

    public Dept(Long parentId,String name,Integer sort,Long leader,String phone,String email,Short status,Long createBy,Date createTime,Long updateBy,Date updateTime) {
        this.parentId = parentId;
        this.name = name;
        this.sort = sort;
        this.leader = leader;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
    }

//    Dept dept = new Dept(
//            null,    // 父部门编号
//            null,    // 部门名称[非空]
//            null,    // 显示顺序
//            null,    // 负责人
//            null,    // 联系电话
//            null,    // 邮箱
//            null,    // 部门状态
//            null,    // 创建者
//            null,    // 创建时间
//            null,    // 更新者
//            null    // 更新时间
//    );

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getParentId$() {
        DeptService service = SpringUtils.getBean(DeptService.class);
        Dept dept = service.selectById(this.parentId);
        if (!Cools.isEmpty(dept)){
            return dept.getName();
        }
        return null;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getLeader() {
        return leader;
    }

    public String getLeader$(){
        UserService service = SpringUtils.getBean(UserService.class);
        User user = service.selectById(this.leader);
        if (!Cools.isEmpty(user)){
            return String.valueOf(user.getNickname());
        }
        return null;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getStatus() {
        return status;
    }

    public String getStatus$(){
        if (null == this.status){ return null; }
        switch (this.status){
            case 1:
                return "正常";
            case 0:
                return "停用";
            default:
                return String.valueOf(this.status);
        }
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCreateTime$(){
        if (Cools.isEmpty(this.createTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getUpdateTime$(){
        if (Cools.isEmpty(this.updateTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.updateTime);
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
