package com.cool.demo.common;

import com.core.generators.BuildCodeTemplates;

import java.io.IOException;

/**
 * Created by vincent on 2019-06-04
 */
public class CodeBuilder {

    public static void main(String[] args) throws IOException {
        new BuildCodeTemplates("com/cool", "demo", "user").buildCodeFile();
    }
}
