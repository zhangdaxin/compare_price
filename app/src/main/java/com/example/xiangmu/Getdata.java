package com.example.xiangmu;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.xiangmu.main_layout.search_main;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import middle_commodity.Grid;
import middle_commodity.MyPojo;
import middle_commodity.Spus;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;

public class Getdata {
    public static String responseData;
    public static String responseData1;
    public static String url;
    public static String shop;
    public static List<Spus> sp=new ArrayList<Spus>();
    /*
    从淘宝中获取商品数据
     */
    public static void get()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int k=1;
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
                    responseData=responseData.substring(responseData.indexOf("grid")-1);
                    responseData="{"+responseData.substring(0,responseData.indexOf("header")-2)+"}";
                    Log.d("result:", "run:"+responseData);
                    search_main.dialog.dismiss();
//                    Grid grid=new Grid();
//                    responseData=JSON.toJSONString(grid, SerializerFeature.WriteClassName);
                //    Log.d("", "run: "+responseData);
                     MyPojo m= JSON.parseObject(responseData,MyPojo.class);
                    Spus[] modes= m.getGrid().getData().getSpus();

                    for (int i=0;i<modes.length;i++) {
                        String pic_url=modes[i].getPic_url();
                        String title=modes[i].getTitle();
                        String price=modes[i].getPrice();
                        String month_sales=modes[i].getMonth_sales();
                        url="http:"+modes[i].getUrl();
                        if(k==1)
                        {
                            shop="淘宝商城";
                        }
                        Spus spus=new Spus("http:"+pic_url,title,shop+":","价格为:￥"+price,"月销量: "+month_sales,url);
                        sp.add(spus);
                    }
                    Log.d("", "run: "+m);

                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
