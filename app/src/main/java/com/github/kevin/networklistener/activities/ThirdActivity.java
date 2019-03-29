package com.github.kevin.networklistener.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.kevin.library.NetworkManager;
import com.github.kevin.library.annocation.Network;
import com.github.kevin.library.type.NetType;
import com.github.kevin.library.utils.Constants;
import com.github.kevin.networklistener.R;

/**
 * 网络监听实现方式：新方案替代广播 - ConnectivityManager
 */
public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    @Override
    protected void onStart() {
        super.onStart();
        NetworkManager.getDefault().registerNetwork();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getDefault().unRegisterNetwork();
    }
}
