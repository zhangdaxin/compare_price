package com.example.xiangmu.main_layout;

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
import java.util.ArrayList;
import java.util.List;


public class home_modes_adapter extends ArrayAdapter<home_modes> {
    public List<home_modes> hm=new ArrayList<home_modes>();
    private int resourceId;
    Intent intent;
    public home_modes_adapter(@NonNull Context context, int resource, @NonNull List<home_modes> objects) {
        super(context,resource,objects);
        hm=objects;
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view= LayoutInflater.from(getContext()).inflate(R.layout.activity_home_modes,parent,false);
            viewHolder.img=view.findViewById(R.id.home_pic_modes);
            viewHolder.title=view.findViewById(R.id.home_title);
            viewHolder.new_money=view.findViewById(R.id.home_new_money);
            viewHolder.old_money=view.findViewById(R.id.home_old_money);
            viewHolder.months_sales=view.findViewById(R.id.home_months_sales);
            viewHolder.go_to_get=view.findViewById(R.id.go_to_get_quan2);
            viewHolder.quan=view.findViewById(R.id.home_quan);
            view.setTag(viewHolder);//将ViewHolder储存在View中
        } else {
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        String url=hm.get(position).getPic_url();
        Log.d("", "getView: "+url);
        viewHolder.img.setTag(url);
        new ImageLoader().showImageByThead(viewHolder.img,url);
        viewHolder.title.setText(hm.get(position).getTitle());
        viewHolder.new_money.setText(hm.get(position).getNew_money());
        viewHolder.old_money.setText(hm.get(position).getOld_money());
        viewHolder.months_sales.setText(hm.get(position).getCount());
        viewHolder.quan.setText(hm.get(position).getQuan());
        viewHolder.go_to_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse(hm.get(position).getUrl()));//为Intent设置数据
                Log.d("", "onClick: "+hm.get(position).getUrl());
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
