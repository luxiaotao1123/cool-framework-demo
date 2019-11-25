package com.cool.demo.common.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincent on 2019-11-24
 */
public class ExcelUtils {

    public static void main(String[] args) {
//        new ExcelUtils().simpleRead();
        new ExcelUtils().simpleWrite();
    }

    public void simpleRead(){
        ExcelReaderBuilder read = EasyExcel.read("/Users/vincent/Desktop/3869.xls", EnInitData.class, new EnInitDataListener());
        // 读取第一个sheet(表),文件流会自动关闭
        read.sheet().doRead();
    }

    public void simpleWrite(){
        EasyExcel.write("/Users/vincent/Desktop/writeDemo.xls", EnInitData.class).sheet("lxt").doWrite(data());
    }

    public static List<EnInitData> data(){
        List<EnInitData> list = new ArrayList<>();
        for (int i = 0; i<1000; i++) {
            EnInitData data = new EnInitData();
            data.setEnName("测试");
            data.setCode("test");
            data.setOffice("国家税务总局杭州市余杭区税务局余杭税务所");
            data.setMobile("15988786205");
            list.add(data);
        }
        return list;
    }
}
