package com.github.kevin.library.callback;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import com.github.kevin.library.utils.Constants;

public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {

    /**
     * 在onAvailable和onLost方法中，它们一定会成对出现
     *
     * @param network
     */
    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.e(Constants.LOG_TAG, "onAvailable ==> 网络已连接");
    }


    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.e(Constants.LOG_TAG, "onLost ==> 网络已中断");
    }

    /**
     * 手动断开网络，可能不会回调该方法
     *
     * @param network
     * @param maxMsToLive
     */
    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
    }

    /**
     * 该方法会回调多次，使用时需要谨慎，避免重复的操作
     *
     * @param network
     * @param networkCapabilities
     */
    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            //NetworkCapabilities.TRANSPORT_WIFI可以用来判断网络属性
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.e(Constants.LOG_TAG, "onCapabilitiesChanged ==> 网络发生变更，类型为：wifi");
            } else {
                Log.e(Constants.LOG_TAG, "onCapabilitiesChanged ==> 网络发生变更，类型为：其他");
            }
        }
    }
}
