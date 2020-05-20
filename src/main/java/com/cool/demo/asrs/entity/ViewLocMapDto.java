package com.cool.demo.asrs.entity;

/**
 * Created by vincent on 2020-05-20
 */
public class ViewLocMapDto {

    // 列
    private Integer bay1;
    // 库位状态
    private String locType;

    public Integer getBay1() {
        return bay1;
    }

    public void setBay1(Integer bay1) {
        this.bay1 = bay1;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }
}
