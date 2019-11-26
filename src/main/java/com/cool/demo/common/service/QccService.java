package com.cool.demo.common.service;

import com.alibaba.fastjson.JSON;
import com.cool.demo.common.entity.QccBasicResult;
import com.cool.demo.common.entity.QccFullResult;
import com.cool.demo.common.entity.QccMasterResult;
import com.cool.demo.common.utils.HttpHandler;
import com.cool.demo.common.utils.KeyedDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 企查查工商数据查询服务类
 * Created by vincent on 2019-11-17
 */
@Service("qccService")
public class QccService {

    private static Logger log = LoggerFactory.getLogger(QccService.class);
    // url
    private static final String URL = "http://api.qichacha.com/";
    // basic
    private static final String BASIC_PATH = "ECIV4/GetBasicDetailsByName";
    // master
    private static final String MASTER_PATH = "ECIV4/GetDetailsByName";
    // full
    private static final String FULL_PATH = "ECIV4/GetFullDetailsByName";
    // 企查查应用KEY
    @Value("${qcc.key}")
    private String QCC_KEY;
    // 企查查应用密钥
    @Value("${qcc.secret}")
    private String QCC_SECRET;

    private interface QccResultSupport<T> { T parse(String msg);}

    /**
     * 工商详情Basic版
     * @param keyword 搜索关键字（公司名、注册号、社会统一信用代码或KeyNo）注：社会组织、香港企业仅支持通过企业名称和KeyNo查询
     * @return the result
     */
    public QccBasicResult getBasicDetailsByName(String keyword){
        return doGet(BASIC_PATH, keyword, msg -> JSON.parseObject(msg, QccBasicResult.class));
    }

    /**
     * 工商详情Master版
     * @param keyword the keyword
     * @return the result
     */
    public QccMasterResult getDetailsByName(String keyword){
        return doGet(MASTER_PATH, keyword, msg -> JSON.parseObject(msg, QccMasterResult.class));
    }

    /**
     * 工商详情Full版
     * @param keyword the keyword
     * @return the result
     */
    public QccFullResult GetFullDetailsByName(String keyword){
        return doGet(FULL_PATH, keyword, msg -> JSON.parseObject(msg, QccFullResult.class));
    }

    /**
     * 调用企查查API
     */
    private  <T> T doGet(String path, String keyword, QccResultSupport<T> support) {
        Map<String, Object> header = new HashMap<>();
        String timeSpan = getTimeSpan();
        header.put("Token", enToken(timeSpan));
        header.put("Timespan", timeSpan);
        Map<String, Object> body = new HashMap<>();
        body.put("key", QCC_KEY);
        body.put("keyword", keyword);
        String response;
        try {
            response = new HttpHandler.Builder()
                    .setUri(URL)
                    .setPath(path)
                    .setHeaders(header)
                    .setParams(body)
                    .build()
                    .doGet();
            return support.parse(response);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成token
     * @return the token
     */
    private String enToken(String timeSpan){
        return KeyedDigest.getKeyedDigestMD5(QCC_KEY.concat(timeSpan).concat(QCC_SECRET), "").toUpperCase();
    }

    /**
     * 精确到秒的Unix时间戳
     * @return the timeSpan
     */
    private String getTimeSpan(){
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

}
