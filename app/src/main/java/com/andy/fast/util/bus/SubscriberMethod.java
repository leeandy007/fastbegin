package com.andy.fast.util.bus;

import java.lang.reflect.Method;

public class SubscriberMethod {

    //标签
    private String lable;

    //方法
    private Method method;

    //参数类型
    private Class[] paramterClass;

    public SubscriberMethod(String lable, Method method, Class[] paramterClass) {
        this.lable = lable;
        this.method = method;
        this.paramterClass = paramterClass;
    }

    public String getLable() {
        return lable;
    }

    public Method getMethod() {
        return method;
    }

    public Class[] getParamterClass() {
        return paramterClass;
    }

}
