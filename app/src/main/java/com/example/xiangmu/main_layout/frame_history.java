package com.example.xiangmu.main_layout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xiangmu.Getdata;
import com.example.xiangmu.Gethistorical_item;
import com.example.xiangmu.R;
import com.example.xiangmu.compare_price.show_modes;

import java.util.ArrayList;
import java.util.List;

import static com.example.xiangmu.Gethistorical_item.list;

public class frame_history extends Fragment {
    public  ProgressDialog progressDialog;
    EditText et;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.activity_history_1,container,false);
       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("加载中");
        progressDialog.setMessage("请稍后...");
        progressDialog .setCancelable(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();                             //先获取当前布局的填充器
        View view1 = inflater.inflate(R.layout.activity_search_main, null);   //通过填充器获取另外一个布局的对象
        et=view1.findViewById(R.id.search_keyword);
        Gethistorical_item.get();
        history_adapter adapter = new history_adapter(getActivity(), R.layout.activity_history,list);
        ListView listView=getActivity().findViewById(R.id.historical_search);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                historical_item his=list.get(position);
                search_main.searchkeyword=his.getItem();
                progressDialog.show();
                Getdata.get();
                et.setText(search_main.searchkeyword);
                replaceFragment(new show_modes());
                progressDialog.dismiss();
                Toast.makeText(getActivity(), his.getItem(), Toast.LENGTH_SHORT).show();
            }
        });
        list.clear();
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.history_1,fragment);
        fragmentTransaction.commit();
    }


}
