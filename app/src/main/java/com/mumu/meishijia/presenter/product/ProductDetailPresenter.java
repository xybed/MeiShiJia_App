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

    public void getProductDetail(int id){
        model.getProductDetail(id)
                .subscribe(new RxObserver<Product>() {
                    @Override
                    protected void onSuccess(Product product) {
                        if(view != null)
                            view.getSuccess(product);
                    }
                });
    }

    public void addShoppingCart(int userId, int productId, int num){
        model.addShoppingCart(userId, productId, num)
                .subscribe(new RxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if(view != null)
                            view.addSuccess(s);
                    }

                    @Override
                    protected void onFail(String errMsg) {
                        if(view != null)
                            view.addFail(errMsg);
                    }
                });
    }
}
