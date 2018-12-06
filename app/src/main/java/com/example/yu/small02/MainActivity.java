package com.example.yu.small02;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yu.small02.model.ReBean;
import com.example.yu.small02.presenter.IpresenterImpl;
import com.example.yu.small02.view.IView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IView{

    EditText name,pwd;
    CheckBox remeber,autologin;
    IpresenterImpl ipresenter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipresenter=new IpresenterImpl(this);
        sharedPreferences=getSharedPreferences("Mydata",MODE_MULTI_PROCESS);
        editor=sharedPreferences.edit();

        initView();
        //取出sharedpreferences里的数据 判断是否记住密码 判断是否自动登录
        boolean isCheck_r= sharedPreferences.getBoolean("isCheck_R", false);
        boolean isCheck_l=sharedPreferences.getBoolean("isCheck_L",false);
        if(isCheck_l){
            //如果自动登录 直接跳转
            Intent intent=new Intent(MainActivity.this,ShowActivity.class);
            startActivity(intent);
            //跳转后 销毁页面
            finish();
        }else if(isCheck_r){
            //如果记住密码 把数据 显示在输入框内 且 CheckBox状态为true
            String names = sharedPreferences.getString("name", null);
            String pwds = sharedPreferences.getString("pwd", null);
            pwd.setText(pwds);
            name.setText(names);
            remeber.setChecked(true);
        }else{
            pwd.setText("");
        }
    }
    //给控件找ID
    private void initView() {
        name=findViewById(R.id.name);
        pwd=findViewById(R.id.pwd);
        remeber=findViewById(R.id.remeber);
        autologin=findViewById(R.id.autologin);
        findViewById(R.id.commite).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //单击
        int id=v.getId();
        switch (id){
            case R.id.commite:
                //单击登录按钮  判断版本是否是6.0以上的
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    //如果是 动态添加权限
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},100);
                    }else{
                        startRequest();
                    }
                }else{
                    startRequest();
                }

                break;
            default:break;
        }
    }
    //开始请求数据
    private void startRequest() {
        //获取输入框的内容
        String names = name.getText().toString();
        String pwds = pwd.getText().toString();
        //调用接口 传参
        ipresenter.startRequest("http://www.xieast.com/api/user/login.php?username="+names+"&password="+pwds,ReBean.class);
    }

    @Override
    public void setData(Object data) {
        //判断解析回来的数据
        ReBean reBean= (ReBean) data;
        Log.i("TEST",reBean.toString());
        if(reBean.getCode()==100){
            if(autologin.isChecked()){
                //登录成功 且 自动登录勾选上
                editor.putBoolean("isCheck_L",autologin.isChecked());
                editor.commit();
            }else{
                editor.remove("isCheck_L");
                editor.commit();
            }
            if(remeber.isChecked()){
                //登录成功 且 记住密码勾选上
                editor.putString("name",name.getText().toString());
                editor.putString("pwd",pwd.getText().toString());
                editor.putBoolean("isCheck_R",remeber.isChecked());
                editor.commit();
            }else{
                editor.remove("name");
                editor.remove("pwd");
                editor.remove("isCheck_R");
                editor.commit();
            }
            Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
            //成功后跳转
            Intent intent=new Intent(MainActivity.this,ShowActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,reBean.getMsg(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ipresenter.onDetach();
    }
}
