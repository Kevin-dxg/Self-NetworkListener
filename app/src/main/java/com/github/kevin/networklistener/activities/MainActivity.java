package com.github.kevin.networklistener.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.kevin.library.callback.NetChangeObserver;
import com.github.kevin.library.NetworkManager;
import com.github.kevin.library.type.NetType;
import com.github.kevin.library.utils.Constants;
import com.github.kevin.networklistener.R;

/**
 * 网络监听实现方式：广播 + 回调
 */
public class MainActivity extends AppCompatActivity implements NetChangeObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkManager.getDefault().setListener(this);
    }

    @Override
    public void onConnect(NetType type) {
        Toast.makeText(this, "当前网络类型：" + type.name(), Toast.LENGTH_SHORT).show();
        Log.e(Constants.LOG_TAG, "onConnect ==> NetType: " + type.name());
    }

    @Override
    public void onDisConnect() {

    }

    public void nextPage1(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }

}
