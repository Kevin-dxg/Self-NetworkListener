package com.github.kevin.library.type;

/**
 * 注：CMNET和CMWAP在中国是根据业务来区分的，其实都是一回事。
 */
public enum NetType {
    //只要有网络，不关心网络类型
    AUTO,

    //wifi网络
    WIFI,

    //主要是pc/笔记本电脑/PAD设备网络
    CMNET,

    //手机网络
    CMWAP,

    //无任何网络
    NONE

}
