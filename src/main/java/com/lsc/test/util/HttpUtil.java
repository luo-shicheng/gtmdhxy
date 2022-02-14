package com.lsc.test.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final OkHttpClient CLIENT = new OkHttpClient();

    public static String request(HttpMethod httpMethod, String url, RequestBody requestBody, Headers headers) {
        Request request;
        if (httpMethod.equals(HttpMethod.POST)) {
            request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .headers(headers)
                .build();
        } else if (httpMethod.equals(HttpMethod.GET)) {
            request = new Request.Builder()
                .url(url)
                .get()
                .headers(headers)
                .build();
        } else {
            logger.warn("目前只支持post 和 get方法");
            return null;
        }
        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                String string = response.body().string();
                logger.debug("处理url信息: " + url + "\n返回消息: " + string);
                return string;
            } else {
                logger.error("查询接口失败，返回码：" + response.code());
            }
        } catch (Exception e) {
            logger.error("查询接口失败", e);
        }
        return null;
    }
}
