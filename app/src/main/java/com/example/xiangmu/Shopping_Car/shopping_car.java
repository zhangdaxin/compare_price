package com.example.xiangmu.Shopping_Car;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.xiangmu.Ip;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.R;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class shopping_car extends Fragment {
    public static List<shopping_car_modes> mos = new ArrayList<shopping_car_modes>();

    public static final int NULL = 3;
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static ProgressDialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_shopping_car, container, false);
        return view;
    }

    /*
      异常处理
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NULL:
                    dialog.dismiss();
                    replaceFragment(new null_modes());
                    break;
                case SUCCESS:
                    replaceFragment(new have_modes());
                    break;
                case FAIL:
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "网络错误!获取数据失败!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        dialog.show();
        /*
       遍历数据
        */
        initGetCars();
    }

    public void initGetCars() {
        mos.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("userid", MainActivity.userid)
                        .build();

                Request request = new Request.Builder()
                        .url("http://" + Ip.ip + ":8080/project/FindproductById")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    //Log.d("", "run: "+response);
                    String responseData = response.body().string();
                    Log.d("", "run: " + responseData);
                    if (responseData.equals("fail")) {
                        handler.sendEmptyMessage(NULL);
                    } else {
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String pic_url = jsonObject.getString("pic_url");
                            String title = jsonObject.getString("title");
                            String price = jsonObject.getString("price");
                            String month_sales = jsonObject.getString("month_sales");
                            String url = jsonObject.getString("url");
                            String shop = jsonObject.getString("shop");
                            String modeId = jsonObject.getString("pid");

                            // shopping_car_modes modes= new shopping_car_modes("http:" + pic_url, title, shop + ":", "价格为:￥" + price, "月销量: " + month_sales, url);
                            shopping_car_modes modes = new shopping_car_modes(pic_url, title, shop + ":", "价格为:￥" + price, "月销量: " + month_sales, url, modeId, false);
                            mos.add(modes);

                            handler.sendEmptyMessage(SUCCESS);
                        }
                    }
                    Log.d("", "initGetCars: " + mos);

                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(FAIL);
                }
            }
        }).start();

    }

    private void initView() {
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("加载中");
        dialog.setMessage("请稍后...");
        dialog.setCancelable(false);

    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.shopping_car_frame,fragment);
        fragmentTransaction.commit();
    }
}
