package com.example.xiangmu.main_layout;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xiangmu.Mall_navigation.mall_navigation;
import com.example.xiangmu.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class home_page extends Fragment implements View.OnClickListener {
    private ImageView voice;
    private ImageView voice1;
    private EditText input;
    private Dialog dialog;
    private View view;
    public ImageView cross1;
    Intent intent;
    private LinearLayout historical_price1;
    private LinearLayout mall_navigation1;
    private LinearLayout save_money1;
    private LinearLayout discount_coupon1;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    // 语音识别对象
//    private SpeechRecognizer recognizer;
//
//    //语音合成对象
//   // private SpeechSynthesizer speaker;
//
//    //识别出来的句子
//    StringBuilder sentence = null ;
//
//    //麦克风按钮
//    private ImageView startRecord ;


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
    }


    public void initListener() {
        voice.setOnClickListener(this);
        cross1.setOnClickListener(this);
        voice1.setOnClickListener(this);
        input.setFocusable(false);//让EditText失去焦点，然后获取点击事件
        input.setOnClickListener(this);
        mall_navigation1.setOnClickListener(this);
    }

    public void initView() {
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
        historical_price1=getActivity().findViewById(R.id.historical_price1);
        mall_navigation1=getActivity().findViewById(R.id.mall_navigation1);
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
                speak();
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

        }
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

    public void speak()
    {
        try{
            //通过Intent传递语音识别的模式，开启语音
            Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            //语言模式和自由模式的语音识别
            intent.putExtra(RecognizerIntent. EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            //提示语音开始
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "开始语音");
            //开始语音识别
            startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "找不到语音设备!", Toast.LENGTH_SHORT).show();
        }
    }

}
