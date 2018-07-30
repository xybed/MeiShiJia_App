package com.mumu.meishijia.presenter.product;

import com.mumu.meishijia.model.product.ProductCategory;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.product.ProductCategoryView;
import com.mumu.meishijia.viewmodel.product.ProductCategoryViewModel;

import java.util.List;

/**
 * Created by 77 on 2018/7/30 0030.
 */

public class ProductCategoryPresenter extends BasePresenter<ProductCategoryView, ProductCategoryViewModel>{
    public ProductCategoryPresenter(BaseView view) {
        super(view);
    }

    public void getProductCategory(int id){
        model.getProductCategory(id)
                .subscribe(new RxObserver<List<ProductCategory>>() {
                    @Override
                    protected void onSuccess(List<ProductCategory> productCategories) {
                        if(view != null)
                            view.getSuccess(productCategories);
                    }
                });
    }
}
