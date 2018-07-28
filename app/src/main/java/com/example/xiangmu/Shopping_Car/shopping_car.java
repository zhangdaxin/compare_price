package com.example.xiangmu.Shopping_Car;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xiangmu.Ip;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.R;
import com.example.xiangmu.main_layout.main_layout;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class shopping_car extends Fragment implements View.OnClickListener {
    public static List<shopping_car_modes> mos=new ArrayList<shopping_car_modes>();
    private Button bt_delete;
    private ListView shopping_list;
    private int checkNum;
    public shopping_car_adapter shopping_car_adapter;
    private CheckBox choose_all;
    private ListView listView;
    public static final int SUCCESS=0;
    public static final int FAIL=1;
    public static final int ERROR=2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_shopping_car,container,false);
        return view;
    }
    /*
       异常处理
        */
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case SUCCESS:
                    NewsAsyncTask n=new NewsAsyncTask();
                    n.onPostExecute(mos);
                    break;
                case FAIL:
                    Toast.makeText(getActivity(), "存储失败", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    Toast.makeText(getActivity(), "网络错误!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
        /*
       遍历数据
        */
        initGetCars();
    }
    class NewsAsyncTask extends AsyncTask<String, Void, List<shopping_car_modes>> {
        /**
         * 每一个List都代表一行数据
         *
         * @param
         * @return
         */
        @Override
        protected List<shopping_car_modes> doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(List<shopping_car_modes> modes) {
            super.onPostExecute(modes);
            shopping_car_adapter adapter = new shopping_car_adapter(getActivity(), R.layout.activity_shopping_car_list,mos);
            listView.setAdapter(adapter);
        }
    }

    public void initGetCars() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();

                RequestBody requestBody=new FormBody.Builder()
                        .add("userid", MainActivity.userid)
                        .build();

                Request request=new Request.Builder()
                        .url("http://"+ Ip.ip+":8080/project/FindproductById")
                        .post(requestBody)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    //Log.d("", "run: "+response);
                    String responseData = response.body().string();
                 //   Log.d("", "run: "+responseData);
                    JSONArray jsonArray=new JSONArray(responseData);
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String pic_url = jsonObject.getString("pic_url");
                        String title = jsonObject.getString("title");
                        String price = jsonObject.getString("price");
                        String month_sales = jsonObject.getString("month_sales");
                        String url = jsonObject.getString("url");
                        String shop = jsonObject.getString("shop");
                        String modeId=jsonObject.getString("pid");
                       // shopping_car_modes modes= new shopping_car_modes("http:" + pic_url, title, shop + ":", "价格为:￥" + price, "月销量: " + month_sales, url);
                        shopping_car_modes modes= new shopping_car_modes(pic_url, title, shop + ":", "价格为:￥" + price, "月销量: " + month_sales, url);
                        mos.add(modes);
                        handler.sendEmptyMessage(SUCCESS);
                    }
                    Log.d("", "initGetCars: "+mos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

//    private void initShoppingCar() {
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
//    }

    private void initListener() {
    bt_delete.setOnClickListener(this);
    choose_all.setOnClickListener(this);
    }

    private void initView() {
    bt_delete=getActivity().findViewById(R.id.bt_delete);
    shopping_list=getActivity().findViewById(R.id.shopping_car_list);
    choose_all=getActivity().findViewById(R.id.cb_choose_all);
    listView =getActivity().findViewById(R.id.shopping_car_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_delete:

                break;
            case R.id.cb_choose_all:
                break;
        }

}

    // 刷新listview和TextView的显示
    private void dataChanged() {
        // 通知listView刷新
        shopping_car_adapter.notifyDataSetChanged();

    }

}
