package com.mumu.meishijia.model.order;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by 77 on 2018/11/12 0012.
 * 购物车实体
 */

public class ShoppingCart {
    private Integer id;

    private String name;

    private String image;

    private BigDecimal price;

    @SerializedName("original_price")
    private BigDecimal originalPrice;

    @SerializedName("discount_price")
    private BigDecimal discountPrice;

    private Integer num;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
