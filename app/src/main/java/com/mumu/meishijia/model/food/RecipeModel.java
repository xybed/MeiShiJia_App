package com.mumu.meishijia.model.food;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */

public class RecipeModel {
    private String value;
    private List<RecipeSubModel> sub;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<RecipeSubModel> getSub() {
        return sub;
    }

    public void setSub(List<RecipeSubModel> sub) {
        this.sub = sub;
    }
}
