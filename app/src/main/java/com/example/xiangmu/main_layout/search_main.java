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

public class search_main extends AppCompatActivity implements View.OnClickListener {
    public static String searchkeyword;
    private EditText searchkey_word;
    private TextView search1;
    private ImageView return6;
    public List list=new ArrayList();
    public FrameLayout history_1;
    public static ProgressDialog dialog;
    public Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);
        initView();
//        /**
//         * 如果是语音录入的情况
//         */
//        if(!searchkeyword.equals(""))
//        {
//            searchkey_word.setText(searchkeyword);
//            searchkey_word.setSelection(searchkey_word.length());//把光标定位末尾
//        }
        replaceFragment(new frame_history());
        initListener();
    }

    private void initListener() {
        search1.setOnClickListener(this);
        return6.setOnClickListener(this);
    }

    private void initView() {
        dialog=new ProgressDialog(this);
        dialog.setTitle("加载中");
        dialog.setMessage("请稍后...");
        dialog.setCancelable(false);

        searchkey_word=findViewById(R.id.search_keyword);
        search1=findViewById(R.id.search_1);
        history_1=findViewById(R.id.history_1);
        return6=findViewById(R.id.return6);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.search_1:
                searchkeyword=searchkey_word.getText().toString();
                if(!searchkeyword.equals(""))
                {
                    dialog.show();
                    postHistory();
                    Getdata.get();
                    replaceFragment(new show_modes());
                }else{
                    Toast.makeText(this, "你没有输入想要查询的商品!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.return6:
                main_layout.choose=2;
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
                        .add("userid",MainActivity.userid)
                        .add("searchkeyword", searchkeyword)
                        .build();

                Request request = new Request.Builder()
                        .url("http://"+ Ip.ip+":8080/project/SaveSearch")
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
        fragmentTransaction.replace(R.id.history_1,fragment);
        fragmentTransaction.commit();
    }

}
