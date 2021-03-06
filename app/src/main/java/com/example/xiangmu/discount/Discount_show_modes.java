package com.example.xiangmu.discount;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xiangmu.Getdata;
import com.example.xiangmu.R;
import com.example.xiangmu.main_layout.discount_page1;

import java.util.List;

import static com.example.xiangmu.main_layout.discount_page1.dm;

public class Discount_show_modes extends Fragment {
    public static final int SUCCESS=0;
    public static final int FAIL=1;
    private ListView listView;
    private Discount_Mode_Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_discount_list,container,false);
        return view;
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
                    discount_page1.dialog.dismiss();
                    adapter.notifyDataSetChanged();
                    break;
                case FAIL:
                    Toast.makeText(getActivity(), "存储失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void update(){
        discount_page1.getDiscountModes(handler);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        adapter = new Discount_Mode_Adapter(getContext(),R.layout.activity_discount_modes,dm);
        listView.setAdapter(adapter);
        update();
    }

    private void initView() {
        listView =getActivity().findViewById(R.id.discount_list);
    }

}