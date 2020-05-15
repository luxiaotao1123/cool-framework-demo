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
//        generator.table="sys_role";
        // sqlserver
        generator.url="192.168.3.215:1433;databasename=cool";
        generator.username="sa";
        generator.password="sa@123";
        generator.table="asr_bas_loc_type";
        generator.packagePath="com.cool.demo.asrs";
        generator.build();
    }

}
