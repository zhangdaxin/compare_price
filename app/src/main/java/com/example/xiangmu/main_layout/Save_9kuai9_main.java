package com.example.xiangmu.main_layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.example.xiangmu.R;
import com.example.xiangmu.save_money_9kuai9.save_9kuai9;
import com.example.xiangmu.save_money_9kuai9.save_money_9kuai9_adapter;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Save_9kuai9_main extends AppCompatActivity implements View.OnClickListener {
    public ListView listView;
    private ImageView return8;
    public Intent intent;
    public save_money_9kuai9_adapter adapter;
    public List<save_9kuai9> s9=new ArrayList<save_9kuai9>();
    public static final int SUCCESS=0;
    public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_9kuai9_main);
        initView();
        initListener();
        dialog.show();
        adapter = new save_money_9kuai9_adapter(Save_9kuai9_main.this,R.layout.activity_save_9kuai9, s9);
        listView.setAdapter(adapter);
        update();
        s9.clear();
    }

    private void update() {
      getsave_9kuai9_Data(handler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.return8:
                intent=new Intent(Save_9kuai9_main.this,main_layout.class);
                startActivity(intent);
                break;
        }
    }

    private void initListener() {
        return8.setOnClickListener(this);
    }

    private void initView() {
        dialog=new ProgressDialog(this);
        dialog.setTitle("加载中");
        dialog.setMessage("请稍后...");
        dialog.setCancelable(false);

        listView=findViewById(R.id.list_9kuai9);
        return8=findViewById(R.id.return8);
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
            }
        }
    };

    private void getsave_9kuai9_Data(final Handler handler) {

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
                    dealwith(handler,responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void dealwith(Handler handler,String html) {
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
                new_price = element2.getElementsByClass("price-current").text();
                new_price = new_price + "领券后";

                old_price = element2.getElementsByClass("price-old").text();
                quan = element2.getElementsByClass("item-coupon mui-act-item-couponColor").text();
                if (quan.equals("")||quan==null) {
                continue;
                }else{
                    quan=quan.substring(0,quan.length()-1);
                }
                count = element2.getElementsByClass("sold").text();
            }
            //   title
            Elements elementsByClass1 = element.getElementsByClass("good-title");
            for (org.jsoup.nodes.Element element2 : elementsByClass1) {
                title = element2.getElementsByTag("a").text();
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
                url1 = "http://www.ataoju.com" + url1;
            }
            if (!quan.equals("") && !pic_url.equals("") && !old_price.equals("")) {
                save_9kuai9 s = new save_9kuai9(pic_url, title, new_price, old_price, quan, count, url1);
                s9.add(s);
            }
        }
        handler.sendEmptyMessage(0);
    }

}
