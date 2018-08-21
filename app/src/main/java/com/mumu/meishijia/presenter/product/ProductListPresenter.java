package com.mumu.meishijia.presenter.product;

import com.mumu.meishijia.model.product.Product;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.product.ProductListView;
import com.mumu.meishijia.viewmodel.product.ProductListViewModel;

import java.util.List;

import lib.utils.StringUtil;

/**
 * Created by 77 on 2018/8/10 0010.
 */

public class ProductListPresenter extends BasePresenter<ProductListView, ProductListViewModel>{
    public ProductListPresenter(BaseView view) {
        super(view);
    }

    public void getProductList(int categoryId, String keyword, int pageIndex, int pageSize){
        if(StringUtil.isEmpty(keyword)){
            model.getProductList(categoryId, pageIndex, pageSize)
                    .subscribe(new RxObserver<List<Product>>() {
                        @Override
                        protected void onSuccess(List<Product> products) {
                            if(view != null)
                                view.getListSuccess(products);
                        }

                        @Override
                        protected void onFail(String errMsg) {
                            if(view != null)
                                view.getListFail(errMsg);
                        }
                    });
        }else {
            model.searchProductList(keyword, pageIndex, pageSize)
                    .subscribe(new RxObserver<List<Product>>() {
                        @Override
                        protected void onSuccess(List<Product> products) {
                            if(view != null)
                                view.getListSuccess(products);
                        }

                        @Override
                        protected void onFail(String errMsg) {
                            if(view != null)
                                view.getListFail(errMsg);
                        }
                    });
        }
    }
}
