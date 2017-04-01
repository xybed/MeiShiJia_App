package com.mumu.meishijia.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mumu.meishijia.R;

import lib.baidu.MyLocation;
import lib.utils.ToastUtil;
import lib.widget.CenterInDialog;

public class SplashActivity extends BaseActivity {

    private CenterInDialog dialog;
    private boolean isLoc = true;
    private boolean isWri = true;
    private boolean isCam = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        transparencyStatusBar();
        initPermission();
    }

    private void initPermission(){
        /*
        1.定位权限
        2.存储权限
        3.相机权限
        权限一个一个去请求用户获得
         */
        if(permissionIsGet(REQ_LOCATION_PMS, Manifest.permission.ACCESS_FINE_LOCATION)){
            MyLocation.getInstance().requestLocation();
            if(permissionIsGet(REQ_WRITE_EXTERNAL_STORAGE_PMS, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                permissionIsGet(REQ_CAMERA_PMS, Manifest.permission.CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQ_LOCATION_PMS:
                if (grantResults != null && grantResults.length != 0)
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        isLoc = false;
                    }else{
                        MyLocation.getInstance().requestLocation();
                    }
                if(permissionIsGet(REQ_WRITE_EXTERNAL_STORAGE_PMS, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    permissionIsGet(REQ_CAMERA_PMS, Manifest.permission.CAMERA);
                }
                break;
            case REQ_WRITE_EXTERNAL_STORAGE_PMS:
                if (grantResults != null && grantResults.length != 0)
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        isWri = false;
                    }
                permissionIsGet(REQ_CAMERA_PMS, Manifest.permission.CAMERA);
                break;
            case REQ_CAMERA_PMS:
                if (grantResults != null && grantResults.length != 0)
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        isCam = false;
                    }
                //最后一个权限获取完，弹窗
                showDialog();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showDialog(){
        View view = View.inflate(this, R.layout.dialog_permission_tip, null);
        TextView textView = (TextView) view.findViewById(R.id.txt_permission_tip);
        StringBuilder builder = new StringBuilder();
        if(!isLoc && !isWri && !isCam){
            builder.append("定位权限、存储权限和相机权限");
        }else if(!isLoc && !isWri){
            builder.append("定位权限和存储权限");
        }else if(!isLoc && !isCam){
            builder.append("定位权限和相机权限");
        }else if(!isLoc){
            builder.append("定位权限");
        }else if(!isWri){
            builder.append("存储权限");
        }else if(!isCam){
            builder.append("相机权限");
        }else {
            return;
        }
        textView.setText("使用美食佳之前，需要开启" + builder.toString() + "，已确保帐号登录安全和信息安全。");
        Button button = (Button) view.findViewById(R.id.btn_go_open);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog != null)
                    dialog.dismiss();
            }
        });
        dialog = new CenterInDialog(this, view, false, false);
        dialog.show();
    }
}
