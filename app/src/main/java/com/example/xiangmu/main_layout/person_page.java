package com.example.xiangmu.main_layout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiangmu.Getimageid;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class person_page extends Fragment implements View.OnClickListener {
    private LinearLayout person_manage;
    private LinearLayout alter_message;
    private LinearLayout my_shopping_car;
    private LinearLayout find_order;
    private LinearLayout clean_rubbish;
    private TextView username;
    public  Bitmap bitmap;
    private ImageView head;
    Intent intent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_person_page,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }

    private void initListener() {
        person_manage.setOnClickListener(this);
        alter_message.setOnClickListener(this);
        my_shopping_car.setOnClickListener(this);
        find_order.setOnClickListener(this);
        clean_rubbish.setOnClickListener(this);
    }

    private void initView() {
        person_manage = getActivity().findViewById(R.id.person_manage);
        username = getActivity().findViewById(R.id.user_name);
        head=getActivity().findViewById(R.id.head_1);
        alter_message=getActivity().findViewById(R.id.alter_message);
        my_shopping_car=getActivity().findViewById(R.id.my_shopping_car);
        find_order=getActivity().findViewById(R.id.find_order);
        clean_rubbish=getActivity().findViewById(R.id.clean_rubbish);

        if (!MainActivity.image.equals("null")) {
            Log.d("", "getImage: " + MainActivity.image);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getImage(MainActivity.image);
                }
            }).start();
        }else{

        }
        if (!MainActivity.username.equals("null")) {
            username.setText(MainActivity.username);
        } else {
            username.setText("未命名");
        }
    }

    public  void  getImage(String getpath) {
        Log.d("", "getImage: "+getpath);
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(getpath).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            System.out.println("tdw1");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Log.d("", "getImage: " + inputStream);
                bitmap = BitmapFactory.decodeStream(inputStream);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        head.setImageBitmap(bitmap);
                    }
                });
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.person_manage:
                intent=new Intent(getActivity(),manager_person.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter,R.anim.exit);
                break;
            case R.id.alter_message:
                intent=new Intent(getActivity(),manager_person.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter,R.anim.exit);
                break;
            case R.id.my_shopping_car:
                main_layout.choose=3;
                intent=new Intent(getActivity(),main_layout.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.dong, R.anim.dong1);
                break;
            case R.id.find_order:
                break;
            case R.id.clean_rubbish:
                Toast.makeText(getActivity(), "清理成功!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
