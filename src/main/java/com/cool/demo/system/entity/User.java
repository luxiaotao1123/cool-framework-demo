package com.cool.demo.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.core.common.Cools;
import com.core.common.SpringUtils;
import com.cool.demo.system.service.DeptService;
import com.cool.demo.system.service.HostService;
import com.cool.demo.system.service.RoleService;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

//import com.cool.demo.system.service.RoleService;

@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 授权商户
     */
    @TableField("host_id")
    private Long hostId;


    /**
     * 所属部门
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 角色
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 账号
     */
    private String username;

    /**
     * 名称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别 0: 男  1:  女   2:  未知
     */
    private Integer sex;

    /**
     * 注册时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 状态 1: 启用  0: 禁用
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

    public Long getHostId() {
        return hostId;
    }

    public String getHostName() {
        HostService service = SpringUtils.getBean(HostService.class);
        Host host = service.selectById(this.hostId);
        if (!Cools.isEmpty(host)){
            return host.getName();
        }
        return null;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public String getDeptName() {
        DeptService service = SpringUtils.getBean(DeptService.class);
        Dept dept = service.selectById(this.deptId);
        if (!Cools.isEmpty(dept)){
            return dept.getName();
        }
        return null;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public String getSex$(){
        if (null == this.sex){ return null; }
        switch (this.sex){
            case 0:
                return "男";
            case 1:
                return "女";
            case 2:
                return "未知";
            default:
                return String.valueOf(this.sex);
        }
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getRoleName(){
        RoleService service = SpringUtils.getBean(RoleService.class);
        Role role = service.selectById(this.roleId);
        if (!Cools.isEmpty(role)){
            return role.getName();
        }
        return null;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public Integer getStatus() {
        return status;
    }

    public String getStatus$(){
        if (null == this.status){ return null; }
        switch (this.status){
            case 1:
                return "启用";
            case 0:
                return "禁用";
            default:
                return String.valueOf(this.status);
        }
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
