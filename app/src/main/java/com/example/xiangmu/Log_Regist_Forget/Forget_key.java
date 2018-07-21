package com.example.xiangmu.Log_Regist_Forget;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xiangmu.Ip;
import com.example.xiangmu.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Forget_key extends AppCompatActivity implements View.OnClickListener {

    private EditText phone_number_1;
    private EditText messsage3;
    private EditText changekey_2;
    private Button sendmessage_1;
    private EditText change_key;
    private Button cancel3;
    private Button change;
    private String strPhoneNumber;
    private String changekey;
    private String changekey2;
    private ProgressDialog progressDialog;
    private ProgressDialog dialog;
    public String message;
    String strCode;
    public int time=60;
    public static final int SUCCESS=4;
    public static final int FAIL=5;
    public static final int ERROR=6;
    public static final int CODEERROR=1;
    public static final int SENDSUCCESS=2;

    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forget_key);
            initView();
            initListener();
        }

    private void initListener() {
        sendmessage_1.setOnClickListener(this);
        change.setOnClickListener(this);
        cancel3.setOnClickListener(this);
    }

    private void initView() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("正在修改中！");
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);

        dialog=new ProgressDialog(this);
        dialog.setTitle("正在发送中！");
        dialog.setMessage("Loading.....");
        dialog.setCancelable(false);

        phone_number_1=findViewById(R.id.phone_number_1);
        changekey_2=findViewById(R.id.change_key2);
        messsage3=findViewById(R.id.message_3);
        sendmessage_1=findViewById(R.id.send_message1);
        cancel3=findViewById(R.id.cancel_3);
        change_key=findViewById(R.id.change_key);
        change=findViewById(R.id.change);
    }
/*
异常处理服务器返回值
 */
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    progressDialog.dismiss();
                    Toast.makeText(Forget_key.this, "修改密码成功!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Forget_key.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case FAIL:
                    progressDialog.dismiss();
                    Toast.makeText(Forget_key.this, "这个手机号未被注册过!无法修改密码!请重新修改!", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    Toast.makeText(Forget_key.this, "网络错误!", Toast.LENGTH_SHORT).show();
                    break;
                case CODEERROR:
                    dialog.dismiss();
                    Toast.makeText(Forget_key.this, "验证码发送失败！请重新发送!", Toast.LENGTH_SHORT).show();
                    break;

                case SENDSUCCESS:
                    dialog.dismiss();
                    Toast.makeText(Forget_key.this, "验证码发送成功!", Toast.LENGTH_SHORT).show();
                    break;
            }
            }

            };
    /*
    发送请求到后台
     */
    private void jiaohu() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("phone", strPhoneNumber)
                        .add("userpassword", changekey)
                        .build();

                Request request = new Request.Builder()
                        .url("http://"+ Ip.ip+":8080/project/ForgetPassWord")
                        .post(requestBody)
                        .build();
                Response response=null;

                try {
                    response = client.newCall(request).execute();
                    String  result1 = response.body().string();

                    Log.d("response", "handleMessage: "+response);
                    Log.d("result1", "handleMessage: "+result1);
                    if(response!=null&&response.isSuccessful()) {
                        if (result1.equals("success")) {
                        myHandler.sendEmptyMessage(SUCCESS);
                        }
                        else if (result1.equals("fail")){
                            myHandler.sendEmptyMessage(FAIL);
                        }
                    }else{
                        myHandler.sendEmptyMessage(ERROR);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message1:
                strPhoneNumber = phone_number_1.getText().toString();
                dialog.show();
                /*
                获取验证码
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        OkHttpClient client = new OkHttpClient();

                        RequestBody requestBody = new FormBody.Builder()
                                .add("phone", strPhoneNumber)
                                .build();

                        Request request = new Request.Builder()
                                .url("http://" + Ip.ip + ":8080/project/SendCodeServlet")
                                .post(requestBody)
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            Log.d("reaponse:", "run: " + response);
                            message = response.body().string();
                            Log.d("run:", "run: " + message);
                            if (response != null && response.isSuccessful()) {

                                myHandler.sendEmptyMessage(SENDSUCCESS);

                            } else {
                                myHandler.sendEmptyMessage(CODEERROR);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

        if (strPhoneNumber.equals("")) {
            progressDialog.dismiss();
            Toast.makeText(this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
        }
        if (null == strPhoneNumber || "".equals(strPhoneNumber) || strPhoneNumber.length() != 11) {
            progressDialog.dismiss();
            Toast.makeText(this, "电话号码输入有误,请重新输入!", Toast.LENGTH_SHORT).show();
            return;
        }
        //开启线程去更新button的text
      new Thread(new Runnable() {
          @Override
          public void run() {
              while (time > 0) {
                  myHandler.post(new Runnable() {
                      @Override
                      public void run() {
                          sendmessage_1.setClickable(false);
                          sendmessage_1.setText("重新发送("+(time-1)+ ")");
                      }
                  });
                  try {
                      Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  time--;
              }

              //倒计时结束，也就是循环结束
              myHandler.post(new Runnable() {
                  @Override
                  public void run() {
                      sendmessage_1.setClickable(true);
                      sendmessage_1.setText("重新发送");
                  }
              });
              time = 60; //最后再恢复倒计时时长
          }
      }).start();
        break;
        case R.id.change:
            strCode= messsage3.getText().toString();
        changekey=change_key.getText().toString();
        changekey2=changekey_2.getText().toString();

        if(changekey==null){
            progressDialog.dismiss();
            Toast.makeText(this, "密码不能为空!", Toast.LENGTH_SHORT).show();
        }else if(changekey.length()<6)
        {
            Toast.makeText(this, "密码长度不能少于6位!请重新输入!", Toast.LENGTH_SHORT).show();
        }
        if(!changekey2.equals(changekey))
        {
            Toast.makeText(this, "两遍密码输入的不一致!请重新输入!", Toast.LENGTH_SHORT).show();
        }
        if(strCode.equals(message)&&changekey2.equals(changekey)) {
            progressDialog.show();
            jiaohu();
        }
        else if (!strCode.equals(message)){
            Toast.makeText(this, "验证码输入错误!请重新输入!", Toast.LENGTH_SHORT).show();
        }
        break;

        case R.id.cancel_3:
        Intent intent=new Intent(Forget_key.this,MainActivity.class);
        startActivity(intent);
        break;
    }
}
}


