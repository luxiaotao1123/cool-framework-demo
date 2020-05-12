package com.cool.demo.common.utils.excel;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * ExcelProperty中的value和index只要有一个对上就能解析
 * Created by vincent on 2019-11-25
 */
public class EnInitData {

    @ExcelProperty(value = "企业名称", index = 0)
    private String enName;

    @ExcelProperty(value = "统一社会信用代码", index = 1)
    private String code;

    @ExcelProperty(value = "主管税务机关", index = 7)
    private String office;

    @ExcelProperty(value = "负责人电话", index = 10)
    private String mobile;

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
