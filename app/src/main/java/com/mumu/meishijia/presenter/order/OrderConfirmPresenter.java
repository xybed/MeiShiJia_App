package com.mumu.meishijia.presenter.order;

import com.mumu.meishijia.model.mine.ReceivingAddress;
import com.mumu.meishijia.model.order.ShoppingCartDto;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.order.OrderConfirmView;
import com.mumu.meishijia.viewmodel.order.OrderConfirmViewModel;

import java.util.List;

/**
 * Created by 77 on 2018/11/8 0008.
 * 确认订单presenter
 */

public class OrderConfirmPresenter extends BasePresenter<OrderConfirmView, OrderConfirmViewModel>{
    public OrderConfirmPresenter(BaseView view) {
        super(view);
    }

    public void getDefaultReceivingAddress(int userId){
        model.getDefaultReceivingAddress(userId)
                .subscribe(new RxObserver<ReceivingAddress>() {
                    @Override
                    protected void onSuccess(ReceivingAddress receivingAddress) {
                        if(view != null)
                            view.getAddressSuccess(receivingAddress);
                    }

                    @Override
                    protected void onFail(String errMsg) {

                    }
                });
    }

    public void placeOrder(List<ShoppingCartDto> dtoList, int addressId){
        model.placeOrder(dtoList, addressId)
                .subscribe(new RxObserver<Integer>() {
                    @Override
                    protected void onSuccess(Integer integer) {
                        if(view != null)
                            view.orderSuccess(integer);
                    }
                });
    }
}
