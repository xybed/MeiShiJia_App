package com.mumu.meishijia.api;

import com.mumu.meishijia.model.BaseModel;
import com.mumu.meishijia.model.food.RecipeSubModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2016/12/14.
 */

public interface RecipeApi {
    @GET("food/recipe")
    Observable<BaseModel<List<RecipeSubModel>>> getRecipe(@QueryMap Map<String, String> map);
}
