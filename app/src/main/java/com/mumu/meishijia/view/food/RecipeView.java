package com.mumu.meishijia.view.food;

import com.mumu.meishijia.model.food.RecipeModel;
import com.mumu.meishijia.view.BaseView;

import java.util.List;

/**
 * Created by 7mu on 2016/12/14.
 * 食谱view接口
 */

public interface RecipeView extends BaseView{
    void getSuccess(List<RecipeModel> result);
    void getFail(String errMsg);
}
