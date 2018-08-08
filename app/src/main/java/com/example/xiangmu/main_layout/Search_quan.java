package com.example.xiangmu.main_layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.xiangmu.R;
import com.example.xiangmu.search_quan.Quan_Mode;
import com.example.xiangmu.search_quan.Search_quan_Adapter;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Search_quan extends AppCompatActivity implements View.OnClickListener {
    public static final int SUCCESS=0;
    public static final int FAIL=1;
    private ListView listView;
    private ImageView return10;
    private Search_quan_Adapter adapter;
    public static List<Quan_Mode> qm=new ArrayList<Quan_Mode>();
    public static ProgressDialog dialog;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_quan);
        initView();
        initListener();
        dialog.show();
        adapter = new Search_quan_Adapter(Search_quan.this,R.layout.activity_quan,qm);
        listView.setAdapter(adapter);
        update();
        qm.clear();
    }

    private void initListener() {
        return10.setOnClickListener(this);
    }

    private void update() {
        getQuanModes(handler);
        getQuanModes1(handler);
    }
    /*
   异步处理
    */
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case SUCCESS:
                    dialog.dismiss();
                    adapter.notifyDataSetChanged();
                    break;
                case FAIL:
                    Toast.makeText(Search_quan.this, "存储失败!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public static void getQuanModes(final Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://www.ataoju.com/jiu")
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    dealwith(handler,responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getQuanModes1(final Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://www.shazhekou.com/jiu.html")
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    dealwith1(handler,responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void dealwith(Handler handler,String html) throws Exception{
        String count=null;
        String url1=null;
        String pic_url = null;
        String new_price=null;
        String title=null;
        String old_price=null;
        String quan=null;

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
                quan=element2.getElementsByClass("item-coupon mui-act-item-couponColor").text();
                quan=quan.substring(0,quan.length()-1);
                count = element2.getElementsByClass("sold").text();
            }
            //   title
            Elements elementsByClass1 = element.getElementsByClass("good-title");
            for (org.jsoup.nodes.Element element2 : elementsByClass1) {
                title= element2.getElementsByTag("a").text();
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
                url1="http://www.ataoju.com"+url1;
            }
            Quan_Mode d= new Quan_Mode(pic_url, title ,new_price,old_price,quan, count, url1);
            qm.add(d);
        }
        handler.sendEmptyMessage(0);
    }
    public static void dealwith1(Handler handler,String html) throws Exception{

        String count=null;
        String url1=null;
        String pic_url = null;
        String new_price=null;
        String title=null;
        String old_price=null;
        String quan=null;

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
                quan=element2.getElementsByClass("item-coupon mui-act-item-couponColor").text();
                quan=quan.substring(0,quan.length()-1);
                count = element2.getElementsByClass("sold").text();
            }
            //   title
            Elements elementsByClass1 = element.getElementsByClass("good-title");
            for (org.jsoup.nodes.Element element2 : elementsByClass1) {
                title= element2.getElementsByTag("a").text();
            }

            //   pic_url
            Elements elementsByClass2 = element.getElementsByClass("good-pic");
            for (org.jsoup.nodes.Element element2 : elementsByClass2) {
                pic_url= element2.getElementsByTag("img").attr("data-original");
            }
            //url
            Elements elementsByClass3 = element.getElementsByClass("item-btn");
            for (org.jsoup.nodes.Element element2 : elementsByClass3) {
                url1= element2.getElementsByTag("a").attr("href");
                url1="http://www.shazhekou.com/"+url1;
            }
            Quan_Mode d= new Quan_Mode(pic_url, title ,new_price,old_price,quan, count, url1);
            qm.add(d);
        }
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.return10:
                intent=new Intent(Search_quan.this,main_layout.class);
                startActivity(intent);
                break;
        }
    }


    private void initView() {
        dialog=new ProgressDialog(this);
        dialog.setTitle("加载中");
        dialog.setMessage("请稍后...");
        dialog.setCancelable(false);

        listView =findViewById(R.id.search_quan_list);
        return10=findViewById(R.id.return10);
    }
}
