package com.example.xiangmu.Mall_navigation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.xiangmu.R;
import com.example.xiangmu.main_layout.main_layout;

public class mall_navigation extends AppCompatActivity implements View.OnClickListener {
  private LinearLayout tianma;
  private LinearLayout taobao;
  private LinearLayout jingdong;
  private LinearLayout alibaba;
  private LinearLayout suningyigou;
  private ImageView return9;
  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_navigation);
        initView();
        initListener();
    }

    private void initListener() {
        tianma.setOnClickListener(this);
        taobao.setOnClickListener(this);
        jingdong.setOnClickListener(this);
        alibaba.setOnClickListener(this);
        suningyigou.setOnClickListener(this);
    }

    private void initView() {
        tianma=findViewById(R.id.tianmao);
        taobao=findViewById(R.id.taobao);
        jingdong=findViewById(R.id.jingdong);
        alibaba=findViewById(R.id.alibaba);
        suningyigou=findViewById(R.id.suningyigou);
        return9=findViewById(R.id.return9);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tianmao:
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse("https://www.tmall.com/"));//为Intent设置数据
                startActivity(intent);//将Intent传递给Activity
                break;
            case R.id.taobao:
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse("https://www.taobao.com/"));//为Intent设置数据
                startActivity(intent);//将Intent传递给Activity
                break;
            case R.id.jingdong:
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse("https://www.jd.com/"));//为Intent设置数据
                startActivity(intent);//将Intent传递给Activity
                break;
            case R.id.alibaba:
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse("https://www.1688.com/"));//为Intent设置数据
                startActivity(intent);//将Intent传递给Activity
                break;
            case R.id.suningyigou:
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse("https://www.suning.com/"));//为Intent设置数据
                startActivity(intent);//将Intent传递给Activity
                break;
            case R.id.return9:
                intent=new Intent(mall_navigation.this,main_layout.class);
                startActivity(intent);
                break;

        }
    }
}
