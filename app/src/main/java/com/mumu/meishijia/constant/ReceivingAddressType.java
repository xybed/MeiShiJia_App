package com.mumu.meishijia.constant;

public enum ReceivingAddressType {
    COMMON(0, "普通"),
    DEFAULT(1, "默认");

    private Integer code;

    private String text;

    ReceivingAddressType(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
