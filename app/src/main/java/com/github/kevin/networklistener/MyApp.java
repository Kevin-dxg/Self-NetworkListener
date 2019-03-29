package com.github.kevin.networklistener;

import android.app.Application;

import com.github.kevin.library.NetworkManager;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getDefault().init(this);
    }
}
