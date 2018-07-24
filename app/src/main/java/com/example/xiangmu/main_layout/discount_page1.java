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
import com.example.xiangmu.Getdata;
import com.example.xiangmu.Ip;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.R;
import com.example.xiangmu.compare_price.show_modes;
import java.util.ArrayList;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class discount_page1 extends AppCompatActivity implements View.OnClickListener {
    public static String discountkeyword;
    private EditText discount_keyword;
    private TextView search2;
    private ImageView return7;
    public List list=new ArrayList();
    public FrameLayout history_2;
    public static ProgressDialog dialog;
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
                    Getdata.get();
                    replaceFragment(new show_modes());
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
