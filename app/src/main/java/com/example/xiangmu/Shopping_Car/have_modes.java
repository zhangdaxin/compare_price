package com.example.xiangmu.Shopping_Car;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.example.xiangmu.Ip;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.xiangmu.Shopping_Car.shopping_car.dialog;
import static com.example.xiangmu.Shopping_Car.shopping_car.mos;

public class have_modes extends Fragment implements View.OnClickListener {

    private CheckBox choose_all;
    private ListView listView;
    private Button bt_delete;
    public static final int SUCCESS = 0;
    private String pid;
    shopping_car_adapter shopping_car_adapter;
    private List<id> list = new ArrayList<id>();
    public static final int DELETEFAIL = 1;
    public static final int DELETESUCCESS = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_have_modes, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
        handler.sendEmptyMessage(SUCCESS);
    }


    /*
     异常处理
    */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    NewsAsyncTask s = new NewsAsyncTask();
                    s.onPostExecute(mos);
                    break;
                case DELETEFAIL:
                    Toast.makeText(getActivity(), "删除失败!", Toast.LENGTH_SHORT).show();
                    break;
                case DELETESUCCESS:
                    if(mos.isEmpty()) {
                        replaceFragment(new null_modes());
                    }
                        Toast.makeText(getActivity(), "删除成功!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private void initListener() {
        bt_delete.setOnClickListener(this);
        choose_all.setOnClickListener(this);
    }


    private void initView() {
        bt_delete = getActivity().findViewById(R.id.bt_delete);
        choose_all = getActivity().findViewById(R.id.cb_choose_all);
        listView = getActivity().findViewById(R.id.shopping_car_list);
        View v=LayoutInflater.from(getContext()).inflate(R.layout.activity_shopping_car,null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_delete:
                for (int i = mos.size() - 1; i >= 0; i--) {
                    if (mos.get(i).isSelected()) {//如果选中的话
                        pid = mos.get(i).getModeId();
                        id i1 = new id(pid);
                        list.add(i1);
                        Log.d("", "onClick: " + 11111);
                        mos.remove(i); //如果选中了就将这个移除
                        Log.d("", "onClick: "+mos);
                    }
                }
                deleteData();
                dataChanged();
                break;
            case R.id.cb_choose_all:
                if (choose_all.isChecked()) {
                    for (int i = 0; i < mos.size(); i++) {
                        mos.get(i).mSelected = true;
                    }
                }else{
                    for (int i = 0; i < mos.size(); i++) {
                        mos.get(i).mSelected = false;
                    }
                }
                shopping_car_adapter.notifyDataSetChanged();
                break;
        }
    }
    private void deleteData() {
        for (int i = 0; i < list.size(); i++) {
            String j = list.get(i).getId();
            delete(j);
        }
    }

    public void delete(final String i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("pid", i)
                        .add("userid", MainActivity.userid)
                        .build();
                Log.d("delete：", "删除的ID是:" + i);
                Request request = new Request.Builder()
                        .url("http://" + Ip.ip + ":8080/project/DeleteProduct")
                        .post(body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful() && response != null) {
                        handler.sendEmptyMessage(DELETESUCCESS);
                    } else {
                        handler.sendEmptyMessage(DELETEFAIL);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.shopping_car_frame,fragment);
        fragmentTransaction.commit();
    }

    // 刷新listview和TextView的显示
    private void dataChanged() {
        // 通知listView刷新
        if(!mos.isEmpty()) {
            shopping_car_adapter.notifyDataSetChanged();
        }
    }

    class NewsAsyncTask extends AsyncTask<String, Void, List<shopping_car_modes>> {
        /**
         * 每一个List都代表一行数据
         *
         * @param
         * @return
         */
        @Override
        protected List<shopping_car_modes> doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(List<shopping_car_modes> modes) {
            super.onPostExecute(modes);
            if (getActivity() != null) {
                shopping_car_adapter = new shopping_car_adapter(getActivity(), R.layout.activity_shopping_car_list, mos);
                Log.d("", "onPostExecute: " + mos);
                listView.setAdapter(shopping_car_adapter);
                dialog.dismiss();
            }
        }

    }
}
