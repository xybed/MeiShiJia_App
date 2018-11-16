package com.mumu.meishijia.view.order;

import com.mumu.meishijia.model.order.ShoppingCart;
import com.mumu.meishijia.view.BaseView;

import java.util.List;

/**
 * Created by 77 on 2018/11/12 0012.
 * 购物车view
 */

public interface ShoppingCartView extends BaseView{
    void getSuccess(List<ShoppingCart> shoppingCartList);

    void updateSuccess(String s);

    void updateFail(String errMsg);

    void deleteSuccess(String s);

    void clearSuccess(String s);
}
