package com.cool.demo.manager.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cool.demo.manager.service.ItemService;
import com.cool.demo.manager.service.TaskService;
import com.cool.demo.system.entity.User;
import com.cool.demo.system.service.UserService;
import com.core.common.Cools;
import com.core.common.SpringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("man_risk")
public class Risk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属检查任务
     */
    @TableField("task_id")
    private Long taskId;

    /**
     * 所属检查项
     */
    @TableField("item_id")
    private Long itemId;

    /**
     * 发布用户
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 隐患描述
     */
    private String content;

    /**
     * 图片集合
     */
    private String img;

    /**
     * 隐患状态 1: 待安排  2: 待批示  3: 待整改  4: 已完成  5: 已驳回  
     */
    private Short state;

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
     * 状态 1: 正常  0: 无效  
     */
    private Short status;

    public Risk() {}

    public Risk(Long taskId,Long itemId,Long userId,String content,String img,Short state,String memo,Date createTime,Date updateTime,Short status) {
        this.taskId = taskId;
        this.itemId = itemId;
        this.userId = userId;
        this.content = content;
        this.img = img;
        this.state = state;
        this.memo = memo;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
    }

//    Risk risk = new Risk(
//            null,    // 所属检查任务
//            null,    // 所属检查项
//            null,    // 发布用户[非空]
//            null,    // 隐患描述
//            null,    // 图片集合
//            null,    // 隐患状态[非空]
//            null,    // 备注
//            null,    // 添加时间[非空]
//            null,    // 修改时间
//            null    // 状态[非空]
//    );

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getTaskId$(){
        TaskService service = SpringUtils.getBean(TaskService.class);
        Task task = service.selectById(this.taskId);
        if (!Cools.isEmpty(task)){
            return task.getName();
        }
        return null;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getItemId$(){
        ItemService service = SpringUtils.getBean(ItemService.class);
        Item item = service.selectById(this.itemId);
        if (!Cools.isEmpty(item)){
            return item.getName();
        }
        return null;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserId$(){
        UserService service = SpringUtils.getBean(UserService.class);
        User user = service.selectById(this.userId);
        if (!Cools.isEmpty(user)){
            return user.getUsername();
        }
        return null;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Short getState() {
        return state;
    }

    public String getState$(){
        if (null == this.state){ return null; }
        switch (this.state){
            case 1:
                return "待安排";
            case 2:
                return "待批示";
            case 3:
                return "待整改";
            case 4:
                return "已完成";
            case 5:
                return "已驳回";
            default:
                return String.valueOf(this.state);
        }
    }

    public void setState(Short state) {
        this.state = state;
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
