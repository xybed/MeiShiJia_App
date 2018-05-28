package com.mumu.meishijia.model;

/**
 * Created by 7mu on 2016/8/24.
 */
public class BaseModel<T> {
    private int resultType;
    private int code;
    private String message;
    private T data;

    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
