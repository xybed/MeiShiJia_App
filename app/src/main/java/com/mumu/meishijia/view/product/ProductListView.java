package com.mumu.meishijia.view.product;

import com.mumu.meishijia.model.product.Product;
import com.mumu.meishijia.view.BaseView;

import java.util.List;

/**
 * Created by 77 on 2018/8/10 0010.
 */

public interface ProductListView extends BaseView{
    void getListSuccess(List<Product> productList);
    void getListFail(String errMsg);
}
