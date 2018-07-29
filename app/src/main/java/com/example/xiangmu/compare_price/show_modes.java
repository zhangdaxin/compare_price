package com.example.xiangmu.compare_price;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import java.util.List;

import middle_commodity.Spus;

import static com.example.xiangmu.Getdata.sp;

public class show_modes extends Fragment {
    public static final int SUCCESS=0;
    public static final int FAIL=1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_show_modes,container,false);
        return view;
    }
    /*
       异常处理
        */
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case SUCCESS:
                    NewsAsyncTask s=new NewsAsyncTask();
                    s.onPostExecute(sp);
                    break;
                case FAIL:
                    Toast.makeText(getActivity(), "存储失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    class NewsAsyncTask extends AsyncTask<String, Void, List<Spus>> {
        /**
         * 每一个List都代表一行数据
         *
         * @param
         * @return
         */
        @Override
        protected List<Spus> doInBackground(String... strings) {
            return null;
        }

        protected void onPostExecute(List<Spus> modes) {
            super.onPostExecute(modes);
            SpusAdapter adapter = new SpusAdapter(getActivity(), R.layout.activity_modes,sp);
            ListView listView =getActivity().findViewById(R.id.modes);
            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Spus s=sp.get(position);
//                    Toast.makeText(getActivity(),s.getTitle(), Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler.sendEmptyMessage(SUCCESS);
    }

}
