package com.mumu.meishijia.presenter.food;

import com.mumu.meishijia.model.food.RecipeSubModel;
import com.mumu.meishijia.view.food.RecipeView;
import com.mumu.meishijia.viewmodel.food.RecipeViewModel;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */

public class RecipePresenter implements RecipeViewModel.RecipeListener{

    private RecipeView view;
    private RecipeViewModel viewModel;

    public RecipePresenter(RecipeView view){
        this.view = view;
        viewModel = new RecipeViewModel(this);
    }

    public void getRecipe(){
        viewModel.getRecipe();
    }

    @Override
    public void getSuccess(List<RecipeSubModel> result) {
        view.getSuccess(result);
    }

    @Override
    public void getFail(String errMsg) {
        view.getFail(errMsg);
    }
}
