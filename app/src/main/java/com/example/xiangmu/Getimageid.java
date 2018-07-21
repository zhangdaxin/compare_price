package com.example.xiangmu;


import android.util.Log;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Getimageid {
    public static void interaction() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("userid", MainActivity.userid)
                        .build();

                Request request = new Request.Builder()
                        .url("http://" + Ip.ip + ":8080/project/GetImageByiD")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Log.d("", "run: " + response);
                    String responseData = response.body().string();
                    Log.d("", "run: " + responseData);
                    if (response != null && response.isSuccessful()) {
                        Log.d("responseData", "run: "+responseData);
                        MainActivity.image=responseData;
                        Log.d("image", "run: "+MainActivity.image);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
