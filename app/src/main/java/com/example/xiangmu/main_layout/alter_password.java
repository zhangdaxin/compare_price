package com.example.xiangmu.main_layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.xiangmu.Ip;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class alter_password extends AppCompatActivity implements View.OnClickListener {
private ImageView return5;
private TextView finish;
private EditText oldPassword;
private EditText newPassword;
public static final int SUCCESS=1;
public static final int FAIL=2;
public ProgressDialog dialog;
public String oldpassword;
public String newpassword;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_password);
        initView();
        initListener();
    }

    private void initListener() {
        return5.setOnClickListener(this);
        finish.setOnClickListener(this);
    }

    private void initView() {
        dialog=new ProgressDialog(this);
        dialog.setTitle("正在修改中!");
        dialog.setMessage("Loading.....");
        dialog.setCancelable(false);

        return5=findViewById(R.id.return5);
        finish=findViewById(R.id.finish);
        oldPassword=findViewById(R.id.oldpassword);
        newPassword=findViewById(R.id.newpassword);
    }
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case SUCCESS:
                    dialog.dismiss();
                    Toast.makeText(alter_password.this, "修改成功!", Toast.LENGTH_SHORT).show();
                    intent=new Intent(alter_password.this,manager_person.class);
                    startActivity(intent);
                    break;
                case FAIL:
                    dialog.dismiss();
                    Toast.makeText(alter_password.this, "修改失败!请重新修改!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.finish:
                oldpassword=oldPassword.getText().toString();
                newpassword=newPassword.getText().toString();
                dialog.show();
                if(!oldpassword.equals(MainActivity.password1))
                {
                    dialog.dismiss();
                    Toast.makeText(this, "旧密码输入错误!请重新输入!", Toast.LENGTH_SHORT).show();
                } else
                {
                    alterpsd();
                }if (newpassword.length()<6)
               {
                   dialog.dismiss();
                   Toast.makeText(this, "新密码的长度小于6位!", Toast.LENGTH_SHORT).show();
               }
                overridePendingTransition(R.anim.enter,R.anim.exit);
                break;
            case R.id.return5:
                intent=new Intent(alter_password.this,manager_person.class);
                startActivity(intent);
                overridePendingTransition(R.anim.dong, R.anim.dong1);
                break;
        }
    }
    public void alterpsd(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("userid", MainActivity.userid)
                        .add("phone", MainActivity.phone)
                        .add("userpassword", newpassword)
                        .add("username",MainActivity.username)
                        // add("image",MainActivity.image)
                        .add("sex", MainActivity.sex)
                        .build();

                Request request = new Request.Builder()
                        .url("http://" + Ip.ip + ":8080/project/UpdateUserdata")
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    Log.d("", "run: " + response);
                    String responseData = response.body().string();
                    Log.d("", "run: " + responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    if (response != null && response.isSuccessful()) {
                        MainActivity.userid = jsonObject.getString("userid");
                        MainActivity.id = Integer.parseInt(MainActivity.userid);
                        MainActivity.phone = jsonObject.getString("phone");
                        MainActivity.password1 = jsonObject.getString("userpassword");
                        MainActivity.username = jsonObject.getString("username");
                        MainActivity.image=jsonObject.getString("image");
                        MainActivity.sex = jsonObject.getString("sex");
                        myHandler.sendEmptyMessage(SUCCESS);
                    } else {
                        myHandler.sendEmptyMessage(FAIL);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

