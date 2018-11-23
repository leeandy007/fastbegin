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

    public static Type analysisClassInfo(Object object){
        Type genType = object.getClass().getGenericSuperclass();//获得带有泛型的父类
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();//getActualTypeArguments获取参数化类型(泛型)的数组
        return params[0];//返回第一个泛型
    }

    /**
     * 将String转化成泛型对象
     * */
    private Result changeResult(String json){
        Gson gson = new Gson();
        Type type = analysisClassInfo(this);
        return gson.fromJson(json, type);
    }

}
