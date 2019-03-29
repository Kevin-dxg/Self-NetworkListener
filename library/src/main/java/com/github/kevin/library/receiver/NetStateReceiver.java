package com.github.kevin.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.github.kevin.library.NetworkManager;
import com.github.kevin.library.utils.MethodManager;
import com.github.kevin.library.callback.NetChangeObserver;
import com.github.kevin.library.type.NetType;
import com.github.kevin.library.utils.Constants;
import com.github.kevin.library.annocation.Network;
import com.github.kevin.library.utils.NetworkUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NetStateReceiver extends BroadcastReceiver {
    //网络类型
    private NetType netType;
    //网络监听
    private NetChangeObserver listener;
    private Map<Object, List<MethodManager>> networkMap;

    public NetStateReceiver() {
        //初始化网络
        netType = NetType.NONE;
        networkMap = new HashMap<>();
    }

    public void setListener(NetChangeObserver listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
            Log.e(Constants.LOG_TAG, "onReceive ==> 网络发生改变了");
            netType = NetworkUtils.getNetTye();
            if (NetworkUtils.isNetworkAvailable()) {
                Log.e(Constants.LOG_TAG, "onReceive ==> 网络连接成功");
                if (listener != null) {
                    listener.onConnect(netType);
                }
            } else {
                Log.e(Constants.LOG_TAG, "onReceive ==> 没有网络连接");
                if (listener != null) {
                    listener.onDisConnect();
                }
            }
            post(netType);
        }
    }

    //消息分发到所有Activity
    private void post(NetType netType) {
        //获取所有的Activity
        Set<Object> set = networkMap.keySet();
        for (Object obj : set) {
            List<MethodManager> methodList = networkMap.get(obj);
            if (methodList != null) {
                for (MethodManager methodManager : methodList) {
                    if (methodManager.getType().isAssignableFrom(netType.getClass())) {
                        switch (methodManager.getNetType()) {
                            case AUTO:
                                invoke(methodManager, obj, netType);
                                break;
                            case WIFI:
                                if (NetType.WIFI == netType || NetType.NONE == netType) {
                                    invoke(methodManager, obj, netType);
                                }
                                break;
                            case CMNET:
                                if (NetType.CMNET == netType || NetType.NONE == netType) {
                                    invoke(methodManager, obj, netType);
                                }
                                break;
                            case CMWAP:
                                if (NetType.CMWAP == netType || NetType.NONE == netType) {
                                    invoke(methodManager, obj, netType);
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(MethodManager methodManager, Object obj, NetType netType) {
        Method method = methodManager.getMethod();
        try {
            method.invoke(obj, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private List<MethodManager> findAnnocationMethod(Object register) {
        List<MethodManager> methodList = new ArrayList<>();
        Class<?> clazz = register.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Network network = method.getAnnotation(Network.class);
            if (network == null) {
                continue;
            }
            Type returnType = method.getGenericReturnType();
            if (!"void".equalsIgnoreCase(returnType.toString())) {
                continue;
            }
            Class<?>[] params = method.getParameterTypes();
            if (params.length != 1) {
                continue;
            }
            MethodManager methodManager = new MethodManager(method, params[0], network.netType());
            methodList.add(methodManager);
        }
        return methodList;
    }

    public void registerObserver(Object register) {
        List<MethodManager> methodList = networkMap.get(register);
        if (methodList == null) {
            methodList = findAnnocationMethod(register);
            networkMap.put(register, methodList);
        }
    }

    public void unRegisterObserver(Object register) {
        if (!networkMap.isEmpty()) {
            networkMap.remove(register);
            Log.e(Constants.LOG_TAG, "unRegisterObserver ==> "
                    + register.getClass().getName() + "注销成功");
        }
    }

    public void unRegisterAllObserver() {
        if (!networkMap.isEmpty()) {
            networkMap.clear();
            networkMap = null;
        }
        //注销广播
        NetworkManager.getDefault().getApplication().unregisterReceiver(this);
        Log.e(Constants.LOG_TAG, "unRegisterAllObserver ==> " + "注销所有监听成功");
    }

}
