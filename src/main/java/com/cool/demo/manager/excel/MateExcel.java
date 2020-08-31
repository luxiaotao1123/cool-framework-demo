package com.cool.demo.manager.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.cool.demo.manager.entity.Mate;
import lombok.Data;

/**
 * 0.A 1.B 2.C 3.D 4.E 5.F 6.G 7.H 8.I
 * 9.J 10.K 11.L 12.M 13.N 14.O 15.P 16.Q 17.R 18.S
 * 19.T 20.U 21.V 22.W 23.X 24.Y 25.Z
 */
@Data
public class MateExcel {

    @ExcelProperty(value= "序号", index = 0)
    private Long id;

    @ExcelProperty(value= "商品编码", index = 1)
    private String code;

    @ExcelProperty(value= "商品名称", index = 3)
    private String name;

    @ExcelProperty(value= "规格型号", index = 5)
    private String str;

    @ExcelProperty(value= "数量", index = 7)
    private Double amount;

    @ExcelProperty(value= "单位", index = 8)
    private String unit;

    @ExcelProperty(value= "商品行备注", index = 9)
    private String content;

    public static MateExcel cover(Mate mate) {
        MateExcel mateExcel = new MateExcel();
        mateExcel.setId(mate.getId());
        mateExcel.setCode(mate.getCode());
        mateExcel.setName(mate.getName());
        mateExcel.setStr(" ");
        mateExcel.setAmount(mate.getAmount());
        mateExcel.setUnit(mate.getUnit());
        mateExcel.setContent(mate.getContent());
        return mateExcel;
    }

}
