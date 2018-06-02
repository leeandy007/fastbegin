package com.andy.fast.util.image;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ImageHandler implements InvocationHandler {

    private Object mObject;

    public ImageHandler(Object object) {
        mObject = object;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        method.invoke(mObject, args);
        return null;
    }
}
