package com.andy.fast.util.bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Bus {

    private static volatile Bus instance;

    //注册表
    Map<Class, List<SubscriberMethod>> METHOD_CACHE = new HashMap<>();
    //执行表
    Map<String, List<Subscription>> SUBSCRIBERS = new HashMap<>();
    //注册辅助表
    Map<Class, List<String>> REGISTER = new HashMap<>();
    private Bus() {}

    //初始化
    public static Bus obtain() {
        if(null == instance){
            synchronized (Bus.class){
                if (null == instance){
                    instance = new Bus();
                }
            }
        }
        return instance;
    }

    //退出App的时候清理缓存
    public void clearCache(){
        METHOD_CACHE.clear();
        SUBSCRIBERS.clear();
        REGISTER.clear();
    }

    //注册
     public void register(Object object){
        Class<?> subscriberClass = object.getClass();
         List<SubscriberMethod> subscriber = findSubscriber(subscriberClass);
         //为了方便注销
         List<String> lables = REGISTER.get(subscriberClass);
         if (null == lables){
             lables = new ArrayList<>();
             REGISTER.put(subscriberClass, lables);
         }
         //制作执行表
         for (SubscriberMethod subscriberMethod : subscriber){
             //找到标签
             String lable = subscriberMethod.getLable();
             if (!lables.contains(lable)){
                 lables.add(lable);
             }
             //判断执行表数据是否存在
             List<Subscription> subscriptions = SUBSCRIBERS.get(lable);
             if(null == subscriptions){
                 subscriptions = new ArrayList<>();
                 SUBSCRIBERS.put(lable, subscriptions);
             }
             subscriptions.add(new Subscription(object, subscriberMethod));
         }
     }

     // 缓存表
     // 找到类中所有被subscriber声明的函数
     // 类-->标签lable，函数Method，参数类型数组paramterClass[]的集 缓存起来
    private List<SubscriberMethod> findSubscriber(Class<?> subscriberClass) {
        List<SubscriberMethod> subscriberMethods = METHOD_CACHE.get(subscriberClass);
        //先从缓存表中找是否注册过
        if(null == subscriberMethods){
            subscriberMethods = new ArrayList<>();
            //通过反射找到该类下所有的方法得到方法数组
            Method[] methods = subscriberClass.getDeclaredMethods();
            //遍历方法数组
            for (Method method : methods){
                //获得方法的注解
                Subscriber subscriber = method.getAnnotation(Subscriber.class);
                //判断是否是注解是否是Subscriber
                if(null != subscriber){
                    //标签的数组
                    String[] values = subscriber.value();
                    //方法参数类型数组
                    Class<?>[] paramterClass = method.getParameterTypes();
                    for(String value : values){
                        method.setAccessible(true);
                        subscriberMethods.add(new SubscriberMethod(value, method, paramterClass));
                    }

                }
            }
            METHOD_CACHE.put(subscriberClass, subscriberMethods);
        }
        return subscriberMethods;
    }

    //发送事件给订阅者
    public void post(String lable, Object... params){
        List<Subscription> subscriptions = SUBSCRIBERS.get(lable);
        if(null == subscriptions){
            return;
        }
        for (Subscription subscription : subscriptions){
            SubscriberMethod subscriberMethod = subscription.getSubscriberMethod();
            //执行函数需要的参数类型数组
            Class[] paramterClass = subscriberMethod.getParamterClass();
            //真是的参数
            Object[] realParams = new Object[paramterClass.length];
            if (null != params){
                for (int i = 0; i < paramterClass.length; i++) {
                    //判断形参是否比实参小同时盘点形参的数据类型是否跟实参的数据类型一致
                    if(i < params.length && paramterClass[i].isInstance(params[i])){
                        realParams[i] = params[i];
                    } else {
                        //形参比实参大，将形参的实际值赋值位null
                        realParams[i] = null;
                    }
                }
            }
            try {
                subscriberMethod.getMethod().invoke(subscription.getSubscriberObject(), realParams);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    //反注册
    // 清理注册表，获得对应类的所有标签
    // 清理执行表，根据标签获得集合，判断与本册注册的对象是否一致
    public void unregister(Object object){
        //对应对象类型的所有注册的标签
        List<String> lables = REGISTER.get(object.getClass());
        if(null != lables){
            for (String lable : lables) {
                //获得执行表中对lable的所有函数
                List<Subscription> subScriptions = SUBSCRIBERS.get(lable);
                if (null != subScriptions){
                    Iterator<Subscription> iterator = subScriptions.iterator();
                    while (iterator.hasNext()){
                        Subscription subscription = iterator.next();
                        //判断是否是同一个对象
                        if (subscription.getSubscriberObject() == object){
                            //从执行表中删除
                            iterator.remove();
                        }
                    }
                }
            }
        }

    }


}
