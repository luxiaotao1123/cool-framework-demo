package com.cool.demo.manager.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.SpringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("man_task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务编号
     */
    private String uuid;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 检查方式 1: 学校自查  2: 总部自查  3: 总部督查  
     */
    private Short type;

    /**
     * 检查组长
     */
    private Long leader;

    /**
     * 工作组员
     */
    private String members;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 任务状态 1: 待分配  2: 未开始  3: 进行中  4: 已完成  
     */
    private Short state;

    /**
     * 发起用户
     */
    private Long sponsor;

    /**
     * 备注
     */
    private String memo;

    /**
     * 添加时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 操作用户
     */
    private Long editor;

    /**
     * 状态 1: 正常  0: 无效  
     */
    private Short status;

    public Task() {}

    public Task(String uuid,String name,Short type,Long leader,String members,Date startTime,Date endTime,Short state,Long sponsor,String memo,Date createTime,Date updateTime,Long editor,Short status) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.leader = leader;
        this.members = members;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.sponsor = sponsor;
        this.memo = memo;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.editor = editor;
        this.status = status;
    }

//    Task task = new Task(
//            null,    // 任务编号[非空]
//            null,    // 任务名称[非空]
//            null,    // 检查方式[非空]
//            null,    // 检查组长[非空]
//            null,    // 工作组员
//            null,    // 开始时间[非空]
//            null,    // 结束时间[非空]
//            null,    // 任务状态[非空]
//            null,    // 发起用户[非空]
//            null,    // 备注
//            null,    // 添加时间[非空]
//            null,    // 修改时间
//            null,    // 操作用户
//            null    // 状态[非空]
//    );

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getType() {
        return type;
    }

    public String getType$(){
        if (null == this.type){ return null; }
        switch (this.type){
            case 1:
                return "学校自查";
            case 2:
                return "总部自查";
            case 3:
                return "总部督查";
            default:
                return String.valueOf(this.type);
        }
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Long getLeader() {
        return leader;
    }

    public String getUserUsername(){
        UserService service = SpringUtils.getBean(UserService.class);
        User user = service.selectById(this.leader);
        if (!Cools.isEmpty(user)){
            return user.getUsername();
        }
        return null;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getStartTime$(){
        if (Cools.isEmpty(this.startTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.startTime);
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getEndTime$(){
        if (Cools.isEmpty(this.endTime)){
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endTime);
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Short getState() {
        return state;
    }

    public String getState$(){
        if (null == this.state){ return null; }
        switch (this.state){
            case 1:
                return "待分配";
            case 2:
                return "未开始";
            case 3:
                return "进行中";
            case 4:
                return "已完成";
            default:
                return String.valueOf(this.state);
        }
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Long getSponsor() {
        return sponsor;
    }

//    public String getUserUsername(){
//        UserService service = SpringUtils.getBean(UserService.class);
//        User user = service.selectById(this.sponsor);
//        if (!Cools.isEmpty(user)){
//            return user.getUsername();
//        }
//        return null;
//    }

    public void setSponsor(Long sponsor) {
        this.sponsor = sponsor;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public Long getEditor() {
        return editor;
    }

    public void setEditor(Long editor) {
        this.editor = editor;
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
                return "无效";
            default:
                return String.valueOf(this.status);
        }
    }

    public void setStatus(Short status) {
        this.status = status;
    }


}
