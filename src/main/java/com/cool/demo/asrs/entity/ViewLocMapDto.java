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
        switch (locType){
            case "D":
                this.bgc = "#00B271";
                this.color = "#fff";
                break;
            case "F":
                this.bgc = "#479AC7";
                this.color = "#fff";
                break;
            case "O":
                this.bgc = "#B45B3E";
                this.color = "#fff";
                break;
            case "P":
                this.bgc = "#66CCCC";
                this.color = "#fff";
                break;
            case "Q":
                this.bgc = "#5172ef";
                this.color = "#fff";
                break;
            case "R":
                this.bgc = "#D7FFF0";
                this.color = "#fff";
                break;
            case "S":
                this.bgc = "#F0DAD2";
                this.color = "#fff";
                break;
            case "X":
                this.bgc = "#bac296";
                this.color = "#fff";
                break;
            default:
                break;
        }
    }

    public String getBgc() {
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
