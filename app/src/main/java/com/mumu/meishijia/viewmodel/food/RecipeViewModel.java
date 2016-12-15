package com.mumu.meishijia.viewmodel.food;

import com.mumu.meishijia.api.food.RecipeService;
import com.mumu.meishijia.http.HttpRequestParams;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.http.RetroResListener;
import com.mumu.meishijia.model.food.RecipeModel;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */

public class RecipeViewModel implements IRecipeViewModel{

    public RecipeViewModel(RecipeListener listener){
        this.listener = listener;
    }

    @Override
    public void getRecipe(){
        HttpRequestParams params = new HttpRequestParams();
        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
        httpRetrofit.getList(httpRetrofit.getApiService(RecipeService.class, "", params).getRecipe(params.urlParams), "", new RetroResListener<List<RecipeModel>>() {
            @Override
            protected void onSuccess(List<RecipeModel> result) {
                if(listener != null)
                    listener.getSuccess(result);
            }

            @Override
            protected void onFailure(String errMsg) {
                if(listener != null)
                    listener.getFail(errMsg);
            }
        });
    }

    private RecipeListener listener;
    public interface RecipeListener{
        void getSuccess(List<RecipeModel> result);
        void getFail(String errMsg);
    }
}
