package com.mumu.meishijia.constant;

public enum OrderStatus {
    WAIT_PAY(0, "代付款"),
    WAIT_SEND(1, "代发货"),
    WAIT_RECEIVE(2, "代收货"),
    WAIT_EVALUATE(3, "代评价"),
    REFUND(4, "退款");

    private Integer code;

    private String text;

    OrderStatus(Integer code, String text) {
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
