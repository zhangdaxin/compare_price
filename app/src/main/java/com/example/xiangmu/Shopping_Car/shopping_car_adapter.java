package com.example.xiangmu.Shopping_Car;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

            convertView.setTag(viewHolder);//将ViewHolder储存在View中
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.img.setImageResource(R.drawable.ic_launcher_foreground);
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
        return convertView;
    }
    /***
     * AsyncTask加载图片
     */
//    class NewsAsyncTask extends AsyncTask<String,Void,Bitmap> {
//
//        private ImageView myImageView;
//        private String mUrl;
//
//        public NewsAsyncTask(ImageView imageView, String url) {
//            myImageView = imageView;
//            mUrl = url;
//        }
//
//        //String...params是可变参数接受execute中传过来的参数
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            String url = params[0];
//            //这里同样调用我们的getBitmapFromeUrl
//            Bitmap bitmap = null;
//            URLConnection connection ;
//            InputStream is;
//            try {
//                connection = new URL(url).openConnection();
//                is = connection.getInputStream();
//                BufferedInputStream bis = new BufferedInputStream(is);
//                bitmap = BitmapFactory.decodeStream(bis);
//                bis.close();
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return bitmap;
//        }

//        //这里的bitmap是从doInBackgroud中方法中返回过来的
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
////            if(myImageView.getTag().equals(mUrl)){
//                myImageView.setImageBitmap(bitmap);
////            }
//        }
//    }

    static class ViewHolder {
        ImageView img;
        TextView title;
        TextView shopname;
        TextView price;
        TextView months_sales;
        CheckBox checkBox;
    }
}