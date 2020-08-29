package com.cool.demo.common;

import com.core.generators.CoolGenerator;

/**
 * Created by vincent on 2019-06-04
 */
public class CodeBuilder {

    public static void main(String[] args) throws Exception {
        CoolGenerator generator = new CoolGenerator();
        // mysql
        generator.url="localhost:3306/db_xty";
        generator.username="root";
        generator.password="root";
        generator.table="sys_host";
        // sqlserver
//        generator.url="127.0.0.1:1433;databasename=db_xty";
//        generator.username="sa";
//        generator.password="sa@123";
//        generator.table="man_wp_detl";
//        generator.packagePath="com.cool.demo.manager";
        generator.js = false;
        generator.html = false;
        generator.htmlDetail = false;
        generator.sql = false;
        generator.build();
    }

}
