package com.evantagesoft.hubspot_wrapper.response;

public class Response<T> {
    private int Code;
    private String Message;
    private T data;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "{" +
                "Code=" + Code +
                ", Message='" + Message + '\'' +
                ", data=" + data +
                '}';
    }
}
