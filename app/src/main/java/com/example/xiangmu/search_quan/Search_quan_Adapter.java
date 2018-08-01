package com.example.xiangmu.search_quan;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiangmu.R;
import com.example.xiangmu.discount.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class Search_quan_Adapter extends ArrayAdapter<Quan_Mode> {
    public List<Quan_Mode> qm=new ArrayList<Quan_Mode>();
    private int resourceId;
    Intent intent;
    public Search_quan_Adapter(@NonNull Context context, int resource, @NonNull List<Quan_Mode> objects) {
        super(context, resource, objects);
        qm=objects;
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view= LayoutInflater.from(getContext()).inflate(R.layout.activity_quan,parent,false);
            viewHolder.img=view.findViewById(R.id.quan_pic_modes);
            viewHolder.title=view.findViewById(R.id.quan_title);
            viewHolder.new_money=view.findViewById(R.id.quan_new_money);
            viewHolder.old_money=view.findViewById(R.id.quan_old_money);
            viewHolder.months_sales=view.findViewById(R.id.quan_months_sales);
            viewHolder.go_to_get=view.findViewById(R.id.go_to_get_quan);
            viewHolder.quan=view.findViewById(R.id.quan);
            view.setTag(viewHolder);//将ViewHolder储存在View中
        } else {
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        String url=qm.get(position).getPic_url();
        Log.d("", "getView: "+url);
        viewHolder.img.setTag(url);
        new ImageLoader().showImageByThead(viewHolder.img,url);
        viewHolder.title.setText(qm.get(position).getTitle());
        viewHolder.new_money.setText(qm.get(position).getNew_money());
        viewHolder.old_money.setText(qm.get(position).getOld_money());
        viewHolder.months_sales.setText(qm.get(position).getCount());
        viewHolder.quan.setText(qm.get(position).getQuan());
        viewHolder.go_to_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse(qm.get(position).getUrl()));//为Intent设置数据
                Log.d("", "onClick: "+qm.get(position).getUrl());
                getContext().startActivity(intent);//将Intent传递给Activity
            }
        });
        return view;
    }


    class ViewHolder {
        ImageView img;
        TextView title;
        TextView new_money;
        TextView months_sales;
        TextView old_money;
        TextView quan;
        Button go_to_get;
    }
}
