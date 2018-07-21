package com.example.xiangmu.main_layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xiangmu.Ip;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.Log_Regist_Forget.PhoneLoginActivity;
import com.example.xiangmu.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class alter_phone1 extends AppCompatActivity implements View.OnClickListener {
private EditText newphone;
private ImageView return3;
private EditText code_1;
private Button change_phone;
public int time=60;
public static final int CODEERROR=1;
public static final int SENDSUCCESS=2;
public static final int SUCCESS=3;
public static final int FAIL=4;
private Button send_code;
public String telephone;
public String getcode;
public String message;
Intent intent;
public ProgressDialog progressDialog;
public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_phone1);
        initView();
        initListener();
    }

    private void initListener() {
        send_code.setOnClickListener(this);
        change_phone.setOnClickListener(this);
        return3.setOnClickListener(this);
    }

    private void initView() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("正在发送中!");
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);

        dialog=new ProgressDialog(this);
        dialog.setTitle("正在修改中!");
        dialog.setMessage("Loading.....");
        dialog.setCancelable(false);

        newphone=findViewById(R.id.new_telephone);
        code_1=findViewById(R.id.code1);
        return3=findViewById(R.id.return3);
        change_phone=findViewById(R.id.change_phone);
        send_code=findViewById(R.id.send_code);
    }
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODEERROR:
                    progressDialog.dismiss();
                    Toast.makeText(alter_phone1.this, "验证码发送失败！请重新发送!", Toast.LENGTH_SHORT).show();
                    break;
                case SENDSUCCESS:
                    progressDialog.dismiss();
                    Toast.makeText(alter_phone1.this, "验证码发送成功!", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    dialog.dismiss();
                    Toast.makeText(alter_phone1.this, "修改成功!", Toast.LENGTH_SHORT).show();
                    intent=new Intent(alter_phone1.this,alter_phone.class);
                    startActivity(intent);
                    break;
                case FAIL:
                    dialog.dismiss();
                    Toast.makeText(alter_phone1.this, "修改失败!请重新修改!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.send_code:
                telephone=newphone.getText().toString();
                progressDialog.show();
                if (telephone.equals("")) {
                    //progressDialog.dismiss();
                    Toast.makeText(this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
                }
                if (null == telephone || "".equals(telephone) ||telephone.length() != 11) {
                  //  progressDialog.dismiss();
                    Toast.makeText(this, "电话号码输入有误,请重新输入!", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        OkHttpClient client = new OkHttpClient();

                        RequestBody requestBody = new FormBody.Builder()
                                .add("phone", telephone)
                                .build();

                        Request request = new Request.Builder()
                                .url("http://" + Ip.ip + ":8080/project/SendCodeServlet")
                                .post(requestBody)
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            Log.d("reaponse:", "run: " + response);
                            getcode= response.body().string();
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
                                    send_code.setClickable(false);
                                    send_code.setText("重新发送("+(time-1)+ ")");
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
                                send_code.setClickable(true);
                                send_code.setText("重新发送");
                            }
                        });
                        time = 60; //最后再恢复倒计时时长
                    }
                }).start();
                break;
            case R.id.change_phone:
                dialog.show();
                changephone();
                break;
            case R.id. return3:
               intent=new Intent(alter_phone1.this,alter_phone.class);
               startActivity(intent);
               overridePendingTransition(R.anim.dong, R.anim.dong1);//动画
               break;
        }
    }

    private void changephone() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("userid", MainActivity.userid)
                        .add("phone", telephone)
                        .add("userpassword", MainActivity.password1)
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
