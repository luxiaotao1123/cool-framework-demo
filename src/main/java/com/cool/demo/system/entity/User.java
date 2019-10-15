package com.cool.demo.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cool.demo.system.service.RoleService;
import com.core.common.Cools;
import com.core.common.SpringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 注册时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 状态 1: 启用  2: 冻结  3: 删除  
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName(){
        RoleService roleService = SpringUtils.getBean(RoleService.class);
        Role role = roleService.selectById(this.roleId);
        if (!Cools.isEmpty(role)){
            return role.getName();
        }
        return null;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCreateTime$(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatus$(){
        if (null == this.status){ return null; }
        switch (this.status){
            case 1:
                return "启用";
            case 2:
                return "冻结";
            case 3:
                return "删除";
            default:
                return String.valueOf(this.status);
        }
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
