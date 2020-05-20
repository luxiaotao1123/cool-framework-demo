package com.cool.demo.asrs.mapper;

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

}
