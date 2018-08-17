package com.mumu.meishijia.presenter.product;

import com.mumu.meishijia.model.product.Product;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.product.ProductDetailView;
import com.mumu.meishijia.viewmodel.product.ProductDetailViewModel;

/**
 * Created by 77 on 2018/8/17 0017.
 */

public class ProductDetailPresenter extends BasePresenter<ProductDetailView, ProductDetailViewModel>{
    public ProductDetailPresenter(BaseView view) {
        super(view);
    }

    public void getProducDetail(int id){
        model.getProductDetail(id)
                .subscribe(new RxObserver<Product>() {
                    @Override
                    protected void onSuccess(Product product) {
                        if(view != null)
                            view.getSuccess(product);
                    }
                });
    }
}
