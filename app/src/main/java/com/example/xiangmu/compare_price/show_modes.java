package com.example.xiangmu.compare_price;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xiangmu.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class show_modes extends Fragment {
  private List<Modes> mos=new ArrayList<Modes>();
  Bitmap bit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_show_modes,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initModes();
        modesAdapter adapter = new modesAdapter(getActivity(), R.layout.activity_modes,mos);
        ListView listView =getActivity().findViewById(R.id.modes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Modes m=mos.get(position);
                Toast.makeText(getActivity(),m.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*
    遍历数据
     */
    private void initModes() {
        Modes m=new Modes("http:"+"//g-search1.alicdn.com/img/bao/uploaded/i4/TB184Y0SpXXXXazaXXXXXXXXXXX.jpg","352 X83空气净器","京东商城"+":","价格为:￥"+"3000","月销量: "+"2000");
        mos.add(m);
        Modes m1 = new Modes("http:"+"//g-search1.alicdn.com/img/bao/uploaded/i4/TB1ZKT2SpXXXXc2aXXXXXXXXXXX.jpg","352 X83空气净器","京东商城","价格为:￥"+"2000","月销量: "+"2000");
        mos.add(m1);
        Modes m2=new Modes("http:"+"//g-search2.alicdn.com/img/bao/uploaded/i4/i4/TB1BH0NbBjTBKNjSZFwXXcG4XXa.jpg","352 X83空气净器","京东商城","价格为:￥"+"1000","月销量: "+"1000");
        mos.add(m2);
        Modes m3 = new Modes("http:"+"//g-search3.alicdn.com/img/bao/uploaded/i4/TB1DcfxaborBKNjSZFjXXc_SpXa.jpg","352 X83空气净器","京东商城","价格为:￥"+"1500","月销量: "+"1000");
        mos.add(m3);
        Modes m4=new Modes("http:"+"//g-search3.alicdn.com/img/bao/uploaded/i4/TB1DcfxaborBKNjSZFjXXc_SpXa.jpg","352 X83空气净器","京东商城","价格为:￥"+"4500","月销量: "+"200");
        mos.add(m4);
        Modes m5 = new Modes("http:"+"//g-search3.alicdn.com/img/bao/uploaded/i4/TB1DcfxaborBKNjSZFjXXc_SpXa.jpg","352 X83空气净器","京东商城","价格为:￥"+"8000","月销量: "+"354");
        mos.add(m5);
    }

}
