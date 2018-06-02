package com.andy.fast.util.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface EventBase {

    //订阅
    String setLinstener();

    //事件源
    Class<?> linstenerType();

    //事件出发后执行的方法
    String callbackMethod();

}
