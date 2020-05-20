package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.ViewStockUseBean;
import com.cool.demo.asrs.mapper.ReportQueryMapper;
import com.cool.demo.common.web.BaseController;
import com.core.annotations.ManagerAuth;
import com.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 日志统计控制器层
 * @author admin
 * @date 2018年11月23日
 */
@RestController
@RequestMapping("/report")
public class ReportQueryController extends BaseController {

	@Autowired
	ReportQueryMapper reportQueryMapper;

	//------------------库位使用统计--------------------------------------
	@RequestMapping("/viewStockUseList.action")
	public R queryViewStockUseListByPages(@RequestParam(defaultValue = "1")Integer curr,
										  @RequestParam(defaultValue = "10")Integer limit,
										  @RequestParam Map<String, Object> param){
		ViewStockUseBean viewStockUse = new ViewStockUseBean();
		viewStockUse.setPageSize(limit);
		viewStockUse.setPageNumber(curr);
		List<ViewStockUseBean> list= reportQueryMapper.queryViewStockUseList(viewStockUse);
		int count = reportQueryMapper.getViewStockUseCount(viewStockUse);
		Page<ViewStockUseBean> page = new Page<>();
		page.setRecords(list);
		page.setTotal(count);
		return R.ok(page);
	}

	// 导出
	@RequestMapping(value = "/viewStockUseExport.action")
	@ManagerAuth
	public R export(@RequestBody JSONObject param){
		List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
		List<ViewStockUseBean> list = reportQueryMapper.getViewStockUseAll(new ViewStockUseBean());
		return R.ok(exportSupport(list, fields));
	}
}
