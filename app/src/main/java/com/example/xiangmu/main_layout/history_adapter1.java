package com.example.xiangmu.main_layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.xiangmu.R;

import java.util.List;

import static com.example.xiangmu.Gethistorical_item1.list;

public class history_adapter1 extends ArrayAdapter<historical_item> {
    private int resourceId;
    public history_adapter1(@NonNull Context context, int resource, List<historical_item> objects) {
        super(context, resource,objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        historical_item his=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null)
        {
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.history2=view.findViewById(R.id.history2);
            view.setTag(viewHolder);//将ViewHolder储存在View中

        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.history2.setText(list.get(position).getItem());
        return view;
    }
    class ViewHolder {

        TextView history2;

    }
}