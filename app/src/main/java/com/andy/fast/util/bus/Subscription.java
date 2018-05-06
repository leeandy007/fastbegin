package com.andy.fast.util.bus;

public class Subscription {

    private Object subscriberObject;

    private SubscriberMethod subscriberMethod;

    public Subscription(Object subscriberObject, SubscriberMethod subscriberMethod) {
        this.subscriberObject = subscriberObject;
        this.subscriberMethod = subscriberMethod;
    }

    public Object getSubscriberObject() {
        return subscriberObject;
    }

    public SubscriberMethod getSubscriberMethod() {
        return subscriberMethod;
    }
}
