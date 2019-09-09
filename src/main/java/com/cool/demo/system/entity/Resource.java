package com.cool.demo.system.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

@TableName("sys_resource")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单编码
     */
    private String code;

    /**
     * 父级菜单编码
     */
    private String pcode;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单等级 1: 一级菜单  2: 二级菜单  
     */
    private Integer level;

    /**
     * 状态 0: 失效  1: 有效  
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public String getLevel$(){
        if (null == this.level){ return null; }
        switch (this.level){
            case 1:
                return "一级菜单";
            case 2:
                return "二级菜单";
            default:
                return String.valueOf(this.level);
        }
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatus$(){
        if (null == this.status){ return null; }
        switch (this.status){
            case 0:
                return "失效";
            case 1:
                return "有效";
            default:
                return String.valueOf(this.status);
        }
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
