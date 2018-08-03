package com.example.xiangmu.main_layout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiangmu.Ip;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.R;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Commit_suggestion extends AppCompatActivity implements View.OnClickListener {
    private EditText suggestion;
    private ImageView return12;
    private TextView commit;
    private LinearLayout type_suggestion;
    public String suggestion_1;
    public String type="功能意见";
    private Intent intent;
    private TextView show_type;
    String responseData;
    public static final int SUCCESS=0;
    public static final int FAIL=1;
    public static final int ERROR=2;
    public ProgressDialog d;
    private String[] items = {"功能意见", "界面建议", "你的新需求", "操作意见", "搜索反馈"};
//    @Override
//    protected void onDestroy() {
//        if(dialog != null) {
//            dialog.dismiss();
//        }
//        super.onDestroy();
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_suggestion);
        initView();
        initListener();
    }

    private void initView() {
        d=new ProgressDialog(this);
        d.setTitle("正在提交...");
        d.setMessage("请稍后!");
        d.setCancelable(false);

        type_suggestion = findViewById(R.id.type_suggestion);
        commit = findViewById(R.id.commit_s);
        suggestion = findViewById(R.id.suggestion);
        return12 = findViewById(R.id.return12);
        show_type = findViewById(R.id.show_type);
    }

    private void initListener() {
        return12.setOnClickListener(this);
        commit.setOnClickListener(this);
        type_suggestion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return12:
                main_layout.choose=4;
                intent=new Intent(Commit_suggestion.this,main_layout.class);
                startActivity(intent);
                overridePendingTransition(R.anim.dong, R.anim.dong1);
                break;
            case R.id.commit_s:
                d.show();
                suggestion_1=suggestion.getText().toString();
                commit();
                break;
            case R.id.type_suggestion:
                show();
                break;
        }
    }

    private void commit() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                RequestBody body=new FormBody.Builder()
                        .add("userid", MainActivity.userid)
                        .add("sugg",suggestion_1)
                        .add("category",type)
                        .build();

                Request request=new Request.Builder()
                        .url("http://"+ Ip.ip+":8080/project/SaveSuggest")
                        .post(body)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();
                    if (response.isSuccessful() && response != null) {
                        if (responseData.equals("success")) {
                        handler.sendEmptyMessage(SUCCESS);
                        } else {
                        handler.sendEmptyMessage(FAIL);
                        }
                    }else{
                        handler.sendEmptyMessage(FAIL);
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(FAIL);
                }

            }
        }).start();
    }
    /*
      异常处理
       */
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case SUCCESS:
                    suggestion.setText("");
                    d.dismiss();
                    Toast.makeText(Commit_suggestion.this, "提交成功!感谢你的反馈!", Toast.LENGTH_SHORT).show();
                    break;
                case FAIL:
                    d.dismiss();
                    Toast.makeText(Commit_suggestion.this, "提交失败!请重试!", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    d.dismiss();
                    Toast.makeText(Commit_suggestion.this, "网络错误!请重试!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
                type=items[which];
                show_type.setText(items[which]);
                dialog.dismiss();
    }
});
        builder.create().show();
    }
}
