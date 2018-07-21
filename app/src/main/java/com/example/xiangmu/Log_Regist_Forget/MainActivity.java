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
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiangmu.Ip;
import com.example.xiangmu.R;
import com.example.xiangmu.main_layout.main_layout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getName();
    private EditText phone1;
    private EditText password;
    private TextView message1;
    private Button login;
    private TextView regist;
    public String ip;
    private ProgressDialog progressDialog;
    public static final int SUCCESS=0;
    public static final int FAIL=1;
    public static final int ERROR=2;
    private TextView forget_key;
    public static int id;
    public static String phone;
    public static String password1;
    public static String sex;
    public static String userid;
    public static String username;
    public static String image;
    Intent intent;
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
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,main_layout.class);
                    startActivity(intent);
                    break;
                case FAIL:
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "手机号或密码错误!登录失败!请重新登录!", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "网络错误!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initset();
    }
     /*
     创建点击事件
      */
    private void initset() {
        login.setOnClickListener(this);
        regist.setOnClickListener(this);
        message1.setOnClickListener(this);
        forget_key.setOnClickListener(this);
    }

    private void initview() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("正在登录中！");
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);
       // progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable());

//        iv_showCode=findViewById(R.id.iv_showCode);
//        //将验证码用图片的形式显示出来
//        iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
//        realCode = Code.getInstance().getCode().toLowerCase();
//        Log.d("readCode", "initView: "+realCode);

        phone1=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login_1);
        regist=findViewById(R.id.regist_1);
        message1=findViewById(R.id.message_1);
        forget_key=findViewById(R.id.forget_key);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_1:
                Intent intent=new Intent(MainActivity.this,main_layout.class);
                startActivity(intent);

                progressDialog.show();
                final String lg_phone=phone1.getText().toString();
                final String lg_password=password.getText().toString();
                if(lg_password.length()<6)
                {
                    progressDialog.dismiss();
                    Toast.makeText(this, "密码长度不能少于6位！请重新输入!", Toast.LENGTH_SHORT).show();
                }

                if(lg_phone==null)
                {
                    progressDialog.dismiss();
                    Toast.makeText(this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
                }else if(lg_password==null)
                {
                    progressDialog.dismiss();
                    Toast.makeText(this, "密码不能为空1", Toast.LENGTH_SHORT).show();
                }
                else if(lg_phone.length()==11&&lg_password.length()>=6) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            OkHttpClient client = new OkHttpClient();

                            RequestBody requestBody = new FormBody.Builder()
                                    .add("phone", lg_phone)
                                    .add("userpassword", lg_password)
                                    .build();

                            Request request = new Request.Builder()
                                    .url("http://"+ Ip.ip+":8080/project/UserLoginServlet")
                                    .post(requestBody)
                                    .build();

                            try {
                                Response response = client.newCall(request).execute();
                                Log.d(TAG, "run: "+response);
                                String responseData = response.body().string();
                                Log.d(TAG, "run: "+responseData);
                                if (response != null && response.isSuccessful()) {

                                    JSONObject jsonObject=new JSONObject(responseData);
                                    userid=jsonObject.getString("userid");
                                    id=Integer.parseInt(userid);
                                    phone=jsonObject.getString("phone");
                                    if (id!=0||!phone.equals("")) {
                                        password1=jsonObject.getString("userpassword");

                                        username=jsonObject.getString("username");

                                        sex=jsonObject.getString("sex");

                                        image=jsonObject.getString("image");

                                        Log.d(TAG, "zhang: "+username+" "+sex+" "+password1);

                                        handler.sendEmptyMessage(SUCCESS);
                                    } else if(id==0||phone.equals("")){

                                        handler.sendEmptyMessage(FAIL);
                                    }
                                } else {
                                    handler.sendEmptyMessage(ERROR);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }).start();
                }else if(lg_phone.length()<11)
                {
                    progressDialog.dismiss();
                    Toast.makeText(this, "手机号码输入错误!请重新输入!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.forget_key:
                 intent=new Intent(MainActivity.this,Forget_key.class);
                 startActivity(intent);
                break;
            case R.id.regist_1:
                intent = new Intent(MainActivity.this, Regist_Activity.class);
                startActivity(intent);
                break;
            case R.id.message_1:
                intent = new Intent(MainActivity.this, PhoneLoginActivity.class);
                startActivity(intent);
        }
    }

}
//                JSONObject jsonObject=new JSONObject();
//                try {
//                    jsonObject.put("username",lg_username);
//                    jsonObject.put("password",lg_password);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                JSON.post("", jsonObject.toString(), new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//String result = response.body().string();