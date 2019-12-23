package com.cool.demo.manager.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cool.demo.manager.service.AreaService;
import com.cool.demo.system.service.UserService;
import com.cool.demo.system.entity.User;
import com.core.common.Cools;
import com.core.common.SpringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("man_area")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 上级区域
     */
    @TableField("area_id")
    private Long areaId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 区域等级 1: 一级区域  2: 二级区域  3: 三级区域  4: 四级区域  5: 五级区域  
     */
    private Short level;

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
     * 状态 1: 正常  0: 禁用  
     */
    private Short status;

    public Area() {}

    public Area(String name,Long areaId,Integer sort,Short level,Date createTime,Date updateTime,Long editor,Short status) {
        this.name = name;
        this.areaId = areaId;
        this.sort = sort;
        this.level = level;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.editor = editor;
        this.status = status;
    }

//    Area area = new Area(
//            null,    // 区域名称[非空]
//            null,    // 上级区域
//            null,    // 排序
//            null,    // 区域等级[非空]
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAreaId() {
        return areaId;
    }

    public String getAreaName(){
        AreaService service = SpringUtils.getBean(AreaService.class);
        Area area = service.selectById(this.areaId);
        if (!Cools.isEmpty(area)){
            return area.getName();
        }
        return null;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Short getLevel() {
        return level;
    }

    public String getLevel$(){
        if (null == this.level){ return null; }
        switch (this.level){
            case 1:
                return "一级区域";
            case 2:
                return "二级区域";
            case 3:
                return "三级区域";
            case 4:
                return "四级区域";
            case 5:
                return "五级区域";
            default:
                return String.valueOf(this.level);
        }
    }

    public void setLevel(Short level) {
        this.level = level;
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

    public String getUserUsername(){
        UserService service = SpringUtils.getBean(UserService.class);
        User user = service.selectById(this.editor);
        if (!Cools.isEmpty(user)){
            return user.getUsername();
        }
        return null;
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
                return "禁用";
            default:
                return String.valueOf(this.status);
        }
    }

    public void setStatus(Short status) {
        this.status = status;
    }


}
