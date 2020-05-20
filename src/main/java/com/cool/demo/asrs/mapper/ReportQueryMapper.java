package com.cool.demo.asrs.mapper;

import com.cool.demo.asrs.entity.ViewStayTimeBean;
import com.cool.demo.asrs.entity.ViewStockUseBean;
import org.apache.ibatis.annotations.Mapper;
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
}
