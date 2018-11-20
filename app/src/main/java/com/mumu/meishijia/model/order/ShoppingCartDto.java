package com.mumu.meishijia.model.order;

/**
 * Created by 77 on 2018/11/19 0019.
 * 用于下单接口，传输数据
 */

public class ShoppingCartDto {
    private Integer id;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
