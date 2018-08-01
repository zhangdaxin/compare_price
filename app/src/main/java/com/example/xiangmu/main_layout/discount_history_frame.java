package com.example.xiangmu.main_layout;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.sax.Element;
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
                historical_item his=list.get(position);
                discountkeyword=his.getItem();
                progressDialog.show();
                getDiscountModes();
                Log.d("", "onItemClick: "+1111);
                et.setText(discount_page1.discountkeyword);
                replaceFragment(new Discount_show_modes());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), his.getItem(), Toast.LENGTH_SHORT).show();
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
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://search.shopin.net/search/"+discountkeyword+"+.html")
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
        String discount = null;
        String url1 = null;
        String pic_url = null;
        String new_price = null;
        String title = null;
        String old_price = null;

        org.jsoup.nodes.Document document = Jsoup.parse(html);
            Elements elements = document.select("div[class=content addOn clear]");
            for (org.jsoup.nodes.Element element : elements) {
                Elements elements2 = element.getElementsByTag("li");
                for (org.jsoup.nodes.Element element2 : elements2) {
                    url1 = element2.getElementsByTag("a").attr("href");
                    pic_url = element2.getElementsByTag("img").attr("data-original");
                    title = element2.getElementsByClass("productName").text();
                    new_price = element2.getElementsByClass("price").text();
                    discount = element2.getElementsByClass("discount").text();
                    old_price = element2.getElementsByClass("gray").text();

                    Discount_Mode d = new Discount_Mode(pic_url, title, "打折后:" + new_price, "原价:" + old_price, discount, url1);
                    dm.add(d);
                }
        }
    }
    }


