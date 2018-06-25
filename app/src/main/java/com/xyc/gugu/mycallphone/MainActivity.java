package com.xyc.gugu.mycallphone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtPhoneNumber;
    private Button tvCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPer();
        initView();

    }

    /**
     * 6.0系统开始需要动态申请权限了
     */
    private void checkPer() {
        // sdk 等于23是6.0的系统，6.0+是需要代码申请权限de
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // 请求权限
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);
            }
        }

    }

    private void initView() {
         // 在xml文件里，通过id找到页面上的2个控件。
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        tvCall = (Button) findViewById(R.id.tvCall);
        // 点击拨号按钮，开始拨号啦。
        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = edtPhoneNumber.getText().toString();
                // 校验一下手机号是否合法，现在的手机号都是11位的
                if (phoneNumber.length() != 11) {
                    Toast.makeText(MainActivity.this, "电话号码非法，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }
                  /// 跳转拨号啦
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                 // 跳转前，先看下是否有打电话权限先。
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "未获得电话权限，请手动授权", Toast.LENGTH_LONG).show();
                    // 请求权限
                    return;
                }
                try {
                    startActivity(intent); // 跳转
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "该手机不支持拨打电话", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
