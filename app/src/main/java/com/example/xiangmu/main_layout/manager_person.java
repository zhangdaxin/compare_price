package com.example.xiangmu.main_layout;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiangmu.FileUtilcll;
import com.example.xiangmu.Getimageid;
import com.example.xiangmu.Ip;
import com.example.xiangmu.Log_Regist_Forget.MainActivity;
import com.example.xiangmu.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class manager_person extends AppCompatActivity implements View.OnClickListener {
    private View view;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;
    Uri saveUri;
    private ImageView return1;
    private TextView settting;
    private TextView user_name;
    private LinearLayout username;
    private LinearLayout alter_password;
    private LinearLayout headcard;
    private LinearLayout sex1;
    private TextView sex;
    private TextView username_1;
    private LinearLayout telephone;

    private Intent intent;
    private ImageView headView;
    private TextView telephone1;
    private Bitmap iBitmap;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static final int CHOOSE_PICTURE = 0;
    public  Uri tempUri;
    public  Bitmap bit;
    private Uri imageUri;
    public  Bitmap bit1;
    public static final int SAVESUCCESS=5;
    public static final int SAVEFAIL=6;
    public static final int SUCCESS = 3;
    public static final int FAIL = 4;
    public String path;
    public Button exit;
    public ProgressDialog progressDialog;
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10*1000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_person);
        initView();
        initListener();
        initMessage();
    }

    private void initMessage() {
//        Getimageid.interaction();
        if (MainActivity.username.equals("null")) {
            username_1.setText("未命名");
        } else {
            username_1.setText(MainActivity.username);
        }
        if (MainActivity.sex.equals("null")) {
            sex.setText("");
        } else {
            sex.setText(MainActivity.sex);
        }
        if (MainActivity.phone.equals("null")) {
            telephone1.setText("");
        } else {
            telephone1.setText(MainActivity.phone);
        }
        if (!MainActivity.image.equals("null")) {
                Log.d("", "getImage: " + MainActivity.image);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                            try {
                                HttpURLConnection conn = (HttpURLConnection) new URL(MainActivity.image).openConnection();
                                conn.setConnectTimeout(5000);
                                conn.setRequestMethod("GET");
                                System.out.println("tdw1");
                                if (conn.getResponseCode() == 200) {
                                    InputStream inputStream = conn.getInputStream();
                                    Log.d(TAG, "getImage: " + inputStream);
                                    bit1= BitmapFactory.decodeStream(inputStream);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            headView.setImageBitmap(bit1);
                                        }
                                    });
                                }
                            } catch (ProtocolException e) {
                                e.printStackTrace();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }
                }).start();
            }else{

        }

        }

    private void initListener() {
        return1.setOnClickListener(this);
        headcard.setOnClickListener(this);
        username.setOnClickListener(this);
        sex1.setOnClickListener(this);
        telephone.setOnClickListener(this);
        alter_password.setOnClickListener(this);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        exit.setOnClickListener(this);
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
        dialog.show();//显示对话框
    }
    private void initView() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        view = LayoutInflater.from(this).inflate(R.layout.take_photo_dialog, null);
        //初始化控件
        saveUri = Uri.fromFile(new File(getExternalFilesDir(Environment.DIRECTORY_DCIM),"test.jpg"));
//        progressDialog=new ProgressDialog(this);
//        progressDialog.setTitle("正在修改中！");
//        progressDialog.setMessage("Loading.....");
//        progressDialog.setCancelable(false);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("正在上传中!");
        progressDialog.setMessage("Loading......");
        progressDialog.setCancelable(false);

        choosePhoto = view.findViewById(R.id.choosePhoto);
        username_1 = findViewById(R.id.username);
        takePhoto = view.findViewById(R.id.takePhoto);
        return1 = findViewById(R.id.return1);
        headcard = findViewById(R.id.head);
        username = findViewById(R.id.username1);
        sex1 = findViewById(R.id.sex1);
        telephone = findViewById(R.id.telephone1);
        sex = findViewById(R.id.sex);
        headView = findViewById(R.id.headimage);
        user_name = findViewById(R.id.user_name);
        settting = findViewById(R.id.setting);
        telephone1=findViewById(R.id.telephone);
        alter_password=findViewById(R.id.alter_password);
        exit=findViewById(R.id.exit_login);
    }

    /*
    性别选择
     */
    public void show1() {
        final String[] items = new String[]{"男", "女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别选择:");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {

                 sex.setText(items[which]);
                //progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();

                        RequestBody requestBody = new FormBody.Builder()
                                .add("userid", MainActivity.userid)
                                .add("phone", MainActivity.phone)
                                .add("userpassword", MainActivity.password1)
                                .add("username",MainActivity.username)
                                // add("image",MainActivity.image)
                                .add("sex", items[which])
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
                                MainActivity.phone = jsonObject.getString("phone");
                                MainActivity.password1 = jsonObject.getString("userpassword");
                                MainActivity.username = jsonObject.getString("username");
                                MainActivity.image=jsonObject.getString("image");
                                MainActivity.sex = jsonObject.getString("sex");
                                handler.sendEmptyMessage(SAVESUCCESS);
                            } else {
                                   handler.sendEmptyMessage(SAVEFAIL);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return1:
                main_layout.choose=4;
                intent=new Intent(manager_person.this,main_layout.class);
                startActivity(intent);
                overridePendingTransition(R.anim.dong, R.anim.dong1);
                break;
            case R.id.head:
                show(view);
                break;
            case R.id.sex1:
                show1();
                break;
            case R.id.username1:
                intent = new Intent(manager_person.this, alter_username.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
                break;
            case R.id.telephone1:
                intent = new Intent(manager_person.this, alter_phone.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
                break;
            case R.id.alter_password:
                intent = new Intent(manager_person.this, alter_password.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
                break;
            case R.id.choosePhoto:
                getPicFromPhoto();
                dialog.dismiss();
                break;
            case R.id.takePhoto:
                getPicFromCamera();
                dialog.dismiss();
                break;
            case R.id.exit_login:
                intent=new Intent(manager_person.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void getPicFromPhoto() {
        intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, CHOOSE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri);
                    ContentResolver cr = this.getContentResolver();
                    Cursor c = cr.query(tempUri, null, null, null, null);
                    c.moveToFirst();
                    //这是获取的图片保存在sdcard中的位置
                    path = c.getString(c.getColumnIndex("_data"));
                    System.out.println(path + "----------保存路径1");
                    break;
                case CHOOSE_PICTURE:
                    Uri uri = data.getData();
                    cutImage(uri);
                    ContentResolver cr1 = this.getContentResolver();
                    Cursor c1 = cr1.query(uri, null, null, null, null);
                    c1.moveToFirst();
                    //这是获取的图片保存在sdcard中的位置
                    path = c1.getString(c1.getColumnIndex("_data"));
                    System.out.println(path + "----------保存路径2");
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data);
                        submitUploadFile();
                    }
                    break;
            }

        }
    }

    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("alanjet", "The uri is not exist.");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            iBitmap= extras.getParcelable("data");
            path = FileUtilcll.saveFile(this, UUID.randomUUID()+".jpg", iBitmap);
            System.out.println("----------路径----------" + path);
            Log.d(TAG, "setImageToView: "+extras);
        }
    }

    private void getPicFromCamera() {
        String SDState = Environment.getExternalStorageState();
        if(SDState.equals(Environment.MEDIA_MOUNTED)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// action is
            ContentValues values = new ContentValues();
            tempUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, tempUri);
            startActivityForResult(intent, TAKE_PICTURE);
        }else{
            Toast.makeText(this, "sd卡不存在", Toast.LENGTH_SHORT).show();
        }

    }

    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case SUCCESS:
                    Toast.makeText(manager_person.this, "上传成功!", Toast.LENGTH_SHORT).show();
                    break;
                case FAIL:
                    Toast.makeText(manager_person.this, "上传失败!", Toast.LENGTH_SHORT).show();
                    break;
                case SAVESUCCESS:
                    Toast.makeText(manager_person.this, "保存成功!", Toast.LENGTH_SHORT).show();
                    break;
                case SAVEFAIL:
                    Toast.makeText(manager_person.this, "保存失败!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /**
     * 上传图片
     */
    private void submitUploadFile(){
        progressDialog.show();
        final File file=new File(path);
        final String RequestURL="http://"+ Ip.ip+":8080/project/Fileupload";
        if (file == null || (!file.exists())) {
            return;
        }
        Log.i(TAG, "请求的URL=" + RequestURL);
        Log.i(TAG, "请求的fileName=" + file.getName());
        final Map<String, String> params = new HashMap<String, String>();
        params.put("userid", MainActivity.userid);
        Log.d(TAG, "submitUploadFile: "+MainActivity.userid);
        params.put("file_type", "utf-8");

        new Thread(new Runnable() { //开启线程上传文件
            @Override
            public void run() {
                uploadFile(file, RequestURL,params);
            }
        }).start();
    }

    /**
     * android上传文件到服务器
     * @param file  需要上传的文件
     * @param RequestURL  请求的url
     * @return  返回响应的内容
     */
    private String uploadFile(File file,String RequestURL,Map<String, String> param)
     {
        String result = null;
        String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
        String PREFIX = "--" , LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";   //内容类型
        // 显示进度框
//		showProgressDialog();
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("Charset", CHARSET);  //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            if(file!=null){
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream( conn.getOutputStream());
                StringBuffer sb = new StringBuffer();

                String params = "";
                if (param != null && param.size() > 0) {
                    Iterator<String> it = param.keySet().iterator();
                    while (it.hasNext()) {
                        sb = null;
                        sb = new StringBuffer();
                        String key = it.next();
                        String value = param.get(key);
                        sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                        sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END);
                        sb.append(value).append(LINE_END);
                        params = sb.toString();
                    Log.i(TAG, key+"111"+params+"##");
                    dos.write(params.getBytes());
                    dos.flush();
                }
                }
                sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                 * filename是文 件的名字，包含后缀名的   比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\"upfile\";filename=\""+file.getName()+"\""+LINE_END);
                sb.append("Content-Type: image/pjpeg; charset="+CHARSET+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while((len=is.read(bytes))!=-1){
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);

                dos.flush();
                /**
                 * 获取响应码  200=成功
                 * 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                System.out.println("res========="+res);
                if(res==200){
                    Log.d(TAG, "uploadFile: "+"success");
                    Getimageid.interaction();
                    Thread.sleep(2000);
                    Log.d(TAG, "uploadFile: "+MainActivity.image);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getImage(MainActivity.image);
                        }
                    }).start();
                }
                else{

                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 接收返回图片地址 将图片地址转换成图片
     */
    public  void  getImage(String getpath) {
        Log.d(TAG, "getImage: "+getpath);
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(getpath).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
          //  System.out.println("tdw1");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Log.d(TAG, "getImage: " + inputStream);
                bit = BitmapFactory.decodeStream(inputStream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        headView.setImageBitmap(bit);
                    }
                });
            }
    } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    }
