package com.mumu.meishijia.presenter.product;

import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.product.ProductListView;
import com.mumu.meishijia.viewmodel.product.ProductListViewModel;

/**
 * Created by 77 on 2018/8/10 0010.
 */

public class ProductListPresenter extends BasePresenter<ProductListView, ProductListViewModel>{
    public ProductListPresenter(BaseView view) {
        super(view);
    }
}
