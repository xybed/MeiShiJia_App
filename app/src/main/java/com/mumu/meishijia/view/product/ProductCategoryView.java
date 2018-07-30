package com.mumu.meishijia.view.product;

import com.mumu.meishijia.model.product.ProductCategory;
import com.mumu.meishijia.view.BaseView;

import java.util.List;

/**
 * Created by 77 on 2018/7/30 0030.
 */

public interface ProductCategoryView extends BaseView{
    void getSuccess(List<ProductCategory> categoryList);
}
