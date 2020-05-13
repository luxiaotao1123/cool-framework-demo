package com.cool.demo.system.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cool.demo.system.service.RoleService;
import com.core.common.Cools;
import com.core.common.SpringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value= "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 编码
     */
    @ApiModelProperty(value= "编码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value= "名称")
    private String name;

    /**
     * 上级
     */
    @ApiModelProperty(value= "上级")
    private Long leader;

    /**
     * 角色等级 1: 一级  2: 二级  3: 三级  4: 四级  5: 五级  
     */
    @ApiModelProperty(value= "角色等级 1: 一级  2: 二级  3: 三级  4: 四级  5: 五级  ")
    private Short level;

    public Role() {}

    public Role(String code,String name,Long leader,Short level) {
        this.code = code;
        this.name = name;
        this.leader = leader;
        this.level = level;
    }

//    Role role = new Role(
//            null,    // 编码[非空]
//            null,    // 名称[非空]
//            null,    // 上级
//            null    // 角色等级
//    );

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLeader() {
        return leader;
    }

    public String getLeader$(){
        RoleService service = SpringUtils.getBean(RoleService.class);
        Role role = service.selectById(this.leader);
        if (!Cools.isEmpty(role)){
            return String.valueOf(role.getName());
        }
        return null;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public Short getLevel() {
        return level;
    }

    public String getLevel$(){
        if (null == this.level){ return null; }
        switch (this.level){
            case 1:
                return "一级";
            case 2:
                return "二级";
            case 3:
                return "三级";
            case 4:
                return "四级";
            case 5:
                return "五级";
            default:
                return String.valueOf(this.level);
        }
    }

    public void setLevel(Short level) {
        this.level = level;
    }


}
