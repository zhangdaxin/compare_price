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

public class modesAdapter extends ArrayAdapter<Modes> {

private int resourceId;

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

            new DownloadImageTask((ImageView) view.findViewById(R.id.pic_modes))
                    .execute("https://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/d50735fae6cd7b8963e0d37b042442a7d8330ebe.jpg");
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
        new DownloadImageTask((ImageView) view.findViewById(R.id.pic_modes))
                .execute(m.getImg());
        viewHolder.img=view.findViewById(R.id.pic_modes);
        viewHolder.introduction1.setText(m.getMessage());
        viewHolder.shop1.setText(m.getShop());
        viewHolder.price1.setText(m.getPrice());
        viewHolder.months_sales.setText(m.getMonth_sales());

        return view;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    class ViewHolder {
        ImageView img;
        TextView introduction1;
        TextView shop1;
        TextView price1;
        TextView months_sales;
    }
}
