package com.cool.demo.asrs.entity;

import com.core.common.Arith;

/**
 * Created by vincent on 2020-05-23
 */
public class PieChartsVo {

    // 总库位
    private Long totalQty = 0L;
    private String totalDes = "";
    //在库数量
    private Long fullQty = 0L;
    private String fullDes = "在库库位";
    //空库位
    private Long nullQty = 0L;
    private String nullDes = "空库位";
    //禁用库位
    private Long forbidQty = 0L;
    private String forbidDes = "禁用库位";
    //使用库位
    private Long occQty = 0L;
    private String occDes = "使用库位";

    public void complete(){
        fullDes = fullDes.concat(Arith.multiplys(1, Arith.divides(3, fullQty, totalQty), 100)+"%");
        nullDes = nullDes.concat(Arith.multiplys(1,Arith.divides(3, nullQty, totalQty), 100)+"%");
        forbidDes = forbidDes.concat(Arith.multiplys(1,Arith.divides(4, forbidQty, totalQty), 100)+"%");
        occDes = occDes.concat(Arith.multiplys(1,Arith.divides(3, occQty, totalQty), 100)+"%");
    }

    public Long getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }

    public Long getFullQty() {
        return fullQty;
    }

    public void setFullQty(Long fullQty) {
        this.fullQty = fullQty;
    }

    public Long getNullQty() {
        return nullQty;
    }

    public void setNullQty(Long nullQty) {
        this.nullQty = nullQty;
    }

    public Long getForbidQty() {
        return forbidQty;
    }

    public void setForbidQty(Long forbidQty) {
        this.forbidQty = forbidQty;
    }

    public Long getOccQty() {
        return occQty;
    }

    public void setOccQty(Long occQty) {
        this.occQty = occQty;
    }

    public String getTotalDes() {
        return totalDes;
    }

    public void setTotalDes(String totalDes) {
        this.totalDes = totalDes;
    }

    public String getFullDes() {
        return fullDes;
    }

    public void setFullDes(String fullDes) {
        this.fullDes = fullDes;
    }

    public String getNullDes() {
        return nullDes;
    }

    public void setNullDes(String nullDes) {
        this.nullDes = nullDes;
    }

    public String getForbidDes() {
        return forbidDes;
    }

    public void setForbidDes(String forbidDes) {
        this.forbidDes = forbidDes;
    }

    public String getOccDes() {
        return occDes;
    }

    public void setOccDes(String occDes) {
        this.occDes = occDes;
    }
}
