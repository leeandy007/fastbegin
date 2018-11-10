package com.andy.fast.util.net.config;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

public class Configurator {

    private static final HashMap<Object, Object> CONFIGS = new HashMap<>();

    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    private Configurator(){
        CONFIGS.put(ConfigKeys.CONFIG_READY.name(), false);
    }

    final HashMap<Object, Object> getConfigs(){
        return CONFIGS;
    }

    final <T> T getConfiguration(Object key){
        checkConfiguration();
        final Object value = CONFIGS.get(key);
        if(value == null){
            throw new NullPointerException(key.toString() + "IS NULL");
        }
        return (T) value;
    }

    //配置APIHOST
    public final Configurator setHost(String host){
        CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    //配置拦截器
    public final Configurator setInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    //检查配置是否完成
    private void checkConfiguration(){
        final boolean isReady = (boolean) CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        if(!isReady){
            throw new RuntimeException("Configuration is not ready, call configure()");
        }
    }

    //配置完成
    public final void build(){
        CONFIGS.put(ConfigKeys.CONFIG_READY.name(), true);
    }

}
