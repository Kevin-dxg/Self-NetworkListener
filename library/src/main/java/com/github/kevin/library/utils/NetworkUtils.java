package com.github.kevin.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.github.kevin.library.NetworkManager;
import com.github.kevin.library.type.NetType;

public class NetworkUtils {


    /**
     * 网络是否可用
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) NetworkManager.getDefault().
                getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo[] infos = manager.getAllNetworkInfo();
        if (infos != null) {
            for (NetworkInfo info : infos) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前的网络类型
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static NetType getNetTye() {
        ConnectivityManager manager = (ConnectivityManager) NetworkManager.getDefault().
                getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return NetType.NONE;
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            return NetType.NONE;
        }
        int type = info.getType();
        if (ConnectivityManager.TYPE_MOBILE == type) {
            if ("cmnet".equals(info.getExtraInfo().toLowerCase())) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (ConnectivityManager.TYPE_WIFI == type) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }

    /**
     * 打开网络设置界面
     *
     * @param context
     */
    public static void openSetting(Context context) {
        Intent intent =  new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        ((Activity) context).startActivityForResult(intent, Constants.SETTING_REQUEST_CODE);
    }

}
