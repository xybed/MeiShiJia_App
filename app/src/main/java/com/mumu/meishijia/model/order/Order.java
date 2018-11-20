package com.mumu.meishijia.model.order;

import com.mumu.meishijia.model.mine.ReceivingAddress;
import com.mumu.meishijia.model.product.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 77 on 2018/11/19 0019.
 * 订单实体类
 */

public class Order {
    private Integer id;

    private String orderNumber;

    private ReceivingAddress receivingAddress;

    private List<Product> products;

    private BigDecimal payAmount;

    private Integer status;

    private String createTime;

    private String payTime;

    private String sendTime;

    private String dealTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public ReceivingAddress getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(ReceivingAddress receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }
}
