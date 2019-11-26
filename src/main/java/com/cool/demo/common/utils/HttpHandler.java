package com.cool.demo.common.utils;


import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Http协议客户端
 * @author vincent
 */
public class HttpHandler {

    private static final Integer DEFAULT_TIMEOUT_SECONDS = 5;
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json;charset=utf-8");

    private String uri;
    private String path;
    private String json;
    private Map<String, Object> params;
    private Map<String, Object> headers;
    private boolean https;
    private Integer timeout;
    private TimeUnit timeUnit;

    public HttpHandler(Builder builder){
        this.uri = builder.uri;
        this.path = builder.path;
        this.json = builder.json;
        this.params = builder.params;
        this.headers = builder.headers;
        this.https = builder.https;
        this.timeout = builder.timeout;
        this.timeUnit = builder.timeUnit;
    }

    /**
     * GET请求执行
     * @return the HttpHandler response
     */
    public String doGet() throws IOException {
        String url = paramsToUrl(uri, path, params, https);
        Request.Builder headerBuilder = new Request.Builder();
        if (headers != null && headers.size()>0){
            for (Map.Entry<String, Object> entry : headers.entrySet()){
                headerBuilder.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        Request request = headerBuilder.url(url).build();
        Response response = getClient(timeout, timeUnit).newCall(request).execute();
        return response.isSuccessful() ? response.body().string() : null;
    }

    /**
     * POST请求执行
     * @return the HttpHandler response
     */
    public String doPost() throws IOException {
        Request request;
        Request.Builder headerBuilder = new Request.Builder();
        if (headers != null && headers.size()>0){
            for (Map.Entry<String, Object> entry : headers.entrySet()){
                headerBuilder.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        if (json == null || "".equals(json)){
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, Object> entry : params.entrySet()){
                builder.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
            FormBody body = builder.build();
            request = headerBuilder
                    .url((https?"https://":"http://")+uri+path)
                    .post(body)
                    .build();
        } else {
            RequestBody body = RequestBody.create(MEDIA_TYPE, json);
            Request.Builder builder = headerBuilder.url((https?"https://":"http://")+uri+path);
            builder.header("Content-Type", "application/json;charset=UTF-8");
            request = builder.post(body).build();

        }
        Call call = getClient(timeout, timeUnit).newCall(request);
        Response response = call.execute();
        return response.body().string();

    }

    /**
     * get请求参数拼接方法
     * @return 请求行
     */
    private String paramsToUrl(String uri, String path, Map<String, Object> params, boolean isHttps) {
        StringBuilder res = new StringBuilder();
        res.append(isHttps ? "https://" : "http://");
        res.append(uri);
        if (path.length() > 0 && !(path.charAt(0) == '/')){
            res.append("/");
        }
        res.append(path);
        Optional.ofNullable(params).ifPresent(
                args -> {
                    res.append("?");
                    args.forEach((key, value) -> {
                        res.append(key);
                        res.append("=");
                        res.append(value);
                        res.append("&");
                    });
                }
        );
        String url = res.toString();
        if ("&".equals(url.substring(url.length()-1, url.length()))){
            url = url.substring(0, url.length()-1);
        }
        return url;
    }

    /**
     * 获取 okHttpClient
     * @return the HttpHandler instance
     */
    private OkHttpClient getClient(Integer timeout, TimeUnit timeUnit){
        return new OkHttpClient
                .Builder()
                .connectTimeout(timeout, timeUnit)
                .readTimeout(timeout, timeUnit)
                .build();
    }

    /**
     * Http协议报文建造者
     */
    public static class Builder {

        private String uri;
        private String path;
        private String json;
        private Map<String, Object> params;
        private Map<String, Object> headers;
        private boolean https;
        private Integer timeout;
        private TimeUnit timeUnit;

        {
            // 默认5s超时
            timeout = DEFAULT_TIMEOUT_SECONDS;
            timeUnit = TimeUnit.SECONDS;
            path = "";
        }

        /**
         * 建造器
         * @return the HttpHandler instance
         */
        public HttpHandler build(){
            if (null == this.uri || "".equals(this.uri)){
                throw new RuntimeException("uri is null");
            }
            if (this.uri.startsWith("http://")){
                this.uri = this.uri.substring(6,uri.length());
            } else if (this.uri.startsWith("https://")){
                this.uri = this.uri.substring(7,uri.length());
            }
            return new HttpHandler(this);
        }

        public Builder setUri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder setPath(String path) {
            if (!path.startsWith("/")){
                path = "/" + path;
            }
            this.path = path;
            return this;
        }

        public Builder setTimeout(Integer timeout, TimeUnit timeUnit) {
            this.timeout = timeout;
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder setParams(Map<String, Object> params) {
            this.params = params;
            return this;
        }

        public Builder setHeaders(Map<String, Object> headers) {
            this.headers = headers;
            return this;
        }

        public Builder setHttps(boolean https) {
            this.https = https;
            return this;
        }

        public Builder setJson(String json) {
            this.json = json;
            return this;
        }

    }

}
