package com.andy.fast.util.net.config;

import java.util.ArrayList;
import java.util.HashMap;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;

public class Configurator {

    private static final HashMap<Object, Object> CONFIGS = new HashMap<>();

    private static final ArrayList<Interceptor> LOGGING_INTERCEPTORS = new ArrayList<>();

    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private static final ArrayList<ConnectionSpec> CONNECTION_SPECS = new ArrayList<>();

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
//        if(value == null){
//            throw new NullPointerException(key.toString() + " IS NULL");
//        }
        return (T) value;
    }

    //配置APIHOST
    public final Configurator setHost(String host){
        CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    //配置日志拦截器
    public final Configurator setLoggingInterceptors(ArrayList<Interceptor> interceptors){
        if(interceptors == null){
            interceptors = new ArrayList<>();
        }
        LOGGING_INTERCEPTORS.addAll(interceptors);
        CONFIGS.put(ConfigKeys.LOGGING_INTERCEPTOR, LOGGING_INTERCEPTORS);
        return this;
    }

    //配置拦截器
    public final Configurator setInterceptors(ArrayList<Interceptor> interceptors){
        if(interceptors == null){
            interceptors = new ArrayList<>();
        }
        INTERCEPTORS.addAll(interceptors);
        CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    //配置ConnectionSpec
    public final Configurator setConnectionSpecs(ArrayList<ConnectionSpec> connectionSpecs){
        if(connectionSpecs == null){
            connectionSpecs = new ArrayList<>();
        }
        CONNECTION_SPECS.addAll(connectionSpecs);
        CONFIGS.put(ConfigKeys.CONNECTION_SPEC, CONNECTION_SPECS);
        return this;
    }

    //配置SocketFactory
    public final Configurator setSocketFactory(SocketFactory socketFactory){
        CONFIGS.put(ConfigKeys.SOCKET_FACTORY, socketFactory);
        return this;
    }

    //配置SSLSocketFactory
    public final Configurator setSSLSocketFactory(SSLSocketFactory sslSocketFactory){
        CONFIGS.put(ConfigKeys.SSL_SOCKET_FACTORY, sslSocketFactory);
        return this;
    }

    //配置X509TrustManager
    public final Configurator setX509TrustManager(X509TrustManager x509TrustManager){
        CONFIGS.put(ConfigKeys.X_509_TRUST_MANAGER, x509TrustManager);
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
