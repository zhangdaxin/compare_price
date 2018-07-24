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
import com.example.xiangmu.main_layout.main_layout;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class PhoneLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText phone_number;
    private EditText messsage2;
    private Button sendmessage;
    private Button login_2;
    private Button cancel_2;
    private String strPhoneNumber;
    private String responseData;
    private ProgressDialog progressDialog;
    private ProgressDialog dialog;
    public int time=60;
    public static final int SUCCESS = 3;
    public static final int FAIL = 4;
    public static final int ERROR = 5;
    String messsage_2;
    String message;
    public static final int CODEERROR=1;
    public static final int SENDSUCCESS=2;
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        initView();
        initListener();
    }

    /*
    创建点击事件的监听
     */
    private void initListener() {
        sendmessage.setOnClickListener(this);
        login_2.setOnClickListener(this);
        cancel_2.setOnClickListener(this);
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("正在登录中！");
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);

        dialog = new ProgressDialog(this);
        dialog.setTitle("正在发送中！");
        dialog.setMessage("Loading.....");
        dialog.setCancelable(false);

        phone_number = findViewById(R.id.phone_number1);
        messsage2 = findViewById(R.id.message_2);
        sendmessage = findViewById(R.id.send_message);
        login_2 = findViewById(R.id.login_2);
        cancel_2 = findViewById(R.id.cancel_2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message:
                strPhoneNumber = phone_number.getText().toString();
                dialog.show();
                if (strPhoneNumber.equals("")) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
                }
                if (null == strPhoneNumber || "".equals(strPhoneNumber) || strPhoneNumber.length() != 11) {
                    progressDialog.dismiss();
                    Toast.makeText(this, "电话号码输入有误,请重新输入!", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*
                获取后台的验证码
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
                            message= response.body().string();
                            Log.d("run:", "run: " + message);

                            if (response != null && response.isSuccessful()) {

                                    myHandler.sendEmptyMessage(SENDSUCCESS);

                            } else {
                            myHandler.sendEmptyMessage(CODEERROR);
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (time > 0) {
                            myHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sendmessage.setClickable(false);
                                    sendmessage.setText("重新发送("+(time-1)+ ")");
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
                                sendmessage.setClickable(true);
                                sendmessage.setText("重新发送");
                            }
                        });
                        time = 60; //最后再恢复倒计时时长
                    }
                }).start();
                            break;
                            case R.id.login_2:
                                messsage_2=messsage2.getText().toString();

                                if (messsage_2.equals(message))
                                {
                                    progressDialog.show();
                                jiaohu();
                                }else{
                                    Toast.makeText(this, "验证码输入错误!请重新输入!", Toast.LENGTH_SHORT).show();
                                }
                                break;

                            case R.id.cancel_2:
                                Intent intent = new Intent(PhoneLoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                        }
        }
   /*
   异常处理
    */
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                        case SUCCESS:
                            progressDialog.dismiss();
                            Toast.makeText(PhoneLoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PhoneLoginActivity.this, main_layout.class);
                            startActivity(intent);
                            break;
                        case FAIL:
                            progressDialog.dismiss();
                            Toast.makeText(PhoneLoginActivity.this, "登录失败!请重新登录!", Toast.LENGTH_SHORT).show();
                            break;
                        case ERROR:
                            progressDialog.dismiss();
                            Toast.makeText(PhoneLoginActivity.this, "网络错误!", Toast.LENGTH_SHORT).show();
                            break;
                case CODEERROR:
                    dialog.dismiss();
                    Toast.makeText(PhoneLoginActivity.this, "验证码发送失败！请重新发送!", Toast.LENGTH_SHORT).show();
                    break;
                case SENDSUCCESS:
                    dialog.dismiss();
                    Toast.makeText(PhoneLoginActivity.this, "验证码发送成功!", Toast.LENGTH_SHORT).show();
                    }
            }

    };
   /*
   登录 从后台获取用户的数据
    */
        private void jiaohu() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();

                    RequestBody requestBody = new FormBody.Builder()
                            .add("phone", strPhoneNumber)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://"+Ip.ip+":8080/project/VCodeLogin")
                            .post(requestBody)
                            .build();
                    Response response = null;
                    try {
                        response = client.newCall(request).execute();
                        responseData = response.body().string();
                        Log.d("response：", "run: " + response);

                        if (response != null && response.isSuccessful()) {
                            JSONObject jsonObject=new JSONObject(responseData);
                            MainActivity.userid=jsonObject.getString("userid");
                            Log.d("userid:", "run: "+MainActivity.id);
                            MainActivity.phone=jsonObject.getString("phone");
                            Log.d("phone:", "run: "+MainActivity.phone);
                           // MainActivity.id=Integer.parseInt(userid);
                            Log.d("id:", "run: "+MainActivity.id);
                            MainActivity.password1=jsonObject.getString("userpassword");

                            MainActivity.username=jsonObject.getString("username");

                            MainActivity.sex=jsonObject.getString("sex");

                            MainActivity.image=jsonObject.getString("image");

                            Log.d("image", "run: "+MainActivity.image);

                            if (MainActivity.id!=0||!MainActivity.phone.equals("")) {
                                myHandler.sendEmptyMessage(SUCCESS);
                            } else  {
                                myHandler.sendEmptyMessage(FAIL);
                            }
                        } else {
                            myHandler.sendEmptyMessage(ERROR);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        myHandler.sendEmptyMessage(ERROR);
                    }
                }
            }).start();
        }

}



