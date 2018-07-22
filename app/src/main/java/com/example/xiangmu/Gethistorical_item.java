package com.example.xiangmu;

import android.util.Log;

import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.main_layout.historical_item;
import com.example.xiangmu.main_layout.search_main;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Gethistorical_item {
    public static String receivekeyword;
    public static List<historical_item> list=new ArrayList<historical_item>();
    public static  void get() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("userid",MainActivity.userid)
                        .build();

                Request request = new Request.Builder()
                        .url("http://"+ Ip.ip+":8080/project/SearchById")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    receivekeyword = response.body().string();
                    JSONArray jsonArray = new JSONArray(receivekeyword);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String keyword = jsonObject.getString("searchkeyword");
                        Log.d("keyword", "run: "+keyword);
                        historical_item historical_item = new historical_item(keyword);
                        list.add(historical_item);
                    }
                    Log.d("receivekeyword:", "run:" + receivekeyword);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
