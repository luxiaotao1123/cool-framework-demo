package com.cool.demo.asrs.mapper;

import com.cool.demo.asrs.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReportQueryMapper {
	
	//分页查询库位使用率
	List<ViewStockUseBean> queryViewStockUseList(ViewStockUseBean viewStockUse);
	int getViewStockUseCount(ViewStockUseBean viewStockUse);
	List<ViewStockUseBean> getViewStockUseAll(ViewStockUseBean viewStockUse);

	//分页查询库存滞留时间
	public List<ViewStayTimeBean> queryViewStayTimeList(ViewStayTimeBean viewStayTime);
	public int getViewStayTimeCount(ViewStayTimeBean viewStayTime);
	//不分页查询所有信息，用于excel导出
	public List<ViewStayTimeBean> getViewStayTimeAll(ViewStayTimeBean viewStayTime);

	// 库位Map
	@Select("select distinct lev1 from asr_loc_mast where row1=#{row1} order by lev1 desc")
	public List<String> getViewLocLevCount(@Param("row1")int row1);

	@Select("select bay1,loc_type as locType from asr_loc_mast where row1=#{row1} and lev1=#{lev1} order by bay1")
	public List<ViewLocMapDto> getViewLocBays(@Param("row1")int row1, @Param("lev1")int lev1);

	//分页查询站点入出库次数统计
	public List<ViewInOutBean> queryViewInOutList(ViewInOutBean viewInOut);
	public int getViewInOutCount(ViewInOutBean viewInOut);
	//不分页查询所有信息，用于excel导出
	public List<ViewInOutBean> getViewInOutAll(ViewInOutBean viewInOut);

	//分页查询日入库记录
	public List<ViewWorkInBean> queryViewWorkInList(ViewWorkInBean viewWorkIn);
	public int getViewWorkInCount(ViewWorkInBean viewWorkIn);
	//不分页查询所有信息，用于excel导出
	public List<ViewWorkInBean> getViewWorkInAll(ViewWorkInBean viewWorkIn);

	//分页查询日出库记录
	public List<ViewWorkInBean> queryViewWorkOutList(ViewWorkInBean viewWorkOut);
	public int getViewWorkOutCount(ViewWorkInBean viewWorkOut);
	//不分页查询所有信息，用于excel导出
	public List<ViewWorkInBean> getViewWorkOutAll(ViewWorkInBean viewWorkOut);
}
