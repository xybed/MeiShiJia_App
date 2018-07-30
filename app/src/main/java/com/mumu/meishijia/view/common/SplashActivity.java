package com.mumu.meishijia.view.common;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mumu.meishijia.BuildConfig;
import com.mumu.meishijia.R;
import com.mumu.meishijia.tencent.IMUtil;
import com.mumu.meishijia.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lib.baidu.MyLocation;
import lib.utils.MyLogUtil;
import lib.widget.CenterInDialog;

public class SplashActivity extends BaseActivity {

    private int delay = 3 * 1000;
    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            timer.cancel();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    };

    private CenterInDialog dialog;
    private int strIndex = 1;//用于权限提示的，表示第几个权限文字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setStatusBarTransparent();
        IMUtil.getInstance().initTIMSDK();
        IMUtil.getInstance().loginIM();
        initPermission();
    }

    private void initPermission() {
        /*
        1.定位权限
        2.存储权限
        3.相机权限
        合成权限组去请求用户获得
         */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            List<String> permissionList = new ArrayList<>();
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }else {
                MyLocation.getInstance().requestLocation();
            }
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.CAMERA);
            }
            if(permissionList.size() > 0){
                requestPermissions(permissionList.toArray(new String[permissionList.size()]), REQ_PERMISSION_GROUP);
            }else{
                goMain();
            }
        }else{
            goMain();
        }
    }

    private void goMain(){
        if(BuildConfig.DEBUG)
            delay = 1500;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, delay);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQ_PERMISSION_GROUP:
                MyLogUtil.e("grantLength", grantResults.length+"");
                for(int grant : grantResults){
                    MyLogUtil.e("grantResult", grant+"");
                }
                MyLogUtil.e("permissionLength", permissions.length+"");
                for(String permission : permissions){
                    MyLogUtil.e("permission", permission);
                }
                showDialog(permissions, grantResults);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showDialog(String[] permissions, int[] grantResults) {
        View view = View.inflate(this, R.layout.dialog_permission_tip, null);
        TextView textView = (TextView) view.findViewById(R.id.txt_permission_tip);
        StringBuilder builder = new StringBuilder();
        boolean canGoMain = true;
        for(int i=0;i<grantResults.length;i++){
            if(grantResults[i] == -1){
                canGoMain = false;
                switch (permissions[i]){
                    case Manifest.permission.ACCESS_FINE_LOCATION:
                        appendString(builder, "定位权限");
                        break;
                    case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                        appendString(builder, "存储权限");
                        break;
                    case Manifest.permission.CAMERA:
                        appendString(builder, "相机权限");
                        break;
                }
            }
        }
        if(canGoMain){
            goMain();
            return;
        }
        textView.setText("使用美食佳之前，需要开启" + builder.toString() + "，已确保帐号登录安全和信息安全。");
        Button button = (Button) view.findViewById(R.id.btn_go_open);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null)
                    dialog.dismiss();
            }
        });
        dialog = new CenterInDialog(this, view, false, false);
        dialog.show();
    }

    private void appendString(StringBuilder builder, String str){
        switch (strIndex){
            case 1:
                builder.append(str);
                break;
            case 2:
                builder.append("、").append(str);
                break;
            default:
                builder.append("和").append(str);
        }
        strIndex++;
    }
}
