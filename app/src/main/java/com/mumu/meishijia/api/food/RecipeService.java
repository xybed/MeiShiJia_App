package com.mumu.meishijia.api.food;

import com.mumu.meishijia.model.BaseModel;
import com.mumu.meishijia.model.food.RecipeSubModel;

import java.util.List;
import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/14.
 */

public interface RecipeService {
    @POST("food/recipe")
    Observable<BaseModel<List<RecipeSubModel>>> getRecipe(@QueryMap Map<String, String> map);
}
