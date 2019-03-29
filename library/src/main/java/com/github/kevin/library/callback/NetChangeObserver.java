package com.github.kevin.library.callback;

import com.github.kevin.library.type.NetType;

//接口监听
public interface NetChangeObserver {

    //网络连接
    void onConnect(NetType type);

    //网络断开
    void onDisConnect();

}
