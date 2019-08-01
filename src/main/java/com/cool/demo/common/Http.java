package com.cool.demo.common;


import com.alibaba.fastjson.JSON;
import com.core.common.BaseRes;
import com.core.common.R;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by vincent on 2019-08-01
 */
public class Http {

    public static void response(HttpServletResponse response, BaseRes baseRes){
        try (PrintWriter out = response.getWriter()) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            out.print(JSON.toJSONString(new R(baseRes)));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
