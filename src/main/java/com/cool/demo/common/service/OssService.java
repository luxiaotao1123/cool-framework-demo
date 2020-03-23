package com.cool.demo.common.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.core.common.SnowflakeIdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * 阿里云OSS服务类
 * Created by vincent on 2019-11-26
 */
@Service("ossService")
public class OssService {

    private static Logger log = LoggerFactory.getLogger(OssService.class);

    private static final String OSS_ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";

    @Value("${aliyun.oss.id}")
    private String accessKeyId;

    @Value("${aliyun.oss.secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket}")
    private String bucket;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 上传OSS文件
     * @return the url
     */
    public String upload(InputStream inputStream, String suffix){
        OSSClient ossClient = new OSSClient(OSS_ENDPOINT, accessKeyId, accessKeySecret);
        String name = "";
        try {
            if (!ossClient.doesBucketExist(bucket)) {
                ossClient.createBucket(bucket);
                log.info("您的Bucket不存在，创建Bucket：{}",bucket);
            }
            name = String.valueOf(snowflakeIdWorker.nextId()).concat(suffix);
            ossClient.putObject(bucket, name, inputStream);
            log.info("OSS文件上传成功: {}", name);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return endpoint.concat(name);
    }

    public String upload(InputStream inputStream) {
        return upload(inputStream, ".jpg");
    }

    /**
     * 下载OSS文件
     */
    public void download(String name) {
        OSSClient ossClient = new OSSClient(OSS_ENDPOINT, accessKeyId, accessKeySecret);
        try {
            OSSObject ossObject = ossClient.getObject(bucket, name);
            InputStream inputStream = ossObject.getObjectContent();
            // do transfer
            inputStream.close();
            log.info("OSS文件下载成功: {}", name);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 删除OSS文件
     */
    public boolean delete(String name) {
        OSSClient ossClient = new OSSClient(OSS_ENDPOINT, accessKeyId, accessKeySecret);
        try {
            ossClient.deleteObject(bucket, name);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            ossClient.shutdown();
        }
        return true;
    }

}
