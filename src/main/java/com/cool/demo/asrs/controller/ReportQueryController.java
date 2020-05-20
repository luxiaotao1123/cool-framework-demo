package com.cool.demo.asrs.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.cool.demo.asrs.entity.ViewInOutBean;
import com.cool.demo.asrs.entity.ViewStayTimeBean;
import com.cool.demo.asrs.entity.ViewStockUseBean;
import com.cool.demo.asrs.entity.ViewWorkInBean;
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
		ViewStockUseBean bean = new ViewStockUseBean();
		bean.setPageSize(limit);
		bean.setPageNumber(curr);
		List<ViewStockUseBean> list= reportQueryMapper.queryViewStockUseList(bean);
		int count = reportQueryMapper.getViewStockUseCount(bean);
		Page<ViewStockUseBean> page = new Page<>();
		page.setRecords(list);
		page.setTotal(count);
		return R.ok(page);
	}

	// 导出
	@RequestMapping(value = "/viewStockUseExport.action")
	@ManagerAuth
	public R viewStockUseExport(@RequestBody JSONObject param){
		List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
		List<ViewStockUseBean> list = reportQueryMapper.getViewStockUseAll(new ViewStockUseBean());
		return R.ok(exportSupport(list, fields));
	}

	//------------------库存滞留统计--------------------------------------
	@ResponseBody
	@RequestMapping("/viewStayTimeList.action")
	public Map<String,Object> queryViewStayTimeListByPages(@RequestParam(defaultValue = "1")Integer curr,
														   @RequestParam(defaultValue = "10")Integer limit,
														   @RequestParam Map<String, Object> param){
		ViewStayTimeBean bean = new ViewStayTimeBean();
		bean.setPageSize(limit);
		bean.setPageNumber(curr);
		List<ViewStayTimeBean> list = reportQueryMapper.queryViewStayTimeList(bean);
		int count = reportQueryMapper.getViewStayTimeCount(bean);
		Page<ViewStayTimeBean> page = new Page<>();
		page.setRecords(list);
		page.setTotal(count);
		return R.ok(page);
	}

	// 导出
	@RequestMapping(value = "/viewStayTimeExport.action")
	@ManagerAuth
	public R viewStayTimeExport(@RequestBody JSONObject param){
		List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
		List<ViewStayTimeBean> list = reportQueryMapper.getViewStayTimeAll(new ViewStayTimeBean());
		return R.ok(exportSupport(list, fields));
	}

	//------------------站点日入出库次数统计--------------------------------------
	@RequestMapping("/viewInOutList.action")
	public Map<String,Object> viewInOutList(@RequestParam(defaultValue = "1")Integer curr,
											 @RequestParam(defaultValue = "10")Integer limit,
											 @RequestParam Map<String, Object> param){
		ViewInOutBean bean = new ViewInOutBean();
		bean.setPageSize(limit);
		bean.setPageNumber(curr);
		List<ViewInOutBean> list = reportQueryMapper.queryViewInOutList(bean);
		int count = reportQueryMapper.getViewInOutCount(bean);
		Page<ViewInOutBean> page = new Page<>();
		page.setRecords(list);
		page.setTotal(count);
		return R.ok(page);
	}

	//excel导出
	@RequestMapping("/viewInOutExport.action")
	@ManagerAuth
	public R viewInOutExport(@RequestBody JSONObject param){
		List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
		List<ViewInOutBean> list = reportQueryMapper.getViewInOutAll(new ViewInOutBean());
		return R.ok(exportSupport(list, fields));
	}

	//------------------日入库明细统计--------------------------------------
	@RequestMapping("/viewWorkInList.action")
	public Map<String,Object> viewWorkInList(@RequestParam(defaultValue = "1")Integer curr,
											 @RequestParam(defaultValue = "10")Integer limit,
											 @RequestParam Map<String, Object> param){
		ViewWorkInBean bean = new ViewWorkInBean();
		bean.setPageSize(limit);
		bean.setPageNumber(curr);
		List<ViewWorkInBean> list = reportQueryMapper.queryViewWorkInList(bean);
		int count = reportQueryMapper.getViewWorkInCount(bean);
		Page<ViewWorkInBean> page = new Page<>();
		page.setRecords(list);
		page.setTotal(count);
		return R.ok(page);
	}

	//excel导出
	@RequestMapping("/viewWorkInExport.action")
	@ManagerAuth
	public R viewWorkInExport(@RequestBody JSONObject param){
		List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
		List<ViewWorkInBean> list = reportQueryMapper.getViewWorkInAll(new ViewWorkInBean());
		return R.ok(exportSupport(list, fields));
	}

	//------------------日出库明细统计--------------------------------------
	@RequestMapping("/viewWorkOutList.action")
	public R viewWorkOutList(@RequestParam(defaultValue = "1")Integer curr,
						  @RequestParam(defaultValue = "10")Integer limit,
						  @RequestParam Map<String, Object> param){
		ViewWorkInBean bean = new ViewWorkInBean();
		bean.setPageSize(limit);
		bean.setPageNumber(curr);
		List<ViewWorkInBean> list = reportQueryMapper.queryViewWorkOutList(bean);
		int count = reportQueryMapper.getViewWorkOutCount(bean);
		Page<ViewWorkInBean> page = new Page<>();
		page.setRecords(list);
		page.setTotal(count);
		return R.ok(page);
	}

	//excel导出
	@RequestMapping("/viewWorkOutExport.action")
	@ManagerAuth
	public R viewWorkOutExport(@RequestBody JSONObject param){
		List<String> fields = JSONObject.parseArray(param.getJSONArray("fields").toJSONString(), String.class);
		List<ViewWorkInBean> list = reportQueryMapper.getViewWorkOutAll(new ViewWorkInBean());
		return R.ok(exportSupport(list, fields));
	}

}
