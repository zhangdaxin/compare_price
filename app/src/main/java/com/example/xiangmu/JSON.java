package com.example.xiangmu;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
/*
Json传送数据格式
 */
public class JSON {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static void post(String url,String json,okhttp3.Callback callback) {
        OkHttpClient client=new OkHttpClient();
        RequestBody body=RequestBody.create(JSON,json);
        Request request=new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
