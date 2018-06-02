package com.andy.fast.util.ioc;

import android.content.Context;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectUtils {

    public static void inject(Context context) {
        injectLayout(context);
        injectView(context);
        injectEvent(context);
    }

    //注入布局
    private static void injectLayout(Context context) {
        Class<? extends Context> clazz = context.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if(contentView != null){
            try {
                Method method = clazz.getMethod("setContentView", int.class);
                method.invoke(context, contentView.value());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //注入view
    private static void injectView(Context context) {
        Class<? extends Context> clazz = context.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            InjectView injectView = field.getAnnotation(InjectView.class);
            if(injectView != null){
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    View view = (View) method.invoke(context, injectView.value());//得到成员变量的具体实例值
                    field.setAccessible(true);
                    field.set(context, view);//给成员变量赋值
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //注入事件
    private static void injectEvent(Context context) {
        Class<? extends Context> clazz = context.getClass();
        Method[] methods = clazz.getDeclaredMethods();// 拿到所有的方法
        for (Method method : methods) {// 遍历
            Annotation[] annotations = method.getAnnotations();// 找到方法里所有的注解
            for (Annotation annotation : annotations) {//遍历注解
                Class<? extends Annotation> annotationType = annotation.annotationType();//拿到注解类型
                EventBase eventBase = annotationType.getAnnotation(EventBase.class);//找到注解上的直接类型
                if(annotationType == null){//如果是空继续
                    continue;
                }
                String setLinstener = eventBase.setLinstener();//拿到注解上注解里面的订阅
                Class<?> linstenerType = eventBase.linstenerType();//拿到事件源
                String callbackMethod = eventBase.callbackMethod();//拿到回调方法
                try {
                    Method valueMethod = annotationType.getDeclaredMethod("value");//拿到含有EventBase注解的注解里面的value方法
                    int[] viewIds = (int[]) valueMethod.invoke(annotation);//反射取出该方法里面的值
                    for (int viewId : viewIds) {//遍历viewId
                        Method findViewById = clazz.getMethod("findViewById", int.class);//拿到findViewById的方法
                        View view = (View) findViewById.invoke(context, viewId);//通过反射执行findViewById找到View
                        if(view == null){
                            continue;
                        }
                        Method clickMethod = view.getClass().getMethod(setLinstener, linstenerType);//拿到监听的方法
                        //给activity以及对应的方法设置动态代理
                        LinstenerHandler linstenerHandler = new LinstenerHandler(context, method, callbackMethod);
                        //设置动态代理Activity
                        Object proxy = Proxy.newProxyInstance(linstenerType.getClassLoader(), new Class[]{linstenerType}, linstenerHandler);
                        //执view的设置监听的方法
                        clickMethod.invoke(view, proxy);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
