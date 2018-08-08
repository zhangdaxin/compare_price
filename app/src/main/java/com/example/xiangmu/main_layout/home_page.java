package com.example.xiangmu.main_layout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.example.xiangmu.JsonParser;
import com.example.xiangmu.Mall_navigation.mall_navigation;
import com.example.xiangmu.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import static android.app.Activity.RESULT_OK;


public class home_page extends Fragment implements View.OnClickListener {
    private ImageView voice;
    private ImageView voice1;
    private EditText input;
    private Dialog dialog;
    private View view;
    public ImageView cross1;
    Intent intent;
    private LinearLayout save_9kuai9;
    private LinearLayout mall_navigation1;
    private LinearLayout save_money1;
    private LinearLayout discount_coupon1;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    public ListView listView;
    public List<home_modes> hm=new ArrayList<home_modes>();
    public static final int SUCCESS=0;
    public home_modes_adapter adapter;
    ProgressDialog progressDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String , String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_home_page,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
        initSpeech();
        progressDialog.show();

        adapter = new home_modes_adapter(getContext(),R.layout.activity_home_modes, hm);
        listView.setAdapter(adapter);
        update();
        hm.clear();
    }
    public void update()
    {
        getHome_modes(handler);
    }
    public void initListener() {
        voice.setOnClickListener(this);
        cross1.setOnClickListener(this);
        voice1.setOnClickListener(this);
        input.setFocusable(false);//让EditText失去焦点，然后获取点击事件
        input.setOnClickListener(this);
        mall_navigation1.setOnClickListener(this);
        discount_coupon1.setOnClickListener(this);
        save_money1.setOnClickListener(this);
        save_9kuai9.setOnClickListener(this);
    }
    private void initSpeech() {
        // 将“12345678”替换成您申请的 APPID，申请地址： http://www.xfyun.cn
        // 请勿在 “ =”与 appid 之间添加任务空字符或者转义符
        SpeechUtility. createUtility(getActivity(), SpeechConstant. APPID + "=5b559918" );
    }
    public void initView() {
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("加载中");
        progressDialog.setMessage("请稍后...");
        progressDialog.setCancelable(false);


        dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_NoActionBar);
        //填充对话框的布局
        view = LayoutInflater.from(getActivity()).inflate(R.layout.voice_dialog, null);
        //初始化控件
//        saveUri = Uri.fromFile(new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DCIM),"1.jpg"));
        input=getActivity().findViewById(R.id.input_text_cp);
        voice=getActivity().findViewById(R.id.voice);
        cross1=view.findViewById(R.id.cross);
        voice1=view.findViewById(R.id.voice1);
        discount_coupon1=getActivity().findViewById(R.id.discount_coupon1);
        save_money1=getActivity().findViewById(R.id.save_money1);
        save_9kuai9=getActivity().findViewById(R.id.save_9kuai9);
        mall_navigation1=getActivity().findViewById(R.id.mall_navigation1);
        listView=getActivity().findViewById(R.id.home_modes_list);
    }

    public void show(View view) {
        //将布局设置给Dialog
        dialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框m
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.voice:
                show(view);
                break;
            case R.id.cross:
                dialog.dismiss();
                break;
            case R.id.voice1:
                startSpeechDialog();
                break;
            case R.id.input_text_cp:
                main_layout.choose=2;
                intent=new Intent(getActivity(),main_layout.class);
                startActivity(intent);
                break;
            case R.id.mall_navigation1:
                intent =new Intent(getActivity(),mall_navigation.class);
                startActivity(intent);
                break;
            case R.id.discount_coupon1:
                main_layout.choose=5;
                intent=new Intent(getActivity(),main_layout.class);
                startActivity(intent);
                break;
            case R.id.save_money1:
                 intent=new Intent(getActivity(),Search_quan.class);
                 startActivity(intent);
                 break;
            case R.id.save_9kuai9:
                intent=new Intent(getActivity(),Save_9kuai9_main.class);
                startActivity(intent);
                break;
        }
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
                    progressDialog.dismiss();
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private void getHome_modes(final Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://www.yayams.com/jiu/")
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    dealwith(handler,responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void dealwith(Handler handler,String html) {
        String count=null;
        String url1=null;
        String pic_url = null;
        String new_price=null;
        String title=null;
        String old_price=null;
        String quan=null;

        org.jsoup.nodes.Document document = Jsoup.parse(html);
        Elements elements = document.select("div[class=list-good buy]");
        Log.d("", "dealwith: "+elements);
        for (org.jsoup.nodes.Element element : elements) {
            //   销量  领券前的价钱 领券后的价钱
            Elements elementsByClass = element.getElementsByClass("good-price");
            for (org.jsoup.nodes.Element element2 : elementsByClass) {
                new_price=element2.getElementsByClass("price-current").text();
                new_price=new_price+"领券后";
                old_price=element2.getElementsByClass("price-old").text();
            }
            //   title
            Elements elementsByClass1 = element.getElementsByClass("good-title");
            for (org.jsoup.nodes.Element element2 : elementsByClass1) {
                title= element2.getElementsByTag("a").text();
                count = element2.getElementsByClass("sold").text();
            }

            //   pic_url
            Elements elementsByClass2 = element.getElementsByClass("good-pic");
            for (org.jsoup.nodes.Element element2 : elementsByClass2) {
                pic_url= element2.getElementsByTag("img").attr("d-src");
            }
            //url    券
            Elements elementsByClass3 = element.getElementsByClass("lingquan");
            quan=element.getElementsByClass("lingquan").text();
            quan=quan.substring(quan.indexOf("省")+1,quan.indexOf("元"));
            for (org.jsoup.nodes.Element element2 : elementsByClass3) {
                url1= element2.getElementsByTag("a").attr("href");
                url1="https://www.yayams.com"+url1;
            }
            home_modes h= new home_modes(pic_url,title,new_price,old_price,"券￥"+quan, count+"件", url1);
            hm.add(h);
        }
        handler.sendEmptyMessage(0);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        //回调获取从谷歌得到的数据
        if(requestCode==VOICE_RECOGNITION_REQUEST_CODE && resultCode==RESULT_OK){
            //取得语音的字符
            ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //谷歌可能有许多语音类似的返回，越往上优先级越高，这里列出所有的返回并拼接成字符串
            String resultString="";
            for(int i=0;i<results.size();i++){
                resultString+=results.get(i);
            }
            Toast.makeText(getActivity(), ""+resultString+"", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startSpeechDialog() {
        //1. 创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(getActivity(), new MyInitListener()) ;
        //2. 设置accent、 language等参数
        mDialog.setParameter(SpeechConstant. LANGUAGE, "zh_cn" );// 设置中文
        mDialog.setParameter(SpeechConstant. ACCENT, "mandarin" );
        // 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后 onResult回调返回将是语义理解
        // 结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener( new MyRecognizerDialogListener()) ;
        //4. 显示dialog，接收语音输入
        mDialog.show() ;
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener {

        /**
         * @param results
         * @param isLast  是否说完了
         */
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            String result = results.getResultString(); //为解析的
           // showTip(result) ;
            System. out.println(" 没有解析的 :" + result);

            String text = JsonParser.parseIatResult(result) ;//解析过后的
            System. out.println(" 解析后的 :" + text);

            String sn = null;
            // 读取json结果中的 sn字段
            try {
                JSONObject resultJson = new JSONObject(results.getResultString()) ;
                sn = resultJson.optString("sn" );
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIatResults .put(sn, text) ;//没有得到一句，添加到

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults .get(key));
            }
            SearchActivity.searchkeyword=resultBuffer.toString();
            if(SearchActivity.searchkeyword==null)
            {
                Toast.makeText(getActivity(), "你没有发出声音", Toast.LENGTH_SHORT).show();
            }else {
                intent = new Intent(getActivity(), SearchActivity.class);

                startActivity(intent);
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    }

    class MyInitListener implements InitListener {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败 ");
            }
        }
    }


    private void showTip (String data) {
        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show() ;
    }
}
