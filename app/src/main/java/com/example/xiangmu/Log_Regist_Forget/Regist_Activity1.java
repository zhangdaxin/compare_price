package com.example.xiangmu.Log_Regist_Forget;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xiangmu.Ip;
import com.example.xiangmu.R;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Regist_Activity1 extends Fragment implements View.OnClickListener {
    private Button sendmessage3;
    private Button next_1;
    private EditText phonenumber3;
    private EditText message_4;
    public String message;
    public static String phonenumber1;
    public String messsage4;
    public int time=60;
    private ProgressDialog progressDialog;
    private ProgressDialog dialog;
    public static final int CODEERROR=1;
    public static final int SENDSUCCESS=2;
    public static final int ERROR=3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_regist_1, container, false);
        return view;
    }
   /*
   碎片中的活动创建
    */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("正在注册中！");
        progressDialog.setMessage("Loading.....");
        progressDialog.setCancelable(false);

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("正在发送中！");
        dialog.setMessage("Loading.....");
        dialog.setCancelable(false);

        sendmessage3 = getActivity().findViewById(R.id.send_message_3);
        message_4 = getActivity().findViewById(R.id.message_4);
        phonenumber3 = getActivity().findViewById(R.id.phone_number_3);
        next_1 = getActivity().findViewById(R.id.next);
    }

    private void initListener() {
        sendmessage3.setOnClickListener(this);
        next_1.setOnClickListener(this);
    }
    /*
    点击触发事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message_3:
                phonenumber1 = phonenumber3.getText().toString();
                dialog.show();
                Log.d("phone", "onClick: " + phonenumber1);
                if (phonenumber1.equals("")) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "手机号不能为空!", Toast.LENGTH_SHORT).show();
                }
                if (null == phonenumber1 || "".equals(phonenumber1) || phonenumber1.length() != 11) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "电话号码输入有误,请重新输入!", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*
                发送请求让后台发验证码
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        OkHttpClient client = new OkHttpClient();

                        RequestBody requestBody = new FormBody.Builder()
                                .add("phone", phonenumber1)
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
                            myHandler.sendEmptyMessage(ERROR);
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
                                    sendmessage3.setClickable(false);
                                    sendmessage3.setText("重新发送("+(time-1)+ ")");
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
                                sendmessage3.setClickable(true);
                                sendmessage3.setText("重新发送");
                            }
                        });
                        time = 60; //最后再恢复倒计时时长
                    }
                }).start();
                break;
            case R.id.next:
                messsage4 = message_4.getText().toString();
                if (messsage4.equals(message)) {
                    replaceFragment(new Regist_Activity2());
                } else {
                    Toast.makeText(getActivity(), "验证码错误,不能继续注册!请重新获取!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR:
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "网络错误!", Toast.LENGTH_SHORT).show();
                    break;
                case CODEERROR:
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "验证码发送失败！请重新发送!", Toast.LENGTH_SHORT).show();
                    break;
                case SENDSUCCESS:
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "验证码发送成功!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
           /*
           碎片
           */
         private void replaceFragment(Fragment fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.FrameLayout, fragment);
            transaction.commit();
        }
    }

