package com.cool.demo.manager.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.common.web.BaseController;
import com.cool.demo.manager.entity.Mate;
import com.cool.demo.manager.excel.MateExcel;
import com.cool.demo.manager.excel.MateExcelListener;
import com.cool.demo.manager.excel.MonthSheetWriteHandler;
import com.cool.demo.manager.service.MateService;
import com.core.annotations.ManagerAuth;
import com.core.common.BaseRes;
import com.core.common.Cools;
import com.core.common.DateUtils;
import com.core.common.R;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MateController extends BaseController {

    @Autowired
    private MateService mateService;

    @RequestMapping(value = "/mate/{id}/auth")
    @ManagerAuth
    public R get(@PathVariable("id") String id) {
        return R.ok(mateService.selectById(String.valueOf(id)));
    }

    @RequestMapping(value = "/mate/list/auth")
    @ManagerAuth
    public R list(@RequestParam(defaultValue = "1")Integer curr,
                  @RequestParam(defaultValue = "10")Integer limit,
                  @RequestParam(required = false)String orderByField,
                  @RequestParam(required = false)String orderByType,
                  @RequestParam(required = false)String condition,
                  @RequestParam Map<String, Object> param){
        EntityWrapper<Mate> wrapper = new EntityWrapper<>();
        excludeTrash(param);
        convert(param, wrapper);
        allLike(Mate.class, param.keySet(), wrapper, condition);
        if (!Cools.isEmpty(orderByField)){wrapper.orderBy(humpToLine(orderByField), "asc".equals(orderByType));}
        return R.ok(mateService.selectPage(new Page<>(curr, limit), wrapper));
    }

    @RequestMapping(value = "/mate/export/auth")
    public void export(@RequestParam(required = false)String orderByField,
                    @RequestParam(required = false)String orderByType,
                    @RequestParam(required = false)String condition,
                    @RequestParam Map<String, Object> param,
                    HttpServletResponse response)throws Exception{
        EntityWrapper<Mate> wrapper = new EntityWrapper<>();
        excludeTrash(param);
        convert(param, wrapper);
        allLike(Mate.class, param.keySet(), wrapper, condition);
        if (!Cools.isEmpty(orderByField)){wrapper.orderBy(humpToLine(orderByField), "asc".equals(orderByType));}
        List<Mate> mates = mateService.selectList(wrapper);
        List<MateExcel> excels = new ArrayList<>();
        for (Mate mate : mates) {
            excels.add(MateExcel.cover(mate));
        }

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");


        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)11);
        headWriteFont.setBold(false);
        headWriteCellStyle.setWriteFont(headWriteFont);


        //内容样式策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //垂直居中,水平居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 11);
        contentWriteCellStyle.setWriteFont(contentWriteFont);


        EasyExcel.write(response.getOutputStream(), MateExcel.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                //设置拦截器或自定义样式
                .registerWriteHandler(new MonthSheetWriteHandler())
                .registerWriteHandler(new HorizontalCellStyleStrategy(headWriteCellStyle,contentWriteCellStyle))
                .sheet("sheet1")
                .useDefaultStyle(true).relativeHeadRowIndex(3)
                .doWrite(excels);
    }

    private void convert(Map<String, Object> map, EntityWrapper wrapper){
        for (Map.Entry<String, Object> entry : map.entrySet()){
            String val = String.valueOf(entry.getValue());
            if (val.contains(RANGE_TIME_LINK)){
                String[] dates = val.split(RANGE_TIME_LINK);
                wrapper.ge(entry.getKey(), DateUtils.convert(dates[0]));
                wrapper.le(entry.getKey(), DateUtils.convert(dates[1]));
            } else {
                wrapper.like(entry.getKey(), val);
            }
        }
    }

    @RequestMapping(value = "/mate/cover/auth")
    @ManagerAuth
    public R cover(@RequestBody List<Long> ids){
        if (Cools.isEmpty(ids)){
            return R.error();
        }
        return R.ok();
    }

    @RequestMapping(value = "/mate/add/auth")
    @ManagerAuth
    public R add(Mate mate) {
        mateService.insert(mate);
        return R.ok();
    }

	@RequestMapping(value = "/mate/update/auth")
	@ManagerAuth
    public R update(Mate mate){
        if (Cools.isEmpty(mate) || null==mate.getId()){
            return R.error();
        }
        mateService.updateById(mate);
        return R.ok();
    }

    @RequestMapping(value = "/mate/delete/auth")
    @ManagerAuth
    public R delete(@RequestParam String param){
        List<Mate> list = JSONArray.parseArray(param, Mate.class);
        if (Cools.isEmpty(list)){
            return R.error();
        }
        for (Mate entity : list){
            mateService.delete(new EntityWrapper<>(entity));
        }
        return R.ok();
    }

    @RequestMapping(value = "/mateQuery/auth")
    @ManagerAuth
    public R query(String condition) {
        EntityWrapper<Mate> wrapper = new EntityWrapper<>();
        wrapper.like("code", condition);
        Page<Mate> page = mateService.selectPage(new Page<>(0, 10), wrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Mate mate : page.getRecords()){
            Map<String, Object> map = new HashMap<>();
            map.put("id", mate.getId());
            map.put("value", mate.getCode());
            result.add(map);
        }
        return R.ok(result);
    }

    @RequestMapping(value = "/mate/check/column/auth")
    @ManagerAuth
    public R query(@RequestBody JSONObject param) {
        Wrapper<Mate> wrapper = new EntityWrapper<Mate>().eq(humpToLine(String.valueOf(param.get("key"))), param.get("val"));
        if (null != mateService.selectOne(wrapper)){
            return R.parse(BaseRes.REPEAT).add(getComment(Mate.class, String.valueOf(param.get("key"))));
        }
        return R.ok();
    }

    // 导入
    @RequestMapping(value = "/mate/import/auth")
    @ManagerAuth(memo = "商品数据导入")
    @Transactional
    public R matCodeImport(MultipartFile file) throws IOException, InterruptedException {
        MateExcelListener listener = new MateExcelListener(getUserId());
        EasyExcel.read(file.getInputStream(), Mate.class, listener).sheet().doRead();
        return R.ok("成功导入"+listener.getTotal()+"条物料信息");
    }

}
