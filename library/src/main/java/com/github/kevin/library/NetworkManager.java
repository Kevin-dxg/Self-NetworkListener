package com.github.kevin.library;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import com.github.kevin.library.callback.NetworkCallbackImpl;
import com.github.kevin.library.receiver.NetStateReceiver;
import com.github.kevin.library.utils.Constants;
import com.github.kevin.library.callback.NetChangeObserver;

public class NetworkManager {
    private static volatile NetworkManager instance;
    private Application application;
    private NetStateReceiver receiver;
    private ConnectivityManager manager;
    private NetworkCallbackImpl callback;
    private boolean isRegisterNetwork = false;

    public void setListener(NetChangeObserver listener) {
        receiver.setListener(listener);
    }

    private NetworkManager() {
        receiver = new NetStateReceiver();
    }

    public static NetworkManager getDefault() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    public void init(Application application) {
        this.application = application;
        initReceiver();
        initNetwork();
    }

    //方案1和方案2：通过动态广播监听网络状态
    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        application.registerReceiver(receiver, filter);
    }

    //方案3：不通过广播
    private void initNetwork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            manager = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkRequest request = new NetworkRequest.Builder().build();
                callback = new NetworkCallbackImpl();
                manager.registerNetworkCallback(request, callback);
                isRegisterNetwork = true;
            }
        }
    }

    public Application getApplication() {
        if (application == null) {
            throw new RuntimeException("先进行初始化操作");
        }
        return application;
    }

    public void registerObserver(Object register) {
        receiver.registerObserver(register);
    }

    public void unRegisterObserver(Object register) {
        receiver.unRegisterObserver(register);
    }

    public void unRegisterAllObserver() {
        receiver.unRegisterAllObserver();
    }

    public void registerNetwork() {
        if (!isRegisterNetwork) {
            initNetwork();
        }
    }

    public void unRegisterNetwork() {
        if (manager != null && callback != null) {
            manager.unregisterNetworkCallback(callback);
            callback = null;
            isRegisterNetwork = false;
        }
    }

}
