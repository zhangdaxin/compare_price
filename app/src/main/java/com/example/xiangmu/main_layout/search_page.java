package com.example.xiangmu.main_layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xiangmu.R;

public class search_page extends Fragment implements View.OnClickListener {
    Intent intent;
    TextView search;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_search_page,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }

    private void initListener() {
        search.setOnClickListener(this);
    }

    private void initView() {
        search=getActivity().findViewById(R.id.search_keyword1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.search_keyword1:
                intent =new Intent(getActivity(),search_main.class);
                startActivity(intent);
                break;
        }
    }

}
