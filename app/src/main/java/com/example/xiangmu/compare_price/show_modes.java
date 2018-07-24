package com.example.xiangmu.compare_price;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xiangmu.R;

import java.util.ArrayList;
import java.util.List;

import middle_commodity.Spus;

public class show_modes extends Fragment {
  private List<Spus> mos=new ArrayList<Spus>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_show_modes,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*
       遍历数据
        */
        SpusAdapter adapter = new SpusAdapter(getActivity(), R.layout.activity_modes,mos);
        ListView listView =getActivity().findViewById(R.id.modes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Spus s=mos.get(position);
               Toast.makeText(getActivity(),s.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
