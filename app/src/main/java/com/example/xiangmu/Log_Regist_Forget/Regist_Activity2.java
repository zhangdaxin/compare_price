package com.example.xiangmu.Log_Regist_Forget;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xiangmu.Ip;
import com.example.xiangmu.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Regist_Activity2 extends Fragment implements View.OnClickListener {

    private EditText code;
    private Button regist;
    private Button cancel3;
    private ImageView iv_showCode;
    private EditText password1;
    private EditText password2;
    public String realCode;
    private ProgressDialog progressDialog;
    public static final int SUCCESS=0;
    public static final int FAIL=1;
    public static final int ERROR=2;
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
                    Toast.makeText(getActivity(), "注册成功!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    break;

                case FAIL:
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "该账号已经注册!注册失败!请重新注册!", Toast.LENGTH_SHORT).show();
                    replaceFragment(new Regist_Activity1());
                    break;

                case ERROR:
                    progressDialog.dismiss();
                    Toast.makeText(
                            getActivity(), "网络错误!", Toast.LENGTH_SHORT).show();
                    break;
            }
            }

    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_regist_2, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("正在注册中！");
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);

        password1=getActivity().findViewById(R.id.password_1);
        code=getActivity().findViewById(R.id.code);
        password2=getActivity().findViewById(R.id.password_2);
        iv_showCode = getActivity().findViewById(R.id.iv_showCode);
        //将验证码用图片的形式显示出来
        iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
        regist=getActivity().findViewById(R.id.regist_2);
        cancel3=getActivity().findViewById(R.id.cancel_3);
    }

    private void initListener() {
        iv_showCode.setOnClickListener(this);
        regist.setOnClickListener(this);
        cancel3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
        case R.id.iv_showCode:
        iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
        Log.v("realCode:","realCode"+realCode);
        break;
            case R.id.regist_2:
                        final String password_1 = password1.getText().toString();
                        String password_2 = password2.getText().toString();
                        String Code = code.getText().toString();

                        if(password_1.length()<6)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "密码长度不能低于6位！请重新输入!", Toast.LENGTH_SHORT).show();
                        }
                        else if (password_1.equals("")) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "密码不能为空!请重新输入!", Toast.LENGTH_SHORT).show();
                        } else if (password_2.equals("")) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "第二遍输入的密码不能为空!请重新输入!", Toast.LENGTH_SHORT).show();
                        } else if (!password_2.equals(password_1)) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "输入的两遍密码不一致!请重新输入!", Toast.LENGTH_SHORT).show();

                        } else if (Code.equalsIgnoreCase(realCode) && !Code.equals("") && password_1.equals(password_2) &&password_1.length()>=6) {
                            progressDialog.show();
                            // Toast.makeText(this, "注册成功!", Toast.LENGTH_SHORT).show();
//                JSONObject jsonObject=new JSONObject();
//                try {
//                    jsonObject.put("username",username);
//                    jsonObject.put("password1",password_1);
//                    jsonObject.put("password2",password_2);
//                    jsonObject.put("code",Code);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                JSON.post("", jsonObject.toString(), new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//
//                    }
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                    String responData=response.body().string();
//                        JSONObject jsonObject1= null;
//                        try {
//                            jsonObject1 = new JSONObject(responData);
//                            String result=jsonObject1.getString("");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    OkHttpClient client = new OkHttpClient();

                                    RequestBody requestBody = new FormBody.Builder()
                                            .add("phone", Regist_Activity1.phonenumber1)
                                            .add("userpassword", password_1)
                                            .build();

                                    Request request = new Request.Builder()
                                            .url("http://"+ Ip.ip+":8080/project/RegisterServlet")
                                            .post(requestBody)
                                            .build();

                                    try {
                                        Response response = client.newCall(request).execute();
                                        Log.d("response", "onClick: " + response);
                                        if (response != null && response.isSuccessful()) {
                                            String result = response.body().string();
                                            Log.d("result", "onClick: " + result);
                                            //  String result="success";
                                            if (result.equalsIgnoreCase("success")) {
                                                handler.sendEmptyMessage(SUCCESS);
                                            } else if (result.equalsIgnoreCase("fail")) {
                                                handler.sendEmptyMessage(FAIL);
                                            }
                                        }else {
                                            handler.sendEmptyMessage(ERROR);
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    //}
//                });
                                }
                            }).start();

                        } else if (Code.equals(""))
                        {
                            Toast.makeText(getActivity(), "验证码不能为空!", Toast.LENGTH_SHORT).show();
                        } else
                        {
                            Toast.makeText(getActivity(), "验证码错误!请重新输入!", Toast.LENGTH_SHORT).show();
                        }
                        break;

            case R.id.cancel_3:
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                break;
    }
}
/*
碎片
 */
public void replaceFragment(Fragment fragment)
{
    FragmentManager fragmentManager=getFragmentManager();
    FragmentTransaction transaction=fragmentManager.beginTransaction();
    transaction.replace(R.id.FrameLayout,fragment);
    transaction.commit();
}
}
