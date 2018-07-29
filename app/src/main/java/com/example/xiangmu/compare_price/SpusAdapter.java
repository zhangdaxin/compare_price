package com.example.xiangmu.compare_price;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.xiangmu.R;

import java.io.InputStream;
import java.util.List;

import middle_commodity.Spus;

import static com.example.xiangmu.Getdata.sp;


public class SpusAdapter extends ArrayAdapter<Spus> {

private int resourceId;

/*
适配器
 */
    public SpusAdapter(@NonNull Context context, int resource, @NonNull List<Spus> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

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
            viewHolder.months_sales=view.findViewById(R.id.months_sales);

            view.setTag(viewHolder);//将ViewHolder储存在View中
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }

        String url=sp.get(position).getPic_url();
        Log.d("", "getView: "+url);
        viewHolder.img.setTag(url);
        new ImageLoader().showImageByThead(viewHolder.img,url);

        viewHolder.img=view.findViewById(R.id.pic_modes);
        viewHolder.introduction1.setText(sp.get(position).getTitle());
        viewHolder.shop1.setText(sp.get(position).getShop());
        viewHolder.price1.setText(sp.get(position).getPrice());
        viewHolder.months_sales.setText(sp.get(position).getMonth_sales());

        return view;
    }
    class ViewHolder {
        ImageView img;
        TextView introduction1;
        TextView shop1;
        TextView price1;
        TextView months_sales;
    }
}
