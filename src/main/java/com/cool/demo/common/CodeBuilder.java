package com.cool.demo.common;

import com.core.generators.CoolGenerator;

/**
 * Created by vincent on 2019-06-04
 */
public class CodeBuilder {

    public static void main(String[] args) throws Exception {
        CoolGenerator generator = new CoolGenerator();
        // mysql
//        generator.url="localhost:3306/cool";
//        generator.username="root";
//        generator.password="xltys1995";
//        generator.table="sys_host";
        // sqlserver
        generator.url="192.168.1.102:1433;databasename=cool";
        generator.username="sa";
        generator.password="sa@123";
        generator.table="asr_sta_desc";
        generator.packagePath="com.cool.demo.asrs";
        generator.build();
    }

}
