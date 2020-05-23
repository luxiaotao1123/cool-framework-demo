package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSON;
import com.cool.demo.asrs.entity.PieChartsVo;
import com.cool.demo.asrs.entity.ViewStockUseBean;
import com.cool.demo.asrs.mapper.ReportQueryMapper;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by vincent on 2020-05-23
 */
@RestController
@RequestMapping("/console")
public class ConsoleController {

    @Autowired
    private ReportQueryMapper reportQueryMapper;

    @PostMapping("/pie")
    public R pieStats(){
        ViewStockUseBean bean = new ViewStockUseBean();
        bean.setPageSize(65535);
        bean.setPageNumber(1);
        List<ViewStockUseBean> list= reportQueryMapper.queryViewStockUseList(bean);
        PieChartsVo pieVo = new PieChartsVo();
        for (ViewStockUseBean one: list){
            // 总库位
            pieVo.setTotalQty(pieVo.getTotalQty() + one.getTotal_qty());
            // 在库
            pieVo.setFullQty(pieVo.getFullQty() + one.getFull_qty());
            // 空闲
            pieVo.setNullQty(pieVo.getNullQty() + one.getNull_qty());
            // 禁用
            pieVo.setForbidQty(pieVo.getForbidQty() + one.getForbid_qty());
            // 使用 = 总 - 在库 - 空闲 - 禁用
            pieVo.setOccQty(pieVo.getOccQty()+(one.getTotal_qty()-one.getFull_qty()-one.getNull_qty()-one.getForbid_qty()));
        }
        pieVo.complete();
        System.out.println(JSON.toJSONString(pieVo));
        return R.ok(pieVo);
//        String s = "{\"forbidDes\":\"禁用库位0.1%\",\"forbidQty\":2,\"fullDes\":\"在库库位44.4%\",\"fullQty\":1061,\"nullDes\":\"空库位47.2%\",\"nullQty\":1128,\"occDes\":\"使用库位8.4%\",\"occQty\":201,\"totalDes\":\"\",\"totalQty\":2392}\n";
//        return R.ok(JSON.parse(s));
    }


}
