package com.example.xiangmu.Shopping_Car;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.xiangmu.R;
import java.util.List;
import static com.example.xiangmu.Shopping_Car.shopping_car.mos;

public class shopping_car_adapter extends ArrayAdapter<shopping_car_modes>  {
    private int resourceId;
    private LayoutInflater minflate;
    Intent intent;
    /*
    适配器
     */
    public shopping_car_adapter(@NonNull Context context, int resource, @NonNull List<shopping_car_modes> objects) {
        super(context, resource, objects);
         resourceId = resource;
         minflate=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mos.size();
    }

    @Nullable
    @Override
    public shopping_car_modes getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = minflate.inflate(R.layout.activity_shopping_car_list, null);
            viewHolder.img = convertView.findViewById(R.id.pic_shopping_car);
            viewHolder.title = convertView.findViewById(R.id.title_shopping_car);
            viewHolder.shopname = convertView.findViewById(R.id.shopname);
            viewHolder.price = convertView.findViewById(R.id.price_shopping_car);
            viewHolder.months_sales = convertView.findViewById(R.id.months_sales_shopping);
            viewHolder.checkBox = convertView.findViewById(R.id.checkbox_button);
            viewHolder.go_to_buy=convertView.findViewById(R.id.go_to_buy_1);

            convertView.setTag(viewHolder);//将ViewHolder储存在View中
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String url=mos.get(position).getPic_url();
        Log.d("", "getView: "+url);
        viewHolder.img.setTag(url);
        new ImageLoader().showImageByThead(viewHolder.img,url);
        viewHolder.title.setText(mos.get(position).getTitle());
        viewHolder.shopname.setText(mos.get(position).getShop());
        viewHolder.price.setText(mos.get(position).getPrice());
        viewHolder.months_sales.setText(mos.get(position).getMonth_sales());
        viewHolder.checkBox.setChecked(mos.get(position).isSelected());//设置选中与否
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mos.get(position).setSelected(isChecked);//设置这个位置的是否选中情况
        }
        });
        viewHolder.go_to_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse(mos.get(position).getUrl()));//为Intent设置数据
                Log.d("", "onClick: "+mos.get(position).getUrl());
                getContext().startActivity(intent);//将Intent传递给Activity
            }
        });
        return convertView;
    }
    static class ViewHolder {
        ImageView img;
        TextView title;
        TextView shopname;
        TextView price;
        TextView months_sales;
        CheckBox checkBox;
        Button go_to_buy;
    }
}