package com.mumu.meishijia.presenter.order;

import com.mumu.meishijia.model.order.ShoppingCart;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.order.ShoppingCartView;
import com.mumu.meishijia.viewmodel.order.ShoppingCartViewModel;

import java.util.List;

/**
 * Created by 77 on 2018/11/12 0012.
 * 购物车presenter
 */

public class ShoppingCartPresenter extends BasePresenter<ShoppingCartView, ShoppingCartViewModel>{
    public ShoppingCartPresenter(BaseView view) {
        super(view);
    }

    public void getShoppingCart(int userId, int pageIndex, int pageSize){
        model.getShoppingCart(userId, pageIndex, pageSize)
                .subscribe(new RxObserver<List<ShoppingCart>>() {
                    @Override
                    protected void onSuccess(List<ShoppingCart> shoppingCarts) {
                        if(view != null)
                            view.getSuccess(shoppingCarts);
                    }
                });
    }
}
