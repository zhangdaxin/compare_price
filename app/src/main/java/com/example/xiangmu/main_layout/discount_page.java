package com.example.xiangmu.main_layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.xiangmu.R;

public class discount_page extends Fragment implements View.OnClickListener {
    Intent intent;
    EditText discount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_discount_page, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }

    private void initListener() {
        discount.setOnClickListener(this);
    }

    private void initView() {
        discount = getActivity().findViewById(R.id.discount_1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.discount_1:
                intent = new Intent(getActivity(), discount_page1.class);
                startActivity(intent);
                break;
        }
    }
}
