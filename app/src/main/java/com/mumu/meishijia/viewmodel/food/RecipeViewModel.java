package com.mumu.meishijia.viewmodel.food;

import com.mumu.meishijia.api.food.RecipeService;
import com.mumu.meishijia.http.HttpRequestParams;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.http.RetroResListener;
import com.mumu.meishijia.model.food.RecipeSubModel;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */

public class RecipeViewModel {

    public RecipeViewModel(RecipeListener listener){
        this.listener = listener;
    }

    public void getRecipe(){
//        HttpRequestParams params = new HttpRequestParams();
//        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
//        httpRetrofit.getList(httpRetrofit.getApiService(RecipeService.class, HttpUrl.GetRecipe, params).getRecipe(params.urlParams), "", new RetroResListener<List<RecipeSubModel>>() {
//            @Override
//            protected void onSuccess(List<RecipeSubModel> result) {
//                if(listener != null)
//                    listener.getSuccess(result);
//            }
//
//            @Override
//            protected void onFailure(String errMsg) {
//                if(listener != null)
//                    listener.getFail(errMsg);
//            }
//        });
    }

    private RecipeListener listener;
    public interface RecipeListener{
        void getSuccess(List<RecipeSubModel> result);
        void getFail(String errMsg);
    }
}
