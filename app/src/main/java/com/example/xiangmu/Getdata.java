package com.example.xiangmu;

import android.util.Log;

import com.example.xiangmu.main_layout.search_main;
import com.example.xiangmu.main_layout.search_page;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Getdata {
    public static String responseData;
    public static String responseData1;
    /*
    从淘宝中获取商品数据
     */
    public static void get()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url("https://s.taobao.com/search?q="+ search_main.searchkeyword)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    responseData=response.body().string();
                    String s="g_page_config";
                    String s1="g_srp_loadCss";
                    /*
                    获取完数据截取数据
                     */
                    responseData=responseData.substring(responseData.indexOf(s));
                    responseData=responseData.substring(0,responseData.indexOf(s1));
                    responseData=responseData.substring(responseData.indexOf("= ")+2);
                    responseData=responseData.substring(0,responseData.indexOf("};")+1);
                    Log.d("result:", "run:"+responseData);

                    /*
                    反序列化
                     */
                    JsonParser jsonParser = new JsonParser();
                    JsonElement userJsonElement =jsonParser.parse(responseData);
                    JsonObject userJsonObject = userJsonElement.getAsJsonObject();

                    Log.d("userJsonObject", "run: "+userJsonObject);
                    search_main.dialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient client=new OkHttpClient();
//                Request request=new Request.Builder()
//                        .url("https://search.jd.com/Search?keyword="+search_page.searchkeyword)
//                        .build();
//                try {
//                    Response response=client.newCall(request).execute();
//                    responseData1=response.body().string();
//                    String s="g_page_config";
//                    String s1="g_srp_loadCss";
////                    responseData1=responseData.substring(responseData.indexOf(s));
////                    responseData1=responseData.substring(0,responseData.indexOf(s1));
////                    responseData1=responseData.substring(responseData.indexOf("= ")+2);
//                    //responseData=responseData.substring(responseData.indexOf("= ")+2,responseData.length());
//                    Log.d("result:", "run:"+responseData1);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }
}
