package com.cool.demo.manager.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cool.demo.manager.entity.Mate;
import com.cool.demo.manager.service.MateService;
import com.core.common.SpringUtils;
import com.core.exception.CoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by vincent on 2019-11-25
 */
public class MateExcelListener extends AnalysisEventListener<Mate> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MateExcelListener.class);

    private int total = 0;
    private Long userId;

    public MateExcelListener() {
    }

    public MateExcelListener(Long userId) {
        this.userId = userId;
    }

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 50;

    private final List<Mate> list = new ArrayList<>();

    /**
     * 这里会一行行的返回头
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    }

    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(Mate data, AnalysisContext ctx) {
        MateService mateService = SpringUtils.getBean(MateService.class);
        if (mateService.selectById(data.getCode()) == null) {
            data.setUpdateTime(new Date());
            data.setUpdateBy(this.userId);
            data.setCreateTime(new Date());
            data.setCreateBy(this.userId);
            if (!mateService.insert(data)) {
                throw new CoolException("导入数据异常");
            }
            total ++;
        }
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
//        if (list.size() >= BATCH_COUNT) {
//
//            // 存储完成清理 list
//            list.clear();
//        }
    }

    /**
     * 所有数据解析完成了调用
     * 适合事务
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext ctx) {
        LOGGER.info("新增{}条物料信息！", total);
    }

    public int getTotal() {
        return total;
    }
}
