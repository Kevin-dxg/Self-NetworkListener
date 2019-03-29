package com.github.kevin.library.utils;

import com.github.kevin.library.type.NetType;

import java.lang.reflect.Method;

public class MethodManager {

    //需要执行的注解方法
    private Method method;

    //注解方法的参数类型
    private Class<?> type;

    //注解中的网络类型
    private NetType netType;

    public MethodManager(Method method, Class<?> type, NetType netType) {
        this.method = method;
        this.type = type;
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

}
