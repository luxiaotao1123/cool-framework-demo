package com.cool.demo.common;

import com.core.generators.CoolGenerator;

/**
 * Created by vincent on 2019-06-04
 */
public class CodeBuilder {

    public static void main(String[] args) throws Exception {
        CoolGenerator generator = new CoolGenerator();
        generator.url="localhost:3306/plastic";
        generator.username="root";
        generator.password="xltys1995";
        generator.table="sys_config";
        generator.packagePath="com.cool.demo.system";
        generator.build();
    }

}
