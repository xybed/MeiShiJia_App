package com.mumu.meishijia.view.product;

import com.mumu.meishijia.model.product.Product;
import com.mumu.meishijia.view.BaseView;

/**
 * Created by 77 on 2018/8/17 0017.
 */

public interface ProductDetailView extends BaseView{
    void getSuccess(Product product);

    void addSuccess(String s);
    void addFail(String s);
}
