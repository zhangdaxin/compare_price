package com.example.xiangmu.compare_price;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;
import com.example.xiangmu.Ip;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.R;
import java.io.IOException;
import java.util.List;
import com.example.xiangmu.middle_commodity.Spus;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class SpusAdapter extends ArrayAdapter<Spus> {
    public static final int SUCCESS=0;
    public static final int FAIL=1;
    public static final int ERROR=2;
    public int resourceid;
    private Intent intent;
    List<Spus> sp;
/*
适配器
 */
    public SpusAdapter(Context context,int resourse, List<Spus> s) {
        super(context,resourse,s);
        sp=s;
        resourceid=resourse;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View view;

        if (convertView == null) {
            view= LayoutInflater.from(getContext()).inflate(resourceid,parent,false);
            viewHolder = new ViewHolder();

            viewHolder.img=view.findViewById(R.id.pic_modes);
            viewHolder.introduction1=view.findViewById(R.id.introduction);
            viewHolder.shop1=view.findViewById(R.id.shop);
            viewHolder.price1=view.findViewById(R.id.price);
            viewHolder.months_sales=view.findViewById(R.id.months_sales);
            viewHolder.add_shopping_car=view.findViewById(R.id.add_to_shopping_car);
            viewHolder.go_to_buy=view.findViewById(R.id.go_to_buy);

            view.setTag(viewHolder);//将ViewHolder储存在View中
        } else {

            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        String url=sp.get(position).getPic_url();
        Log.d("", "getView: "+url);

        viewHolder.img.setTag(url);
        new ImageLoader().showImageByThead(viewHolder.img,url);
        viewHolder.introduction1.setText(sp.get(position).getTitle());
        viewHolder.shop1.setText(sp.get(position).getShop());
        viewHolder.price1.setText(String.valueOf(sp.get(position).getPrice()));
        viewHolder.months_sales.setText(sp.get(position).getMonth_sales());
        viewHolder.add_shopping_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client=new OkHttpClient();
                        RequestBody body=new FormBody.Builder()
                                .add("userid", MainActivity.userid)
                                .add("title",sp.get(position).getTitle())
                                .add("price",String.valueOf(sp.get(position).getPrice()))
                                .add("month_sales",sp.get(position).getMonth_sales())
                                .add("url",sp.get(position).getUrl())
                                .add("shop",sp.get(position).getShop())
                                .add("pic_url",sp.get(position).getPic_url())
                                .build();

                        Request request=new Request.Builder()
                                .url("http://"+ Ip.ip+":8080/project/SaveProduct")
                                .post(body)
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            String responseData=response.body().string();
                            if(response.isSuccessful())
                            {
                                if(responseData.equals("success"))
                                {
                                    handler.sendEmptyMessage(SUCCESS);
                                }else{
                                    handler.sendEmptyMessage(FAIL);
                                }
                            }else{
                                handler.sendEmptyMessage(ERROR);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(ERROR);
                        }
                    }
                }).start();
            }
        });
        viewHolder.go_to_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse(sp.get(position).getUrl()));//为Intent设置数据
                Log.d("", "onClick: "+sp.get(position).getUrl());
                getContext().startActivity(intent);//将Intent传递给Activity
            }
        });
        return view;
    }

          /*
          异步处理
           */
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case SUCCESS:
                    Toast.makeText(getContext(),"加入购物车成功!", Toast.LENGTH_SHORT).show();
                    break;
                case FAIL:
                    Toast.makeText(getContext(),"购物车已经有该商品了喔亲!", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    Toast.makeText(getContext(),"网络错误!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    class ViewHolder {
        ImageView img;
        TextView introduction1;
        TextView shop1;
        TextView price1;
        TextView months_sales;
        Button add_shopping_car;
        Button go_to_buy;
    }
}
