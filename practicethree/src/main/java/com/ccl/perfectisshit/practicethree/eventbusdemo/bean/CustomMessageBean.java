package com.ccl.perfectisshit.practicethree.eventbusdemo.bean;

/**
 * Created by ccl on 2018/1/23.
 */

public class CustomMessageBean<T> {
    private int hashCode;
    private int messageCode;
    private T data;
    private String message;
    /*public CustomMessageBean() {
    }

    public CustomMessageBean(int messageCode) {
        this.messageCode = messageCode;
    }

    public CustomMessageBean(int messageCode, String message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    public CustomMessageBean(int hashCode, int messageCode) {
        this.hashCode = hashCode;
        this.messageCode = messageCode;
    }

    public CustomMessageBean(int hashCode, int messageCode, String message) {
        this.hashCode = hashCode;
        this.messageCode = messageCode;
        this.message = message;
    }

    public CustomMessageBean(int messageCode, T data) {
        this.messageCode = messageCode;
        this.data = data;
    }

    public CustomMessageBean(int messageCode, T data, String message) {
        this.messageCode = messageCode;
        this.data = data;
        this.message = message;
    }

    public CustomMessageBean(int hashCode, int messageCode, T data) {
        this.hashCode = hashCode;
        this.messageCode = messageCode;
        this.data = data;
    }

    public CustomMessageBean(int hashCode, int messageCode, T data, String message) {
        this.hashCode = hashCode;
        this.messageCode = messageCode;
        this.data = data;
        this.message = message;
    }*/

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHashCode() {
        return hashCode;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    /*public static class Builder<T>{

        private final CustomMessageBean<T> mCustomMessage;

        public Builder(){
            mCustomMessage = new CustomMessageBean<>();
        }
        public Builder setHashCode(int hashCode){
            mCustomMessage.hashCode = hashCode;
            return this;
        }

        public Builder setMessageCode(int messageCode){
            mCustomMessage.messageCode = messageCode;
            return this;
        }

        public Builder setData(T data){
            mCustomMessage.data = data;
            return this;
        }

        public Builder setMessage(String message){
            mCustomMessage.message = message;
            return this;
        }

        public CustomMessageBean build(){
            return mCustomMessage;
        }
    }*/
}
