package com.cool.demo.asrs.entity;

/**
 * Created by vincent on 2020-05-20
 */
public class ViewLocMapDto {

    // 库位号
    private String locNo;
    // 列
    private Integer bay1;
    // 库位状态
    private String locType;
    // 背景色
    private String bgc = "#fff";
    // 字体颜色
    private String color = "#666";

    public ViewLocMapDto() {
    }

    public ViewLocMapDto(String locNo, Integer bay1, String locType) {
        this.locNo = locNo;
        this.bay1 = bay1;
        this.locType = locType;
        this.bgc = getBgc();
    }

    public String getLocNo() {
        return locNo;
    }

    public void setLocNo(String locNo) {
        this.locNo = locNo;
    }

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

    public String getBgc() {
        if (locType.equals("F")){
            return "#000";
        }
        return bgc;
    }

    public void setBgc(String bgc) {
        this.bgc = bgc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
