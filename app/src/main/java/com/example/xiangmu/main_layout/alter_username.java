package com.example.xiangmu.main_layout;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.xiangmu.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class alter_username extends AppCompatActivity implements View.OnClickListener {
private Button save;
private EditText username;
private ImageView return4;
private Intent intent;
private ProgressDialog dialog;
public static String username_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_username);
        initView();
        initListener();
    }

    private void initListener() {
    save.setOnClickListener(this);
    return4.setOnClickListener(this);
    }

    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("正在修改中！");
        dialog.setMessage("Loading.....");
        dialog.setCancelable(false);

        return4=findViewById(R.id.return4);
        save=findViewById(R.id.save_1);
        username=findViewById(R.id.username_1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.save_1:
                username_2=username.getText().toString();
                interaction();
                dialog.show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "保存成功!", Toast.LENGTH_SHORT).show();
              //  dialog.dismiss();
                intent = new Intent(alter_username.this, manager_person.class);
                startActivity(intent);
                overridePendingTransition(R.anim.dong, R.anim.dong1);
                break;
            case R.id.return4:
                intent =new Intent(alter_username.this,manager_person.class);
                startActivity(intent);
                overridePendingTransition(R.anim.dong, R.anim.dong1);
            break;
        }
    }
    public void interaction() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("userid", MainActivity.userid)
                        .add("phone", MainActivity.phone)
                        .add("userpassword", MainActivity.password1)
                        .add("username",username_2)
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
                    } else {
                           Toast.makeText(alter_username.this, "更改失败!", Toast.LENGTH_SHORT).show();
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
