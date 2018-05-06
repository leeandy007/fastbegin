package com.andy.fast.util.net;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public  abstract class NetCallback<Result> implements Callback {

    public abstract void onSuccess(Result result);


    @Override
    public void onSuccess(String result) {
        onSuccess(changeResult(result));
    }

    public static Class<?> analysisClassInfo(Object object){
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        return (Class<?>) params[0];

    }

    /**
     * 将String转化成泛型对象
     * */
    private Result changeResult(String result){
        Gson gson = new Gson();
        Class<?> clz = analysisClassInfo(this);
        Result objResult = (Result) gson.fromJson(result, clz);
        return objResult;
    }

}
