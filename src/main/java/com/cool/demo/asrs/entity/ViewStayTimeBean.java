package com.cool.demo.asrs.entity;

/**
 * 库存滞留时间实体类
 * @author admin
 * @date 2018年11月23日
 */
public class ViewStayTimeBean {
	private String lgnum;    	//仓库号
	private int tbnum;          //转储请求编号
	private int tbpos;          //行项目
	private String zmatid;      //物料标签ID
	
	private String matnr;      //物料
	private String maktx;      //物料描述
	private String werks;      //工厂
	private double anfme;	   //数量	
	private String altme;      //单位
	private String zpallet;    //托盘条码
	private String bname;      //用户ID
	
	private String loc_no;      //库位号
    private String mat_no;      //物料编码
    private Long ctns;          //箱数
    private Long qty;           //数量
    private double wt;			//重量
    private String memo;        //备注
    private String mat_name;      //物料名称
    private String str1;
    private String str2;
    private String str3;
    private String str4;
    private String str5;
    private String str6;
    private String str7;
    private String str8;
    private String str9;
    private String str10;
    private String str11;
    private String str12;
    private String str13;
    private String str14;
    private String str15;
    private String str16;
    private String str17;
    private String str18;
    private String str19;
    private String str20;
    private String str21;
    private String str22;
    private String str23;
    private double num1;
    private double num2;
    private double num3;
    private double num4;
    private double num5;
    private double num6;
    private String modi_user;
    private String modi_time;
    private String stay_time;
    private String appe_time;
    private int pageNumber;
    private int pageSize;
    private String begin_date; //查询开始日期
    private String end_date;   //查询截止日期
    private String barcode;
    private String date1;
    
	public String getLgnum() {
		return lgnum;
	}
	public void setLgnum(String lgnum) {
		this.lgnum = lgnum;
	}
	public int getTbnum() {
		return tbnum;
	}
	public void setTbnum(int tbnum) {
		this.tbnum = tbnum;
	}
	public int getTbpos() {
		return tbpos;
	}
	public void setTbpos(int tbpos) {
		this.tbpos = tbpos;
	}
	public String getZmatid() {
		return zmatid;
	}
	public void setZmatid(String zmatid) {
		this.zmatid = zmatid;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public double getAnfme() {
		return anfme;
	}
	public void setAnfme(double anfme) {
		this.anfme = anfme;
	}
	public String getAltme() {
		return altme;
	}
	public void setAltme(String altme) {
		this.altme = altme;
	}
	public String getZpallet() {
		return zpallet;
	}
	public void setZpallet(String zpallet) {
		this.zpallet = zpallet;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getLoc_no() {
		return loc_no;
	}
	public void setLoc_no(String loc_no) {
		this.loc_no = loc_no;
	}
	/**
	 * @return the mat_no
	 */
	public String getMat_no() {
		return mat_no;
	}
	/**
	 * @param mat_no the mat_no to set
	 */
	public void setMat_no(String mat_no) {
		this.mat_no = mat_no;
	}
	public Long getCtns() {
		return ctns;
	}
	public void setCtns(Long ctns) {
		this.ctns = ctns;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	public double getWt() {
		return wt;
	}
	public void setWt(double wt) {
		this.wt = wt;
	}
	public String getStay_time() {
		return stay_time;
	}
	public void setStay_time(String stay_time) {
		this.stay_time = stay_time;
	}
	/**
	 * @return the mat_name
	 */
	public String getMat_name() {
		return mat_name;
	}
	/**
	 * @param mat_name the mat_name to set
	 */
	public void setMat_name(String mat_name) {
		this.mat_name = mat_name;
	}
	/**
	 * @return the str1
	 */
	public String getStr1() {
		return str1;
	}
	/**
	 * @param str1 the str1 to set
	 */
	public void setStr1(String str1) {
		this.str1 = str1;
	}
	/**
	 * @return the str2
	 */
	public String getStr2() {
		return str2;
	}
	/**
	 * @param str2 the str2 to set
	 */
	public void setStr2(String str2) {
		this.str2 = str2;
	}
	/**
	 * @return the str3
	 */
	public String getStr3() {
		return str3;
	}
	/**
	 * @param str3 the str3 to set
	 */
	public void setStr3(String str3) {
		this.str3 = str3;
	}
	/**
	 * @return the str4
	 */
	public String getStr4() {
		return str4;
	}
	/**
	 * @param str4 the str4 to set
	 */
	public void setStr4(String str4) {
		this.str4 = str4;
	}
	/**
	 * @return the str5
	 */
	public String getStr5() {
		return str5;
	}
	/**
	 * @param str5 the str5 to set
	 */
	public void setStr5(String str5) {
		this.str5 = str5;
	}
	/**
	 * @return the str6
	 */
	public String getStr6() {
		return str6;
	}
	/**
	 * @param str6 the str6 to set
	 */
	public void setStr6(String str6) {
		this.str6 = str6;
	}
	/**
	 * @return the str7
	 */
	public String getStr7() {
		return str7;
	}
	/**
	 * @param str7 the str7 to set
	 */
	public void setStr7(String str7) {
		this.str7 = str7;
	}
	/**
	 * @return the str8
	 */
	public String getStr8() {
		return str8;
	}
	/**
	 * @param str8 the str8 to set
	 */
	public void setStr8(String str8) {
		this.str8 = str8;
	}
	/**
	 * @return the str9
	 */
	public String getStr9() {
		return str9;
	}
	/**
	 * @param str9 the str9 to set
	 */
	public void setStr9(String str9) {
		this.str9 = str9;
	}
	/**
	 * @return the str10
	 */
	public String getStr10() {
		return str10;
	}
	/**
	 * @param str10 the str10 to set
	 */
	public void setStr10(String str10) {
		this.str10 = str10;
	}
	/**
	 * @return the num1
	 */
	public double getNum1() {
		return num1;
	}
	/**
	 * @param num1 the num1 to set
	 */
	public void setNum1(double num1) {
		this.num1 = num1;
	}
	/**
	 * @return the num2
	 */
	public double getNum2() {
		return num2;
	}
	/**
	 * @param num2 the num2 to set
	 */
	public void setNum2(double num2) {
		this.num2 = num2;
	}
	/**
	 * @return the num3
	 */
	public double getNum3() {
		return num3;
	}
	/**
	 * @param num3 the num3 to set
	 */
	public void setNum3(double num3) {
		this.num3 = num3;
	}
	/**
	 * @return the num4
	 */
	public double getNum4() {
		return num4;
	}
	/**
	 * @param num4 the num4 to set
	 */
	public void setNum4(double num4) {
		this.num4 = num4;
	}
	/**
	 * @return the num5
	 */
	public double getNum5() {
		return num5;
	}
	/**
	 * @param num5 the num5 to set
	 */
	public void setNum5(double num5) {
		this.num5 = num5;
	}
	/**
	 * @return the modi_user
	 */
	public String getModi_user() {
		return modi_user;
	}
	/**
	 * @param modi_user the modi_user to set
	 */
	public void setModi_user(String modi_user) {
		this.modi_user = modi_user;
	}
	/**
	 * @return the modi_time
	 */
	public String getModi_time() {
		return modi_time;
	}
	/**
	 * @param modi_time the modi_time to set
	 */
	public void setModi_time(String modi_time) {
		this.modi_time = modi_time;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getAppe_time() {
		return appe_time;
	}
	public void setAppe_time(String appe_time) {
		this.appe_time = appe_time;
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
	public String getStr11() {
		return str11;
	}
	public void setStr11(String str11) {
		this.str11 = str11;
	}
	public String getStr12() {
		return str12;
	}
	public void setStr12(String str12) {
		this.str12 = str12;
	}
	public String getStr13() {
		return str13;
	}
	public void setStr13(String str13) {
		this.str13 = str13;
	}
	public String getStr14() {
		return str14;
	}
	public void setStr14(String str14) {
		this.str14 = str14;
	}
	public String getStr15() {
		return str15;
	}
	public void setStr15(String str15) {
		this.str15 = str15;
	}
	public String getStr16() {
		return str16;
	}
	public void setStr16(String str16) {
		this.str16 = str16;
	}
	public String getStr17() {
		return str17;
	}
	public void setStr17(String str17) {
		this.str17 = str17;
	}
	public String getStr18() {
		return str18;
	}
	public void setStr18(String str18) {
		this.str18 = str18;
	}
	public String getStr19() {
		return str19;
	}
	public void setStr19(String str19) {
		this.str19 = str19;
	}
	public String getStr20() {
		return str20;
	}
	public void setStr20(String str20) {
		this.str20 = str20;
	}
	public String getStr21() {
		return str21;
	}
	public void setStr21(String str21) {
		this.str21 = str21;
	}
	public String getStr22() {
		return str22;
	}
	public void setStr22(String str22) {
		this.str22 = str22;
	}
	public String getStr23() {
		return str23;
	}
	public void setStr23(String str23) {
		this.str23 = str23;
	}
	public double getNum6() {
		return num6;
	}
	public void setNum6(double num6) {
		this.num6 = num6;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getDate1() {
		return date1;
	}
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	
}