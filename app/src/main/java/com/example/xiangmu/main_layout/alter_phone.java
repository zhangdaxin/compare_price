package com.example.xiangmu.main_layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.R;


public class alter_phone extends AppCompatActivity implements View.OnClickListener {
private Button alter_phone;
private TextView phone;
private ImageView return2;
private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_phone);
        initView();
        initListener();
    }

    private void initListener() {
        alter_phone.setOnClickListener(this);
        return2.setOnClickListener(this);
    }

    private void initView() {
        alter_phone=findViewById(R.id.alter_telephone);
        phone=findViewById(R.id.telephone2);
        return2=findViewById(R.id.return2);
        phone.setText(MainActivity.phone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.alter_telephone:
                intent=new Intent(alter_phone.this,alter_phone1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
                break;
           case R.id.return2:
                intent=new Intent(alter_phone.this,manager_person.class);
                startActivity(intent);
                overridePendingTransition(R.anim.dong, R.anim.dong1);
                break;
        }
    }
}
