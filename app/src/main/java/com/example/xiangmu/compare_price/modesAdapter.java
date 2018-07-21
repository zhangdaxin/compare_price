package com.example.xiangmu.compare_price;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.xiangmu.R;
import java.util.List;

public class modesAdapter extends ArrayAdapter<Modes> {

private int resourceId;

public Bitmap bit;
/*
适配器
 */
    public modesAdapter(@NonNull Context context, int resource, @NonNull List<Modes> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Modes m=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null)
        {
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.img=view.findViewById(R.id.pic_modes);
            viewHolder.introduction1=view.findViewById(R.id.introduction);
            viewHolder.shop1=view.findViewById(R.id.shop);
            viewHolder.price1=view.findViewById(R.id.price);
            view.setTag(viewHolder);//将ViewHolder储存在View中
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.img.setImageBitmap(m.getImg());
        viewHolder.introduction1.setText(m.getMessage());
        viewHolder.shop1.setText(m.getShop());
        viewHolder.price1.setText(m.getPrice());

        return view;
    }
    class ViewHolder {
        ImageView img;
        TextView introduction1;
        TextView shop1;
        TextView price1;
    }
}
