package com.example.xiangmu.Shopping_Car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xiangmu.R;
import com.example.xiangmu.compare_price.SpusAdapter;

import java.util.ArrayList;
import java.util.List;

import middle_commodity.Spus;


public class shopping_car extends Fragment {
    private List<Spus> mos=new ArrayList<Spus>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_shopping_car,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initShoppingCar();
        initListener();
    }

    private void initShoppingCar() {
        /*
       遍历数据
        */
        shopping_car_adapter adapter = new shopping_car_adapter(getActivity(), R.layout.activity_shopping_car_list,mos);
        ListView listView =getActivity().findViewById(R.id.shopping_car_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Spus s=mos.get(position);
                Toast.makeText(getActivity(),s.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListener() {

    }

    private void initView() {

    }
}
