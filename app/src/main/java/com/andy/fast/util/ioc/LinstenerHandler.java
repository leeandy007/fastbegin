package com.andy.fast.util.ioc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LinstenerHandler implements InvocationHandler {

    private Object object;

    private Method objectMethod;

    private String callbackMethod;

    public LinstenerHandler(Object object, Method objectMethod, String callbackMethod) {
        this.object = object;
        this.objectMethod = objectMethod;
        this.callbackMethod = callbackMethod;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        if(method.getName().equals(callbackMethod)){
            return objectMethod.invoke(object, args);
        }
        return null;
    }
}
