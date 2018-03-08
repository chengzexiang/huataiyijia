package com.huatai.czx.huataiyijia_master;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huatai.czx.huataiyijia_master.activity.SecondActivity;
import com.huatai.czx.huataiyijia_master.bean.TestBean;
import com.huatai.czx.huataiyijia_master.presenter.MainActivityPresenter;
import com.huatai.czx.huataiyijia_master.view.MainActivityListener;

public class MainActivity extends AppCompatActivity implements MainActivityListener {

    private MainActivityPresenter presenter;
    private EditText username;
    private EditText userpwd;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this);
        initView();
        initListener();
        presenter.getTestData();
    }
    //监听事件
    private void initListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("100000") && userpwd.getText().toString().equals("123456")){
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "密码不正确,请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void callBackSuccess(TestBean bean) {
        if (bean != null) {
            System.out.println("成功");
        }
    }

    @Override
    public void callBackFailed(int code) {
        if (code == 1) {
            System.out.println("失败");
        }
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        userpwd = (EditText) findViewById(R.id.userpwd);
        login = (Button) findViewById(R.id.login);
    }
}
