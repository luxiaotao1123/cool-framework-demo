package com.cool.demo.common;

import com.core.generators.CoolGenerator;

/**
 * Created by vincent on 2019-06-04
 */
public class CodeBuilder {

    public static void main(String[] args) throws Exception {
        CoolGenerator generator = new CoolGenerator();
        generator.url="47.111.25.152:3306/plastic";
        generator.username="root";
        generator.password="ykBmgA5TPoc";
        generator.table="man_bill_detail";
        generator.packagePath="com.cool.demo.manager";
        generator.build();
    }

}
