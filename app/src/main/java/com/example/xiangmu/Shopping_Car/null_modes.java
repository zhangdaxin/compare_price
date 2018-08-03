package com.example.xiangmu.Shopping_Car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.xiangmu.R;
import static com.example.xiangmu.Shopping_Car.shopping_car.dialog;
public class null_modes extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_null_modes, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
        dialog.dismiss();
    }

    private void initListener() {

    }

    private void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
