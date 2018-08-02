package com.example.xiangmu.main_layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiangmu.Ip;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.R;
import com.example.xiangmu.discount.Discount_Mode;
import com.example.xiangmu.discount.Discount_show_modes;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class discount_page1 extends AppCompatActivity implements View.OnClickListener {
    public static String  discountkeyword;
    private EditText discount_keyword;
    private TextView search2;
    private ImageView return7;
    public FrameLayout history_2;
    public static ProgressDialog dialog;

    public static List<Discount_Mode> dm=new ArrayList<Discount_Mode>();
    public Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_page1);
        initView();
        initListener();
        replaceFragment(new discount_history_frame());
    }

    private void initListener() {
        search2.setOnClickListener(this);
        return7.setOnClickListener(this);
    }

    private void initView() {
        dialog=new ProgressDialog(this);
        dialog.setTitle("加载中");
        dialog.setMessage("请稍后...");
        dialog.setCancelable(false);

        discount_keyword=findViewById(R.id.discount_keyword);
        search2=findViewById(R.id.search_2);
        history_2=findViewById(R.id.history_2);
        return7=findViewById(R.id.return7);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.search_2:
                discountkeyword=discount_keyword.getText().toString();
                if(!discountkeyword.equals(""))
                {
                    dialog.show();
                    postHistory();
                    getDiscountModes();
                    replaceFragment(new Discount_show_modes());
                }else{
                    Toast.makeText(this, "你没有输入想要查询的商品!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.return7:
                main_layout.choose=5;
                intent=new Intent(this,main_layout.class);
                startActivity(intent);
                overridePendingTransition(R.anim.dong, R.anim.dong1);
                break;
        }
    }

    public static void getDiscountModes() {
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
        String month_sales=null;
        String url1=null;
        String pic_url = null;
        String new_price=null;
        String title=null;
        String old_price=null;
        org.jsoup.nodes.Document document = Jsoup.parse(html);
        Elements elements = document.select("div[class=list-good buy]");
        Log.d("", "dealwith: "+elements);
        for (org.jsoup.nodes.Element element : elements) {
            //   销量  领券前的价钱 领券后的价钱
            Elements elementsByClass = element.getElementsByClass("good-price");
            for (org.jsoup.nodes.Element element2 : elementsByClass) {
                new_price=element2.getElementsByClass("price-current").text();
                new_price=new_price+"领券后";
                old_price=element2.getElementsByClass("price-old").text();
            }
            //   title
            Elements elementsByClass1 = element.getElementsByClass("good-title");
            for (org.jsoup.nodes.Element element2 : elementsByClass1) {
                title= element2.getElementsByTag("a").text();
                month_sales = element2.getElementsByClass("sold").text();
            }

            //   pic_url
            Elements elementsByClass2 = element.getElementsByClass("good-pic");
            for (org.jsoup.nodes.Element element2 : elementsByClass2) {
                pic_url= element2.getElementsByTag("img").attr("data-original");
            }
            //url
            Elements elementsByClass3 = element.getElementsByClass("lingquan");
            for (org.jsoup.nodes.Element element2 : elementsByClass3) {
                url1= element2.getElementsByTag("a").attr("href");
                url1="http://www.suiyizhe.com"+url1;
            }
            Discount_Mode d= new Discount_Mode(pic_url, title ,new_price,old_price,month_sales+"件",url1);
            dm.add(d);
        }
    }

    private void postHistory() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("userid", MainActivity.userid)
                        .add("searchkeyword", discountkeyword)
                        .build();

                Request request = new Request.Builder()
                        .url("http://"+ Ip.ip+":8080/project/SaveSearch2")
                        .post(requestBody)
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    Log.d("response：", "run: " + response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.history_2,fragment);
        fragmentTransaction.commit();
    }

}
