package com.cool.demo.common.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * ExcelProperty中的value和index只要有一个对上就能解析
 * Created by vincent on 2019-11-25
 */
@Data
public class EnInitData {

    @ExcelProperty(value = "企业名称", index = 0)
    private String enName;

    @ExcelProperty(value = "统一社会信用代码", index = 1)
    private String code;

    @ExcelProperty(value = "主管税务机关", index = 7)
    private String office;

    @ExcelProperty(value = "负责人电话", index = 10)
    private String mobile;

}
