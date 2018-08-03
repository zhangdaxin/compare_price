package com.example.xiangmu.main_layout;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.xiangmu.R;

public class activity_find_order extends AppCompatActivity implements View.OnClickListener {
private ImageView return11;
private Button jingdong_order;
private Button taobao_order;
private Button tianmao_order;
private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_order);
        initView();
        initListener();
    }

    private void initView() {
    return11=findViewById(R.id.return11);
    jingdong_order=findViewById(R.id.jingdong_order);
    taobao_order=findViewById(R.id.taobao_order);
    tianmao_order=findViewById(R.id.tianmao_order);
    }

    private void initListener() {
    return11.setOnClickListener(this);
    jingdong_order.setOnClickListener(this);
    taobao_order.setOnClickListener(this);
    tianmao_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.return11:
                main_layout.choose=4;
                intent=new Intent(activity_find_order.this,main_layout.class);
                startActivity(intent);
                overridePendingTransition(R.anim.dong, R.anim.dong1);
                break;
            case R.id.jingdong_order:
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse("https://passport.jd.com/uc/login?ReturnUrl=https%3A%2F%2Forder.jd.com%2Fcenter%2Flist.action"));//为Intent设置数据
                startActivity(intent);//将Intent传递给Activity
                break;
            case R.id.taobao_order:
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse("https://buyertrade.taobao.com/trade/itemlist/list_bought_items.htm?spm=a1z02.1.a2109.d1000368.a1c6782dXlABIJ&nekot=1470211439694"));//为Intent设置数据
                startActivity(intent);//将Intent传递给Activity
                break;
            case R.id.tianmao_order:
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse("https://login.taobao.com/member/login.jhtml?redirectURL=http%3A%2F%2Fbuyertrade.taobao.com%2Ftrade%2Fitemlist%2Flist_bought_items.htm%3Fspm%3D875.7931836%252FB.a2226mz.4.66144265N4Bsc9%26t%3D20110530"));//为Intent设置数据
                startActivity(intent);//将Intent传递给Activity
                break;
        }
    }
}
