package com.cool.demo.asrs.entity;

/**
 * 入出库统计曲线图
 * @author admin
 * @date 2018年12月12日
 */
public class WorkChartAxis {
	private String ymd;
    private int inqty;
    private int outqty;
    
	public String getYmd() {
		return ymd;
	}
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	public int getInqty() {
		return inqty;
	}
	public void setInqty(int inqty) {
		this.inqty = inqty;
	}
	public int getOutqty() {
		return outqty;
	}
	public void setOutqty(int outqty) {
		this.outqty = outqty;
	}
}