package com.mumu.meishijia.constant;

public enum ProductStatus {
    LOWER(0, "下架"),
    SHELF(1, "上架");

    private Integer code;

    private String text;

    ProductStatus(Integer code, String text) {
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
