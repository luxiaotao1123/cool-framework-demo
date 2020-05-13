package com.cool.demo.common;

import com.core.generators.CoolGenerator;

/**
 * Created by vincent on 2019-06-04
 */
public class CodeBuilder {

    public static void main(String[] args) throws Exception {
        CoolGenerator generator = new CoolGenerator();
        generator.url="192.168.3.211:1433;databasename=cool";
        generator.username="sa";
        generator.password="sa@123";
        generator.table="asr_loc_detl";
        generator.packagePath="com.cool.demo.manager";
        generator.build();
    }

}
