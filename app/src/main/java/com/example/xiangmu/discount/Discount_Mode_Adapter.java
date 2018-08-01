package com.example.xiangmu.discount;

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
import java.util.List;

import static com.example.xiangmu.main_layout.discount_page1.dm;


public class Discount_Mode_Adapter extends ArrayAdapter<Discount_Mode> {
    private int resourceId;
    Intent intent;
    public Discount_Mode_Adapter(@NonNull Context context, int resource, @NonNull List<Discount_Mode> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view= LayoutInflater.from(getContext()).inflate(R.layout.activity_discount_modes,parent,false);
            viewHolder.img=view.findViewById(R.id.discount_pic_modes);
            viewHolder.title=view.findViewById(R.id.discount_title);
            viewHolder.new_money=view.findViewById(R.id.discount_new_money);
            viewHolder.old_money=view.findViewById(R.id.discount_old_money);
            viewHolder.discount=view.findViewById(R.id.dis_discount);
            viewHolder.go_to_buy=view.findViewById(R.id.go_to_buy_2);

            view.setTag(viewHolder);//将ViewHolder储存在View中
        } else {
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        String url=dm.get(position).getPic_url();
        Log.d("", "getView: "+url);

        viewHolder.img.setTag(url);
        new ImageLoader().showImageByThead(viewHolder.img,url);
        viewHolder.title.setText(dm.get(position).getTitle());
        viewHolder.new_money.setText(dm.get(position).getNew_money());
        viewHolder.old_money.setText(dm.get(position).getOld_money());
        viewHolder.discount.setText(dm.get(position).getDiscount());

        viewHolder.go_to_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse(dm.get(position).getUrl()));//为Intent设置数据
            //    intent.setData(Uri.parse("http://www.shopin.net/product/7339231.html"));
                Log.d("", "onClick: "+dm.get(position).getUrl());
                getContext().startActivity(intent);//将Intent传递给Activity
            }
        });
        return view;
    }


    class ViewHolder {
        ImageView img;
        TextView title;
        TextView new_money;
        TextView discount;
        TextView old_money;
        Button go_to_buy;
    }
}
