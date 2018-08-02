package com.example.xiangmu.save_money_9kuai9;

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


public class save_money_9kuai9_adapter extends ArrayAdapter<save_9kuai9> {
    public List<save_9kuai9> s9=new ArrayList<save_9kuai9>();
    private int resourceId;
    Intent intent;
    public save_money_9kuai9_adapter(@NonNull Context context, int resource, @NonNull List<save_9kuai9> objects) {
        super(context,resource,objects);
        s9=objects;
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view= LayoutInflater.from(getContext()).inflate(R.layout.activity_save_9kuai9,parent,false);
            viewHolder.img=view.findViewById(R.id.pic_modes_9kuai9);
            viewHolder.title=view.findViewById(R.id.title_9kuai9);
            viewHolder.new_money=view.findViewById(R.id.new_money_9kuai9);
            viewHolder.old_money=view.findViewById(R.id.old_money_9kuai9);
            viewHolder.months_sales=view.findViewById(R.id.months_sales_9kuai9);
            viewHolder.go_to_get=view.findViewById(R.id.go_to_get_quan1);
            viewHolder._9kuai9=view.findViewById(R.id._9kuai9);
            view.setTag(viewHolder);//将ViewHolder储存在View中
        } else {
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        String url=s9.get(position).getPic_url();
        Log.d("", "getView: "+url);
        viewHolder.img.setTag(url);
        new ImageLoader().showImageByThead(viewHolder.img,url);
        viewHolder.title.setText(s9.get(position).getTitle());
        viewHolder.new_money.setText(s9.get(position).getNew_money());
        viewHolder.old_money.setText(s9.get(position).getOld_money());
        viewHolder.months_sales.setText(s9.get(position).getCount());
        viewHolder._9kuai9.setText(s9.get(position).getQuan());
        viewHolder.go_to_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse(s9.get(position).getUrl()));//为Intent设置数据
                Log.d("", "onClick: "+s9.get(position).getUrl());
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
        TextView _9kuai9;
        Button go_to_get;
    }
}
