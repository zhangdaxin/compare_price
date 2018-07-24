package com.example.xiangmu;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.xiangmu.main_layout.search_main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
                    JSONObject jsonObject = JSONObject.parseObject(responseData) ;
                    for(java.util.Map.Entry<String,Object> entry:jsonObject.entrySet()){
                        System.out.print(entry.getKey()+"-"+entry.getValue()+"\t");
                    }
                //    MyPojo myPojo= gson.fromJson(responseData,MyPojo.class);
                 //     Grid grid=gson.fromJson(responseData,Grid.class);
                //     String title=grid.getData().getSpus().getTitle();
               //      Log.d("title", "run: "+title);
                //    List<Spus> list = JSON.parseObject(responseData, new TypeReference<List<Spus>>() {});
               //    Log.d("grid", "run: "+grid);
                //    Log.d("myPojo", "run: "+myPojo);
// for (int i=0;i<jsonArray.length();i++)
//                    {
//                        JSONObject jsonObject=jsonArray.getJSONObject(i);
//                        String pic_url=jsonObject.getString("pic_url");
//                        String title=jsonObject.getString("title");
//                        String price=jsonObject.getString("price");
//                        String month_sales=jsonObject.getString("month_sales");
//                        url=jsonObject.getString("url");
//                        url="http:"+url;
//                        if(k==1)
//                        {
//                            shop="京东商城";
//                        }
//                        Spus spus=new Spus("http:"+pic_url,title,shop+":","价格为:￥"+price,"月销量: "+month_sales,url);
//                        sp.add(spus);
//                    }
                   // Log.d("data", "run: "+data);
                    //JsonObject userJsonObject = userJsonElement.getAsJsonObject();

//                    Log.d("userJsonObject", "run: "+userJsonObject);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
