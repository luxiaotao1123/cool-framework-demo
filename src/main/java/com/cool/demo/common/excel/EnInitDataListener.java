package com.cool.demo.common.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vincent on 2019-11-25
 */
public class EnInitDataListener extends AnalysisEventListener<EnInitData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnInitDataListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    List<EnInitData> list = new ArrayList<>();

    /**
     * 这里会一行行的返回头
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        LOGGER.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
    }

    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(EnInitData data, AnalysisContext ctx) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了调用
     * 适合事务
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext ctx) {
        LOGGER.info("所有数据解析完成！");
    }
}
