package com.example.xiangmu.Log_Regist_Forget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xiangmu.R;

public class  Regist_Activity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        replaceFragment(new Regist_Activity1());//调用碎片进行布局的显示

    }
    /*
    碎片方法
     */
    private  void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.FrameLayout,fragment);
        transaction.commit();
    }

}
