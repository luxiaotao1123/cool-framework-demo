package com.cool.demo.manager.excel;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 0.A 1.B 2.C 3.D 4.E 5.F 6.G 7.H 8.I
 * 9.J 10.K 11.L 12.M 13.N 14.O 15.P 16.Q 17.R 18.S
 * 19.T 20.U 21.V 22.W 23.X 24.Y 25.Z
 */
public class MateExcel {

    @ExcelProperty(value= "物料编码", index = 1)
    private String matNo;

    @ExcelProperty(value= "条形码", index = 4)
    private String barcode;

    @ExcelProperty(value= "物料名称", index = 2)
    private String matName;

    @ExcelProperty(value= "物料单位", index = 8)
    private String str1;

    @ExcelProperty(value= "物料规格", index = 3)
    private String str2;

    @ExcelProperty(value= "颜色", index = 5)
    private String str3;

    @ExcelProperty(value= "客户", index = 6)
    private String str4;

//    @ExcelProperty(value= "助记码", index = 7)
    private String str5;

//    @ExcelProperty(value= "默认供应商", index = 10)
    private String str6;

//    @ExcelProperty(value= "默认仓库", index = 13)
    private String str7;

//    @ExcelProperty(value= "品牌", index = 9)
    private String str8;

//    @ExcelProperty(value= "采购员", index = 11)
    private String str9;

//    @ExcelProperty(value= "产地", index = 12)
    private String str10;

//    @ExcelProperty(value= "序列号管理")
    private String str11;

//    @ExcelProperty(value= "批次管理")
    private String str12;

//    @ExcelProperty(value= "保质期单位")
    private String str13;

//    @ExcelProperty(value= "保质期管理")
    private String str14;

//    @ExcelProperty(value= "保质期")
    private String str15;

//    @ExcelProperty(value= "可销售", index = 20)
    private String str16;

//    @ExcelProperty(value= "可采购", index = 21)
    private String str17;

//    @ExcelProperty(value= "可为子件", index = 22)
    private String str18;

//    @ExcelProperty(value= "可为组件", index = 23)
    private String str19;

//    @ExcelProperty(value= "辅助属性管理")
    private String str20;

//    @ExcelProperty(value= "成本计算方法", index = 29)
    private String str21;

//    @ExcelProperty(value= "采购单位", index = 30)
    private String str22;

//    @ExcelProperty(value= "销售单位", index = 31)
    private String str23;

    @ExcelProperty(value= "重量", index = 7)
    private Double num1;

//    @ExcelProperty(value= "最低库存数量")
    private Double num2;

//    @ExcelProperty(value= "最高库存数量")
    private Double num3;

//    @ExcelProperty(value= "安全库存")
    private Double num4;

//    @ExcelProperty(value= "税率")
    private Double num5;

    private Double num6;

    public MateExcel() {
    }

    public MateExcel(String matNo, String barcode, String matName, String str1, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16, String str17, String str18, String str19, String str20, String str21, String str22, String str23, Double num1, Double num2, Double num3, Double num4, Double num5, Double num6) {
        this.matNo = matNo;
        this.barcode = barcode;
        this.matName = matName;
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
        this.str5 = str5;
        this.str6 = str6;
        this.str7 = str7;
        this.str8 = str8;
        this.str9 = str9;
        this.str10 = str10;
        this.str11 = str11;
        this.str12 = str12;
        this.str13 = str13;
        this.str14 = str14;
        this.str15 = str15;
        this.str16 = str16;
        this.str17 = str17;
        this.str18 = str18;
        this.str19 = str19;
        this.str20 = str20;
        this.str21 = str21;
        this.str22 = str22;
        this.str23 = str23;
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.num4 = num4;
        this.num5 = num5;
        this.num6 = num6;
    }

    public String getMatNo() {
        return matNo;
    }

    public void setMatNo(String matNo) {
        this.matNo = matNo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getStr3() {
        return str3;
    }

    public void setStr3(String str3) {
        this.str3 = str3;
    }

    public String getStr4() {
        return str4;
    }

    public void setStr4(String str4) {
        this.str4 = str4;
    }

    public String getStr5() {
        return str5;
    }

    public void setStr5(String str5) {
        this.str5 = str5;
    }

    public String getStr6() {
        return str6;
    }

    public void setStr6(String str6) {
        this.str6 = str6;
    }

    public String getStr7() {
        return str7;
    }

    public void setStr7(String str7) {
        this.str7 = str7;
    }

    public String getStr8() {
        return str8;
    }

    public void setStr8(String str8) {
        this.str8 = str8;
    }

    public String getStr9() {
        return str9;
    }

    public void setStr9(String str9) {
        this.str9 = str9;
    }

    public String getStr10() {
        return str10;
    }

    public void setStr10(String str10) {
        this.str10 = str10;
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

    public Double getNum1() {
        return num1;
    }

    public void setNum1(Double num1) {
        this.num1 = num1;
    }

    public Double getNum2() {
        return num2;
    }

    public void setNum2(Double num2) {
        this.num2 = num2;
    }

    public Double getNum3() {
        return num3;
    }

    public void setNum3(Double num3) {
        this.num3 = num3;
    }

    public Double getNum4() {
        return num4;
    }

    public void setNum4(Double num4) {
        this.num4 = num4;
    }

    public Double getNum5() {
        return num5;
    }

    public void setNum5(Double num5) {
        this.num5 = num5;
    }

    public Double getNum6() {
        return num6;
    }

    public void setNum6(Double num6) {
        this.num6 = num6;
    }
}
