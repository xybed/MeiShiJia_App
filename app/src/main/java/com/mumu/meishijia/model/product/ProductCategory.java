package com.mumu.meishijia.model.product;

import java.util.List;

/**
 * 商品分类实体类
 * Created by 77 on 2018/7/30 0030.
 */

public class ProductCategory {
    private Integer id;

    private Integer level;

    private Integer fid;

    private String name;

    //app端需要的字段
    private boolean selected;
    private List<ProductCategory> childList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<ProductCategory> getChildList() {
        return childList;
    }

    public void setChildList(List<ProductCategory> childList) {
        this.childList = childList;
    }
}
