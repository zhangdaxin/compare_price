package com.example.xiangmu.main_layout;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xiangmu.Gethistorical_item1;
import com.example.xiangmu.Ip;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.R;
import com.example.xiangmu.discount.Discount_Mode;
import com.example.xiangmu.discount.Discount_show_modes;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.xiangmu.Gethistorical_item1.list;
import static com.example.xiangmu.main_layout.discount_page1.dm;

public class discount_history_frame extends Fragment implements View.OnClickListener {
    public static ProgressDialog progressDialog;
    String discountkeyword;
    EditText et;
    Button remove_history1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_discount_history,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
        Gethistorical_item1.get();
        history_adapter1 adapter = new history_adapter1(getActivity(), R.layout.activity_history2,list);
        ListView listView=getActivity().findViewById(R.id.discount_history);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                discount_page1.dialog.show();
                historical_item his=list.get(position);
                discountkeyword=his.getItem();
                getDiscountModes();
                Log.d("", "onItemClick: "+1111);

                replaceFragment(new Discount_show_modes());
            }
        });
        list.clear();
    }

    private void initListener() {
        remove_history1.setOnClickListener(this);
    }

    private void initView() {
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("加载中");
        progressDialog.setMessage("请稍后...");
        progressDialog .setCancelable(false);

        remove_history1=getActivity().findViewById(R.id.remove_history1);
        //先获取当前布局的填充器
        View view1 =LayoutInflater.from(getContext()).inflate(R.layout.activity_discount_page1, null);   //通过填充器获取另外一个布局的对象
        et=view1.findViewById(R.id.discount_keyword);
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.history_2,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remove_history1:
                remove();
                replaceFragment(new discount_history_frame());
                break;
        }
    }

    private void remove() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("userid", MainActivity.userid)
                        .build();

                Request request = new Request.Builder()
                        .url("http://"+ Ip.ip+":8080/project/DeleteData2")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Log.d("receivekeyword:", "run:" + response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public  void getDiscountModes() {
        dm.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://www.suiyizhe.com/?m=search&a=index&k="+discountkeyword)
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    dealwith(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void dealwith(String html) throws Exception {
        String month_sales = null;
        String url1 = null;
        String pic_url = null;
        String new_price = null;
        String title = null;
        String old_price = null;
        org.jsoup.nodes.Document document = Jsoup.parse(html);
        Elements elements = document.select("div[class=list-good buy]");
        Log.d("", "dealwith: " + elements);
        for (org.jsoup.nodes.Element element : elements) {
            //   销量  领券前的价钱 领券后的价钱
            Elements elementsByClass = element.getElementsByClass("good-price");
            for (org.jsoup.nodes.Element element2 : elementsByClass) {
                new_price = element2.getElementsByClass("price-current").text();
                new_price = new_price + "领券后";
                old_price = element2.getElementsByClass("price-old").text();
            }
            //   title
            Elements elementsByClass1 = element.getElementsByClass("good-title");
            for (org.jsoup.nodes.Element element2 : elementsByClass1) {
                title = element2.getElementsByTag("a").text();
                month_sales = element2.getElementsByClass("sold").text();
            }

            //   pic_url
            Elements elementsByClass2 = element.getElementsByClass("good-pic");
            for (org.jsoup.nodes.Element element2 : elementsByClass2) {
                pic_url = element2.getElementsByTag("img").attr("data-original");
            }
            //url
            Elements elementsByClass3 = element.getElementsByClass("lingquan");
            for (org.jsoup.nodes.Element element2 : elementsByClass3) {
                url1 = element2.getElementsByTag("a").attr("href");
                url1="http://www.suiyizhe.com"+url1;
            }
            Discount_Mode d = new Discount_Mode(pic_url, title, new_price, old_price+"(折)", month_sales+"件", url1);
            dm.add(d);
        }
    }
}


