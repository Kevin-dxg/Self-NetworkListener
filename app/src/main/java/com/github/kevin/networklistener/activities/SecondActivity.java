package com.github.kevin.networklistener.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.kevin.library.NetworkManager;
import com.github.kevin.library.type.NetType;
import com.github.kevin.library.utils.Constants;
import com.github.kevin.library.annocation.Network;
import com.github.kevin.library.utils.NetworkUtils;
import com.github.kevin.networklistener.R;

/**
 * 网络监听实现方式：广播 + 反射
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onStart() {
        super.onStart();
        NetworkManager.getDefault().registerObserver(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetworkManager.getDefault().unRegisterObserver(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Network(netType = NetType.WIFI)
    public void network(NetType type) {
//        if (NetType.NONE == type) {
//            //没有网络，提示用dialog方式跳转到设置界面
//            NetworkUtils.openSetting(this);
//        }
        Toast.makeText(this, "当前网络类型：" + type.name(), Toast.LENGTH_SHORT).show();
        Log.e(Constants.LOG_TAG, "network ==> NetType: " + type.name());
    }

    public void nextPage2(View view) {
        startActivity(new Intent(this, ThirdActivity.class));
    }
}
