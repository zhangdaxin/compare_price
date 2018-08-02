package com.example.xiangmu.main_layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.xiangmu.R;

public class activity_find_order extends AppCompatActivity implements View.OnClickListener {
private ImageView return11;
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
    }

    private void initListener() {
    return11.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.return11:
                main_layout.choose=4;
                intent=new Intent(activity_find_order.this,main_layout.class);
                startActivity(intent);
                break;
        }
    }
}
