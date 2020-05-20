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
	private ReportQueryMapper reportQueryMapper;

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

	//-----------------库存MAP图--------------------------------------
	@RequestMapping("/viewLocMapList.action")
	public Object queryViewLocMapListByPages(@RequestParam(defaultValue = "1")Integer row){
//		List<Map<String, Object>> res = new ArrayList<>();
//		// 获取排级数据
//		List<String> levs = reportQueryMapper.getViewLocLevCount(row);
//		for (String lev : levs){
//			// 获取层级数据
//			List<ViewLocMapDto> bays = reportQueryMapper.getViewLocBays(row, Integer.parseInt(lev));
//
//			Map<String, Object> map = new HashMap<>();
//			map.put("lev", Integer.parseInt(lev));
//			map.put("loc", bays);
//			res.add(map);
//		}
		String s = "{\"msg\":\"操作成功\",\"code\":200,\"data\":[{\"loc\":[{\"bay1\":2,\"locType\":\"F\"},{\"bay1\":3,\"locType\":\"F\"},{\"bay1\":4,\"locType\":\"F\"},{\"bay1\":5,\"locType\":\"F\"},{\"bay1\":6,\"locType\":\"F\"},{\"bay1\":7,\"locType\":\"F\"},{\"bay1\":8,\"locType\":\"F\"},{\"bay1\":9,\"locType\":\"F\"},{\"bay1\":10,\"locType\":\"P\"},{\"bay1\":11,\"locType\":\"O\"},{\"bay1\":12,\"locType\":\"O\"},{\"bay1\":13,\"locType\":\"O\"},{\"bay1\":14,\"locType\":\"O\"},{\"bay1\":15,\"locType\":\"O\"},{\"bay1\":16,\"locType\":\"O\"},{\"bay1\":17,\"locType\":\"O\"},{\"bay1\":18,\"locType\":\"O\"},{\"bay1\":19,\"locType\":\"O\"},{\"bay1\":20,\"locType\":\"O\"},{\"bay1\":21,\"locType\":\"O\"},{\"bay1\":22,\"locType\":\"O\"},{\"bay1\":23,\"locType\":\"O\"},{\"bay1\":24,\"locType\":\"O\"},{\"bay1\":25,\"locType\":\"O\"},{\"bay1\":26,\"locType\":\"O\"},{\"bay1\":27,\"locType\":\"O\"},{\"bay1\":28,\"locType\":\"O\"},{\"bay1\":29,\"locType\":\"O\"},{\"bay1\":30,\"locType\":\"O\"},{\"bay1\":31,\"locType\":\"O\"},{\"bay1\":32,\"locType\":\"O\"},{\"bay1\":33,\"locType\":\"O\"},{\"bay1\":34,\"locType\":\"O\"},{\"bay1\":35,\"locType\":\"O\"},{\"bay1\":36,\"locType\":\"O\"},{\"bay1\":37,\"locType\":\"O\"},{\"bay1\":38,\"locType\":\"O\"},{\"bay1\":39,\"locType\":\"O\"},{\"bay1\":40,\"locType\":\"O\"},{\"bay1\":41,\"locType\":\"O\"},{\"bay1\":42,\"locType\":\"O\"},{\"bay1\":43,\"locType\":\"O\"},{\"bay1\":44,\"locType\":\"O\"},{\"bay1\":45,\"locType\":\"O\"},{\"bay1\":46,\"locType\":\"O\"},{\"bay1\":47,\"locType\":\"O\"}],\"lev\":13},{\"loc\":[{\"bay1\":2,\"locType\":\"O\"},{\"bay1\":3,\"locType\":\"O\"},{\"bay1\":4,\"locType\":\"O\"},{\"bay1\":5,\"locType\":\"O\"},{\"bay1\":6,\"locType\":\"O\"},{\"bay1\":7,\"locType\":\"O\"},{\"bay1\":8,\"locType\":\"O\"},{\"bay1\":9,\"locType\":\"O\"},{\"bay1\":10,\"locType\":\"O\"},{\"bay1\":11,\"locType\":\"O\"},{\"bay1\":12,\"locType\":\"O\"},{\"bay1\":13,\"locType\":\"O\"},{\"bay1\":14,\"locType\":\"O\"},{\"bay1\":15,\"locType\":\"O\"},{\"bay1\":16,\"locType\":\"O\"},{\"bay1\":17,\"locType\":\"O\"},{\"bay1\":18,\"locType\":\"O\"},{\"bay1\":19,\"locType\":\"O\"},{\"bay1\":20,\"locType\":\"O\"},{\"bay1\":21,\"locType\":\"O\"},{\"bay1\":22,\"locType\":\"O\"},{\"bay1\":23,\"locType\":\"O\"},{\"bay1\":24,\"locType\":\"O\"},{\"bay1\":25,\"locType\":\"O\"},{\"bay1\":26,\"locType\":\"O\"},{\"bay1\":27,\"locType\":\"O\"},{\"bay1\":28,\"locType\":\"O\"},{\"bay1\":29,\"locType\":\"O\"},{\"bay1\":30,\"locType\":\"O\"},{\"bay1\":31,\"locType\":\"O\"},{\"bay1\":32,\"locType\":\"O\"},{\"bay1\":33,\"locType\":\"O\"},{\"bay1\":34,\"locType\":\"O\"},{\"bay1\":35,\"locType\":\"O\"},{\"bay1\":36,\"locType\":\"O\"},{\"bay1\":37,\"locType\":\"O\"},{\"bay1\":38,\"locType\":\"O\"},{\"bay1\":39,\"locType\":\"O\"},{\"bay1\":40,\"locType\":\"O\"},{\"bay1\":41,\"locType\":\"O\"},{\"bay1\":42,\"locType\":\"O\"},{\"bay1\":43,\"locType\":\"O\"},{\"bay1\":44,\"locType\":\"O\"},{\"bay1\":45,\"locType\":\"O\"},{\"bay1\":46,\"locType\":\"O\"},{\"bay1\":47,\"locType\":\"O\"}],\"lev\":12},{\"loc\":[{\"bay1\":2,\"locType\":\"O\"},{\"bay1\":3,\"locType\":\"O\"},{\"bay1\":4,\"locType\":\"O\"},{\"bay1\":5,\"locType\":\"O\"},{\"bay1\":6,\"locType\":\"O\"},{\"bay1\":7,\"locType\":\"O\"},{\"bay1\":8,\"locType\":\"O\"},{\"bay1\":9,\"locType\":\"O\"},{\"bay1\":10,\"locType\":\"O\"},{\"bay1\":11,\"locType\":\"O\"},{\"bay1\":12,\"locType\":\"O\"},{\"bay1\":13,\"locType\":\"O\"},{\"bay1\":14,\"locType\":\"O\"},{\"bay1\":15,\"locType\":\"O\"},{\"bay1\":16,\"locType\":\"O\"},{\"bay1\":17,\"locType\":\"O\"},{\"bay1\":18,\"locType\":\"O\"},{\"bay1\":19,\"locType\":\"O\"},{\"bay1\":20,\"locType\":\"O\"},{\"bay1\":21,\"locType\":\"O\"},{\"bay1\":22,\"locType\":\"O\"},{\"bay1\":23,\"locType\":\"O\"},{\"bay1\":24,\"locType\":\"O\"},{\"bay1\":25,\"locType\":\"O\"},{\"bay1\":26,\"locType\":\"O\"},{\"bay1\":27,\"locType\":\"O\"},{\"bay1\":28,\"locType\":\"O\"},{\"bay1\":29,\"locType\":\"O\"},{\"bay1\":30,\"locType\":\"O\"},{\"bay1\":31,\"locType\":\"O\"},{\"bay1\":32,\"locType\":\"O\"},{\"bay1\":33,\"locType\":\"O\"},{\"bay1\":34,\"locType\":\"O\"},{\"bay1\":35,\"locType\":\"O\"},{\"bay1\":36,\"locType\":\"O\"},{\"bay1\":37,\"locType\":\"O\"},{\"bay1\":38,\"locType\":\"O\"},{\"bay1\":39,\"locType\":\"O\"},{\"bay1\":40,\"locType\":\"O\"},{\"bay1\":41,\"locType\":\"O\"},{\"bay1\":42,\"locType\":\"O\"},{\"bay1\":43,\"locType\":\"O\"},{\"bay1\":44,\"locType\":\"O\"},{\"bay1\":45,\"locType\":\"O\"},{\"bay1\":46,\"locType\":\"O\"},{\"bay1\":47,\"locType\":\"O\"}],\"lev\":11},{\"loc\":[{\"bay1\":2,\"locType\":\"O\"},{\"bay1\":3,\"locType\":\"O\"},{\"bay1\":4,\"locType\":\"O\"},{\"bay1\":5,\"locType\":\"O\"},{\"bay1\":6,\"locType\":\"O\"},{\"bay1\":7,\"locType\":\"O\"},{\"bay1\":8,\"locType\":\"O\"},{\"bay1\":9,\"locType\":\"O\"},{\"bay1\":10,\"locType\":\"O\"},{\"bay1\":11,\"locType\":\"O\"},{\"bay1\":12,\"locType\":\"O\"},{\"bay1\":13,\"locType\":\"O\"},{\"bay1\":14,\"locType\":\"O\"},{\"bay1\":15,\"locType\":\"O\"},{\"bay1\":16,\"locType\":\"O\"},{\"bay1\":17,\"locType\":\"O\"},{\"bay1\":18,\"locType\":\"O\"},{\"bay1\":19,\"locType\":\"O\"},{\"bay1\":20,\"locType\":\"O\"},{\"bay1\":21,\"locType\":\"O\"},{\"bay1\":22,\"locType\":\"O\"},{\"bay1\":23,\"locType\":\"O\"},{\"bay1\":24,\"locType\":\"O\"},{\"bay1\":25,\"locType\":\"O\"},{\"bay1\":26,\"locType\":\"O\"},{\"bay1\":27,\"locType\":\"O\"},{\"bay1\":28,\"locType\":\"O\"},{\"bay1\":29,\"locType\":\"O\"},{\"bay1\":30,\"locType\":\"O\"},{\"bay1\":31,\"locType\":\"O\"},{\"bay1\":32,\"locType\":\"O\"},{\"bay1\":33,\"locType\":\"O\"},{\"bay1\":34,\"locType\":\"O\"},{\"bay1\":35,\"locType\":\"O\"},{\"bay1\":36,\"locType\":\"O\"},{\"bay1\":37,\"locType\":\"O\"},{\"bay1\":38,\"locType\":\"O\"},{\"bay1\":39,\"locType\":\"O\"},{\"bay1\":40,\"locType\":\"O\"},{\"bay1\":41,\"locType\":\"O\"},{\"bay1\":42,\"locType\":\"O\"},{\"bay1\":43,\"locType\":\"O\"},{\"bay1\":44,\"locType\":\"O\"},{\"bay1\":45,\"locType\":\"O\"},{\"bay1\":46,\"locType\":\"O\"},{\"bay1\":47,\"locType\":\"O\"}],\"lev\":10},{\"loc\":[{\"bay1\":2,\"locType\":\"O\"},{\"bay1\":3,\"locType\":\"O\"},{\"bay1\":4,\"locType\":\"O\"},{\"bay1\":5,\"locType\":\"O\"},{\"bay1\":6,\"locType\":\"O\"},{\"bay1\":7,\"locType\":\"O\"},{\"bay1\":8,\"locType\":\"O\"},{\"bay1\":9,\"locType\":\"O\"},{\"bay1\":10,\"locType\":\"O\"},{\"bay1\":11,\"locType\":\"O\"},{\"bay1\":12,\"locType\":\"O\"},{\"bay1\":13,\"locType\":\"O\"},{\"bay1\":14,\"locType\":\"O\"},{\"bay1\":15,\"locType\":\"O\"},{\"bay1\":16,\"locType\":\"O\"},{\"bay1\":17,\"locType\":\"O\"},{\"bay1\":18,\"locType\":\"O\"},{\"bay1\":19,\"locType\":\"O\"},{\"bay1\":20,\"locType\":\"O\"},{\"bay1\":21,\"locType\":\"O\"},{\"bay1\":22,\"locType\":\"O\"},{\"bay1\":23,\"locType\":\"O\"},{\"bay1\":24,\"locType\":\"O\"},{\"bay1\":25,\"locType\":\"O\"},{\"bay1\":26,\"locType\":\"O\"},{\"bay1\":27,\"locType\":\"O\"},{\"bay1\":28,\"locType\":\"O\"},{\"bay1\":29,\"locType\":\"O\"},{\"bay1\":30,\"locType\":\"O\"},{\"bay1\":31,\"locType\":\"O\"},{\"bay1\":32,\"locType\":\"O\"},{\"bay1\":33,\"locType\":\"O\"},{\"bay1\":34,\"locType\":\"O\"},{\"bay1\":35,\"locType\":\"O\"},{\"bay1\":36,\"locType\":\"O\"},{\"bay1\":37,\"locType\":\"O\"},{\"bay1\":38,\"locType\":\"O\"},{\"bay1\":39,\"locType\":\"O\"},{\"bay1\":40,\"locType\":\"O\"},{\"bay1\":41,\"locType\":\"O\"},{\"bay1\":42,\"locType\":\"O\"},{\"bay1\":43,\"locType\":\"O\"},{\"bay1\":44,\"locType\":\"O\"},{\"bay1\":45,\"locType\":\"O\"},{\"bay1\":46,\"locType\":\"O\"},{\"bay1\":47,\"locType\":\"O\"}],\"lev\":9},{\"loc\":[{\"bay1\":2,\"locType\":\"O\"},{\"bay1\":3,\"locType\":\"O\"},{\"bay1\":4,\"locType\":\"O\"},{\"bay1\":5,\"locType\":\"O\"},{\"bay1\":6,\"locType\":\"O\"},{\"bay1\":7,\"locType\":\"O\"},{\"bay1\":8,\"locType\":\"O\"},{\"bay1\":9,\"locType\":\"O\"},{\"bay1\":10,\"locType\":\"O\"},{\"bay1\":11,\"locType\":\"O\"},{\"bay1\":12,\"locType\":\"O\"},{\"bay1\":13,\"locType\":\"O\"},{\"bay1\":14,\"locType\":\"O\"},{\"bay1\":15,\"locType\":\"O\"},{\"bay1\":16,\"locType\":\"O\"},{\"bay1\":17,\"locType\":\"O\"},{\"bay1\":18,\"locType\":\"O\"},{\"bay1\":19,\"locType\":\"O\"},{\"bay1\":20,\"locType\":\"O\"},{\"bay1\":21,\"locType\":\"O\"},{\"bay1\":22,\"locType\":\"O\"},{\"bay1\":23,\"locType\":\"O\"},{\"bay1\":24,\"locType\":\"O\"},{\"bay1\":25,\"locType\":\"O\"},{\"bay1\":26,\"locType\":\"O\"},{\"bay1\":27,\"locType\":\"O\"},{\"bay1\":28,\"locType\":\"O\"},{\"bay1\":29,\"locType\":\"O\"},{\"bay1\":30,\"locType\":\"O\"},{\"bay1\":31,\"locType\":\"O\"},{\"bay1\":32,\"locType\":\"O\"},{\"bay1\":33,\"locType\":\"O\"},{\"bay1\":34,\"locType\":\"O\"},{\"bay1\":35,\"locType\":\"O\"},{\"bay1\":36,\"locType\":\"O\"},{\"bay1\":37,\"locType\":\"O\"},{\"bay1\":38,\"locType\":\"O\"},{\"bay1\":39,\"locType\":\"O\"},{\"bay1\":40,\"locType\":\"O\"},{\"bay1\":41,\"locType\":\"O\"},{\"bay1\":42,\"locType\":\"O\"},{\"bay1\":43,\"locType\":\"O\"},{\"bay1\":44,\"locType\":\"O\"},{\"bay1\":45,\"locType\":\"O\"},{\"bay1\":46,\"locType\":\"O\"},{\"bay1\":47,\"locType\":\"O\"}],\"lev\":8},{\"loc\":[{\"bay1\":2,\"locType\":\"F\"},{\"bay1\":3,\"locType\":\"F\"},{\"bay1\":4,\"locType\":\"F\"},{\"bay1\":5,\"locType\":\"F\"},{\"bay1\":6,\"locType\":\"F\"},{\"bay1\":7,\"locType\":\"F\"},{\"bay1\":8,\"locType\":\"F\"},{\"bay1\":9,\"locType\":\"F\"},{\"bay1\":10,\"locType\":\"F\"},{\"bay1\":11,\"locType\":\"F\"},{\"bay1\":12,\"locType\":\"F\"},{\"bay1\":13,\"locType\":\"F\"},{\"bay1\":14,\"locType\":\"F\"},{\"bay1\":15,\"locType\":\"F\"},{\"bay1\":16,\"locType\":\"F\"},{\"bay1\":17,\"locType\":\"F\"},{\"bay1\":18,\"locType\":\"F\"},{\"bay1\":19,\"locType\":\"F\"},{\"bay1\":20,\"locType\":\"F\"},{\"bay1\":21,\"locType\":\"F\"},{\"bay1\":22,\"locType\":\"F\"},{\"bay1\":23,\"locType\":\"F\"},{\"bay1\":24,\"locType\":\"F\"},{\"bay1\":25,\"locType\":\"F\"},{\"bay1\":26,\"locType\":\"F\"},{\"bay1\":27,\"locType\":\"F\"},{\"bay1\":28,\"locType\":\"F\"},{\"bay1\":29,\"locType\":\"F\"},{\"bay1\":30,\"locType\":\"F\"},{\"bay1\":31,\"locType\":\"P\"},{\"bay1\":32,\"locType\":\"F\"},{\"bay1\":33,\"locType\":\"F\"},{\"bay1\":34,\"locType\":\"F\"},{\"bay1\":35,\"locType\":\"P\"},{\"bay1\":36,\"locType\":\"F\"},{\"bay1\":37,\"locType\":\"F\"},{\"bay1\":38,\"locType\":\"F\"},{\"bay1\":39,\"locType\":\"F\"},{\"bay1\":40,\"locType\":\"F\"},{\"bay1\":41,\"locType\":\"F\"},{\"bay1\":42,\"locType\":\"F\"},{\"bay1\":43,\"locType\":\"O\"},{\"bay1\":44,\"locType\":\"O\"},{\"bay1\":45,\"locType\":\"O\"},{\"bay1\":46,\"locType\":\"O\"},{\"bay1\":47,\"locType\":\"O\"}],\"lev\":7},{\"loc\":[{\"bay1\":2,\"locType\":\"X\"},{\"bay1\":3,\"locType\":\"F\"},{\"bay1\":4,\"locType\":\"F\"},{\"bay1\":5,\"locType\":\"F\"},{\"bay1\":6,\"locType\":\"F\"},{\"bay1\":7,\"locType\":\"F\"},{\"bay1\":8,\"locType\":\"F\"},{\"bay1\":9,\"locType\":\"F\"},{\"bay1\":10,\"locType\":\"F\"},{\"bay1\":11,\"locType\":\"F\"},{\"bay1\":12,\"locType\":\"F\"},{\"bay1\":13,\"locType\":\"F\"},{\"bay1\":14,\"locType\":\"F\"},{\"bay1\":15,\"locType\":\"F\"},{\"bay1\":16,\"locType\":\"F\"},{\"bay1\":17,\"locType\":\"F\"},{\"bay1\":18,\"locType\":\"F\"},{\"bay1\":19,\"locType\":\"F\"},{\"bay1\":20,\"locType\":\"F\"},{\"bay1\":21,\"locType\":\"F\"},{\"bay1\":22,\"locType\":\"F\"},{\"bay1\":23,\"locType\":\"F\"},{\"bay1\":24,\"locType\":\"P\"},{\"bay1\":25,\"locType\":\"F\"},{\"bay1\":26,\"locType\":\"F\"},{\"bay1\":27,\"locType\":\"F\"},{\"bay1\":28,\"locType\":\"F\"},{\"bay1\":29,\"locType\":\"F\"},{\"bay1\":30,\"locType\":\"F\"},{\"bay1\":31,\"locType\":\"F\"},{\"bay1\":32,\"locType\":\"F\"},{\"bay1\":33,\"locType\":\"F\"},{\"bay1\":34,\"locType\":\"F\"},{\"bay1\":35,\"locType\":\"F\"},{\"bay1\":36,\"locType\":\"F\"},{\"bay1\":37,\"locType\":\"F\"},{\"bay1\":38,\"locType\":\"F\"},{\"bay1\":39,\"locType\":\"F\"},{\"bay1\":40,\"locType\":\"F\"},{\"bay1\":41,\"locType\":\"F\"},{\"bay1\":42,\"locType\":\"F\"},{\"bay1\":43,\"locType\":\"F\"},{\"bay1\":44,\"locType\":\"F\"},{\"bay1\":45,\"locType\":\"F\"},{\"bay1\":46,\"locType\":\"F\"},{\"bay1\":47,\"locType\":\"F\"}],\"lev\":6},{\"loc\":[{\"bay1\":2,\"locType\":\"D\"},{\"bay1\":3,\"locType\":\"D\"},{\"bay1\":4,\"locType\":\"D\"},{\"bay1\":5,\"locType\":\"D\"},{\"bay1\":6,\"locType\":\"D\"},{\"bay1\":7,\"locType\":\"D\"},{\"bay1\":8,\"locType\":\"D\"},{\"bay1\":9,\"locType\":\"D\"},{\"bay1\":10,\"locType\":\"D\"},{\"bay1\":11,\"locType\":\"D\"},{\"bay1\":12,\"locType\":\"D\"},{\"bay1\":13,\"locType\":\"D\"},{\"bay1\":14,\"locType\":\"D\"},{\"bay1\":15,\"locType\":\"D\"},{\"bay1\":16,\"locType\":\"D\"},{\"bay1\":17,\"locType\":\"D\"},{\"bay1\":18,\"locType\":\"D\"},{\"bay1\":19,\"locType\":\"D\"},{\"bay1\":20,\"locType\":\"D\"},{\"bay1\":21,\"locType\":\"D\"},{\"bay1\":22,\"locType\":\"D\"},{\"bay1\":23,\"locType\":\"D\"},{\"bay1\":24,\"locType\":\"D\"},{\"bay1\":25,\"locType\":\"D\"},{\"bay1\":26,\"locType\":\"D\"},{\"bay1\":27,\"locType\":\"D\"},{\"bay1\":28,\"locType\":\"D\"},{\"bay1\":29,\"locType\":\"D\"},{\"bay1\":30,\"locType\":\"D\"},{\"bay1\":31,\"locType\":\"D\"},{\"bay1\":32,\"locType\":\"D\"},{\"bay1\":33,\"locType\":\"D\"},{\"bay1\":34,\"locType\":\"D\"},{\"bay1\":35,\"locType\":\"D\"},{\"bay1\":36,\"locType\":\"D\"},{\"bay1\":37,\"locType\":\"D\"},{\"bay1\":38,\"locType\":\"D\"},{\"bay1\":39,\"locType\":\"F\"},{\"bay1\":40,\"locType\":\"F\"},{\"bay1\":41,\"locType\":\"F\"},{\"bay1\":42,\"locType\":\"F\"},{\"bay1\":43,\"locType\":\"F\"},{\"bay1\":44,\"locType\":\"F\"},{\"bay1\":45,\"locType\":\"F\"},{\"bay1\":46,\"locType\":\"F\"},{\"bay1\":47,\"locType\":\"F\"}],\"lev\":5},{\"loc\":[{\"bay1\":2,\"locType\":\"F\"},{\"bay1\":3,\"locType\":\"F\"},{\"bay1\":4,\"locType\":\"F\"},{\"bay1\":5,\"locType\":\"F\"},{\"bay1\":6,\"locType\":\"F\"},{\"bay1\":7,\"locType\":\"F\"},{\"bay1\":8,\"locType\":\"F\"},{\"bay1\":9,\"locType\":\"F\"},{\"bay1\":10,\"locType\":\"F\"},{\"bay1\":11,\"locType\":\"F\"},{\"bay1\":12,\"locType\":\"F\"},{\"bay1\":13,\"locType\":\"F\"},{\"bay1\":14,\"locType\":\"F\"},{\"bay1\":15,\"locType\":\"F\"},{\"bay1\":16,\"locType\":\"F\"},{\"bay1\":17,\"locType\":\"F\"},{\"bay1\":18,\"locType\":\"F\"},{\"bay1\":19,\"locType\":\"F\"},{\"bay1\":20,\"locType\":\"F\"},{\"bay1\":21,\"locType\":\"F\"},{\"bay1\":22,\"locType\":\"F\"},{\"bay1\":23,\"locType\":\"F\"},{\"bay1\":24,\"locType\":\"F\"},{\"bay1\":25,\"locType\":\"F\"},{\"bay1\":26,\"locType\":\"F\"},{\"bay1\":27,\"locType\":\"F\"},{\"bay1\":28,\"locType\":\"F\"},{\"bay1\":29,\"locType\":\"F\"},{\"bay1\":30,\"locType\":\"F\"},{\"bay1\":31,\"locType\":\"F\"},{\"bay1\":32,\"locType\":\"F\"},{\"bay1\":33,\"locType\":\"F\"},{\"bay1\":34,\"locType\":\"F\"},{\"bay1\":35,\"locType\":\"F\"},{\"bay1\":36,\"locType\":\"F\"},{\"bay1\":37,\"locType\":\"F\"},{\"bay1\":38,\"locType\":\"F\"},{\"bay1\":39,\"locType\":\"F\"},{\"bay1\":40,\"locType\":\"F\"},{\"bay1\":41,\"locType\":\"F\"},{\"bay1\":42,\"locType\":\"F\"},{\"bay1\":43,\"locType\":\"F\"},{\"bay1\":44,\"locType\":\"F\"},{\"bay1\":45,\"locType\":\"F\"},{\"bay1\":46,\"locType\":\"F\"},{\"bay1\":47,\"locType\":\"P\"}],\"lev\":4},{\"loc\":[{\"bay1\":2,\"locType\":\"P\"},{\"bay1\":3,\"locType\":\"F\"},{\"bay1\":4,\"locType\":\"F\"},{\"bay1\":5,\"locType\":\"F\"},{\"bay1\":6,\"locType\":\"F\"},{\"bay1\":7,\"locType\":\"F\"},{\"bay1\":8,\"locType\":\"F\"},{\"bay1\":9,\"locType\":\"F\"},{\"bay1\":10,\"locType\":\"F\"},{\"bay1\":11,\"locType\":\"P\"},{\"bay1\":12,\"locType\":\"F\"},{\"bay1\":13,\"locType\":\"F\"},{\"bay1\":14,\"locType\":\"F\"},{\"bay1\":15,\"locType\":\"F\"},{\"bay1\":16,\"locType\":\"F\"},{\"bay1\":17,\"locType\":\"P\"},{\"bay1\":18,\"locType\":\"F\"},{\"bay1\":19,\"locType\":\"F\"},{\"bay1\":20,\"locType\":\"F\"},{\"bay1\":21,\"locType\":\"F\"},{\"bay1\":22,\"locType\":\"F\"},{\"bay1\":23,\"locType\":\"F\"},{\"bay1\":24,\"locType\":\"F\"},{\"bay1\":25,\"locType\":\"F\"},{\"bay1\":26,\"locType\":\"F\"},{\"bay1\":27,\"locType\":\"F\"},{\"bay1\":28,\"locType\":\"F\"},{\"bay1\":29,\"locType\":\"F\"},{\"bay1\":30,\"locType\":\"F\"},{\"bay1\":31,\"locType\":\"F\"},{\"bay1\":32,\"locType\":\"F\"},{\"bay1\":33,\"locType\":\"F\"},{\"bay1\":34,\"locType\":\"F\"},{\"bay1\":35,\"locType\":\"F\"},{\"bay1\":36,\"locType\":\"F\"},{\"bay1\":37,\"locType\":\"P\"},{\"bay1\":38,\"locType\":\"F\"},{\"bay1\":39,\"locType\":\"F\"},{\"bay1\":40,\"locType\":\"F\"},{\"bay1\":41,\"locType\":\"F\"},{\"bay1\":42,\"locType\":\"F\"},{\"bay1\":43,\"locType\":\"F\"},{\"bay1\":44,\"locType\":\"F\"},{\"bay1\":45,\"locType\":\"F\"},{\"bay1\":46,\"locType\":\"F\"},{\"bay1\":47,\"locType\":\"F\"}],\"lev\":3},{\"loc\":[{\"bay1\":2,\"locType\":\"F\"},{\"bay1\":3,\"locType\":\"F\"},{\"bay1\":4,\"locType\":\"F\"},{\"bay1\":5,\"locType\":\"F\"},{\"bay1\":6,\"locType\":\"F\"},{\"bay1\":7,\"locType\":\"F\"},{\"bay1\":8,\"locType\":\"F\"},{\"bay1\":9,\"locType\":\"F\"},{\"bay1\":10,\"locType\":\"P\"},{\"bay1\":11,\"locType\":\"F\"},{\"bay1\":12,\"locType\":\"Q\"},{\"bay1\":13,\"locType\":\"P\"},{\"bay1\":14,\"locType\":\"F\"},{\"bay1\":15,\"locType\":\"F\"},{\"bay1\":16,\"locType\":\"F\"},{\"bay1\":17,\"locType\":\"F\"},{\"bay1\":18,\"locType\":\"F\"},{\"bay1\":19,\"locType\":\"F\"},{\"bay1\":20,\"locType\":\"F\"},{\"bay1\":21,\"locType\":\"F\"},{\"bay1\":22,\"locType\":\"F\"},{\"bay1\":23,\"locType\":\"F\"},{\"bay1\":24,\"locType\":\"F\"},{\"bay1\":25,\"locType\":\"F\"},{\"bay1\":26,\"locType\":\"F\"},{\"bay1\":27,\"locType\":\"F\"},{\"bay1\":28,\"locType\":\"F\"},{\"bay1\":29,\"locType\":\"F\"},{\"bay1\":30,\"locType\":\"F\"},{\"bay1\":31,\"locType\":\"F\"},{\"bay1\":32,\"locType\":\"F\"},{\"bay1\":33,\"locType\":\"F\"},{\"bay1\":34,\"locType\":\"F\"},{\"bay1\":35,\"locType\":\"F\"},{\"bay1\":36,\"locType\":\"F\"},{\"bay1\":37,\"locType\":\"F\"},{\"bay1\":38,\"locType\":\"F\"},{\"bay1\":39,\"locType\":\"F\"},{\"bay1\":40,\"locType\":\"F\"},{\"bay1\":41,\"locType\":\"F\"},{\"bay1\":42,\"locType\":\"F\"},{\"bay1\":43,\"locType\":\"F\"},{\"bay1\":44,\"locType\":\"F\"},{\"bay1\":45,\"locType\":\"F\"},{\"bay1\":46,\"locType\":\"F\"},{\"bay1\":47,\"locType\":\"P\"}],\"lev\":2},{\"loc\":[{\"bay1\":2,\"locType\":\"F\"},{\"bay1\":3,\"locType\":\"F\"},{\"bay1\":4,\"locType\":\"F\"},{\"bay1\":5,\"locType\":\"P\"},{\"bay1\":6,\"locType\":\"F\"},{\"bay1\":7,\"locType\":\"F\"},{\"bay1\":8,\"locType\":\"P\"},{\"bay1\":9,\"locType\":\"F\"},{\"bay1\":10,\"locType\":\"F\"},{\"bay1\":11,\"locType\":\"F\"},{\"bay1\":12,\"locType\":\"F\"},{\"bay1\":13,\"locType\":\"F\"},{\"bay1\":14,\"locType\":\"F\"},{\"bay1\":15,\"locType\":\"F\"},{\"bay1\":16,\"locType\":\"F\"},{\"bay1\":17,\"locType\":\"F\"},{\"bay1\":18,\"locType\":\"F\"},{\"bay1\":19,\"locType\":\"F\"},{\"bay1\":20,\"locType\":\"F\"},{\"bay1\":21,\"locType\":\"F\"},{\"bay1\":22,\"locType\":\"F\"},{\"bay1\":23,\"locType\":\"F\"},{\"bay1\":24,\"locType\":\"F\"},{\"bay1\":25,\"locType\":\"F\"},{\"bay1\":26,\"locType\":\"F\"},{\"bay1\":27,\"locType\":\"F\"},{\"bay1\":28,\"locType\":\"F\"},{\"bay1\":29,\"locType\":\"F\"},{\"bay1\":30,\"locType\":\"P\"},{\"bay1\":31,\"locType\":\"F\"},{\"bay1\":32,\"locType\":\"F\"},{\"bay1\":33,\"locType\":\"F\"},{\"bay1\":34,\"locType\":\"F\"},{\"bay1\":35,\"locType\":\"F\"},{\"bay1\":36,\"locType\":\"F\"},{\"bay1\":37,\"locType\":\"F\"},{\"bay1\":38,\"locType\":\"F\"},{\"bay1\":39,\"locType\":\"F\"},{\"bay1\":40,\"locType\":\"F\"},{\"bay1\":41,\"locType\":\"F\"},{\"bay1\":42,\"locType\":\"F\"},{\"bay1\":43,\"locType\":\"F\"},{\"bay1\":44,\"locType\":\"F\"},{\"bay1\":45,\"locType\":\"F\"},{\"bay1\":46,\"locType\":\"F\"},{\"bay1\":47,\"locType\":\"F\"}],\"lev\":1}]}";
		return JSONObject.parse(s);
//		return R.ok(res);
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
