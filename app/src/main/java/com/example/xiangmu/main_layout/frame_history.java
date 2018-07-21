package com.example.xiangmu.main_layout;

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

public class frame_history extends Fragment {
    private List<historical_item> his_list=new ArrayList<historical_item>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.activity_history_1,container,false);
       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initHistorys();
        history_adapter adapter = new history_adapter(getActivity(), R.layout.activity_history,his_list);
        ListView listView=getActivity().findViewById(R.id.historical_search);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                historical_item his=his_list.get(position);
                Toast.makeText(getActivity(), his.getItem(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initHistorys() {

        historical_item h = new historical_item(search_main.searchkeyword);
        his_list.add(h);

        historical_item h1 = new historical_item("");
        his_list.add(h1);

        historical_item h2 = new historical_item("");
        his_list.add(h2);

        historical_item h3 = new historical_item("");
        his_list.add(h3);

        historical_item h4 = new historical_item("");
        his_list.add(h4);
        historical_item h5 = new historical_item("");
        his_list.add(h5);

        historical_item h6 = new historical_item("");
        his_list.add(h6);

        historical_item h7=new historical_item("");
        his_list.add(h7);

        historical_item h8=new historical_item("");
        his_list.add(h8);

        historical_item h9=new historical_item("");
        his_list.add(h9);

    }

}
