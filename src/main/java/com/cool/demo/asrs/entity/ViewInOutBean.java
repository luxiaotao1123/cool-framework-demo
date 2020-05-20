package com.cool.demo.asrs.entity;

/**
 * 日出入库次数统计
 * @author admin
 * @date 2018年11月24日
 */
public class ViewInOutBean {
	private String ymd;
    private String source_sta_no;
    private Long sto_qty;
    private Long ret_qty;
    private Long total_qty;
    private int pageNumber;
    private int pageSize;
    private String begin_date; //查询开始日期
    private String end_date;   //查询截止日期
    
	public String getYmd() {
		return ymd;
	}
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	public String getSource_sta_no() {
		return source_sta_no;
	}
	public void setSource_sta_no(String source_sta_no) {
		this.source_sta_no = source_sta_no;
	}
	public Long getSto_qty() {
		return sto_qty;
	}
	public void setSto_qty(Long sto_qty) {
		this.sto_qty = sto_qty;
	}
	public Long getRet_qty() {
		return ret_qty;
	}
	public void setRet_qty(Long ret_qty) {
		this.ret_qty = ret_qty;
	}
	public Long getTotal_qty() {
		return total_qty;
	}
	public void setTotal_qty(Long total_qty) {
		this.total_qty = total_qty;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
}