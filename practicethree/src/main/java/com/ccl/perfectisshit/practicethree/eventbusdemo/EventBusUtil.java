package com.ccl.perfectisshit.practicethree.eventbusdemo;

import com.ccl.perfectisshit.practicethree.eventbusdemo.bean.CustomMessageBean;
import com.ccl.perfectisshit.practicethree.eventbusdemo.interf.OnNotifyListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;

/**
 * Created by ccl on 2018/1/19.
 */

public class EventBusUtil {

    public static EventBusBuilder eventBusBuilder() {
        return EventBus.builder();
    }

    public static EventBus getEventBus() {
        return EventBus.getDefault();
    }

    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unRegister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    /**
     * 为了规范回调方法名（方法必须添加EventBus注解）
     * @param onNotifyListener 回调监听
     * @author ccl
     * @date 2018 /1/22 13:49
     */
    public static void setOnMessageNotify(OnNotifyListener onNotifyListener) {

    }

    /**
     * 发送事件
     * @author ccl
     * @param customMessage 需要发送的事件
     * @date 2018/1/22 14:13
     */
    public static void sendEvent(CustomMessageBean customMessage) {
        EventBus.getDefault().post(customMessage);
    }

    /**
     * 发送粘性事件（系统会存储最近一次粘性事件，新注册能收到粘性事件的实体总会接收到这个事件，所以一般收到并且处理完之后需移除该事件）
     * #
     * @author ccl
     * @param object 需要发送的粘性事件
     * @date 2018/1/22 14:13
     */
    public static void sendStickyEvent(Object object) {
        EventBus.getDefault().postSticky(object);
    }

    /**
     * 移除所有的粘性事件
     * @author ccl
     * @date 2018/1/22 14:20
     */
    public static void removeAllStickyEvents(){
        EventBus.getDefault().removeAllStickyEvents();
    }

    /**
     * 移除某个具体的粘性事件
     * @author ccl
     * @param customMessage 需要移除的粘性事件
     * @date 2018/1/22 14:21
     */
    public static void removeStickyEvent(CustomMessageBean customMessage){
        EventBus.getDefault().removeStickyEvent(customMessage);
    }

    /**
     * 移除某类粘性事件
     * @author ccl
     * @param clazz 需要移除的粘性事件的Class
     * @date 2018/1/22 14:22
     */
    public static void removeStickyEvent(Class<? extends CustomMessageBean> clazz){
        EventBus.getDefault().removeStickyEvent(clazz);
    }

    /**
     * 取消事件的传递（一般发生在高优先级的subscriber）
     * @author ccl
     * @param customMessage 需要取消传递的事件
     * @date 2018/1/22 14:23
     */
    public static void cancelEventDelivery(CustomMessageBean customMessage){
        EventBus.getDefault().cancelEventDelivery(customMessage);
    }
}
