package com.example.xiangmu.main_layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiangmu.Getdata;
import com.example.xiangmu.R;
import com.example.xiangmu.compare_price.show_modes;

import java.util.ArrayList;
import java.util.List;

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
    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.history_1,fragment);
        fragmentTransaction.commit();
    }

}
