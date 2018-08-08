package com.example.xiangmu.compare_price;

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
import com.example.xiangmu.main_layout.SearchActivity;

import static com.example.xiangmu.Getdata.sp;

public class ShowDataFragment extends Fragment {
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    private ListView listView;
    private static SpusAdapter spusAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_show_modes, container, false);
        return view;
    }

    public void update(){
        Getdata.get(handler);
    }

    /*
     异步处理
      */
    public  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    SearchActivity.dialog.dismiss();
                    spusAdapter.notifyDataSetChanged();
                    break;
                case FAIL:
                    Toast.makeText(getActivity(), "存储失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getActivity().findViewById(R.id.modes);
        spusAdapter = new SpusAdapter(getActivity(), R.layout.activity_modes, sp);
        listView.setAdapter(spusAdapter);
        update();
    }

}
