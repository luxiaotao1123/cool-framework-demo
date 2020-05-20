package com.cool.demo.asrs.entity;

/**
 * 库位使用率视图实体类
 * @author admin
 * @date 2018年11月23日
 */
public class ViewStockUseBean {
    private String row1;    	//钢架号
    private Long total_qty;    	//库位总数
    private Long full_qty;    	//在库数量
    private Long null_qty;    	//空库位
    private Long forbid_qty;    //禁用库位
    private Long empty_qty;		//空容器
    private String full_rate;   //在库率
    private String occ_rate;   //使用率
    private int pageNumber;
    private int pageSize;
    
	public String getRow1() {
		return row1;
	}
	public void setRow1(String row1) {
		this.row1 = row1;
	}
	public Long getTotal_qty() {
		return total_qty;
	}
	public void setTotal_qty(Long total_qty) {
		this.total_qty = total_qty;
	}
	public Long getFull_qty() {
		return full_qty;
	}
	public void setFull_qty(Long full_qty) {
		this.full_qty = full_qty;
	}
	public Long getNull_qty() {
		return null_qty;
	}
	public void setNull_qty(Long null_qty) {
		this.null_qty = null_qty;
	}
	public Long getForbid_qty() {
		return forbid_qty;
	}
	public void setForbid_qty(Long forbid_qty) {
		this.forbid_qty = forbid_qty;
	}
	public Long getEmpty_qty() {
		return empty_qty;
	}
	public void setEmpty_qty(Long empty_qty) {
		this.empty_qty = empty_qty;
	}
	public String getFull_rate() {
		return full_rate;
	}
	public void setFull_rate(String full_rate) {
		this.full_rate = full_rate;
	}
	public String getOcc_rate() {
		return occ_rate;
	}
	public void setOcc_rate(String occ_rate) {
		this.occ_rate = occ_rate;
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

}