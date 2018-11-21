package com.mumu.meishijia.presenter.order;

import com.mumu.meishijia.model.order.Order;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.order.OrderDetailView;
import com.mumu.meishijia.viewmodel.order.OrderDetailViewModel;

/**
 * Created by 77 on 2018/11/21 0021.
 * 订单详情presenter
 */

public class OrderDetailPresenter extends BasePresenter<OrderDetailView, OrderDetailViewModel>{

    public OrderDetailPresenter(BaseView view) {
        super(view);
    }

    public void getOrderDetail(int orderId){
        model.getOrderDetail(orderId)
                .subscribe(new RxObserver<Order>() {
                    @Override
                    protected void onSuccess(Order order) {
                        if(view != null)
                            view.getSuccess(order);
                    }
                });
    }

    public void updateOrderStatus(int orderId, int status){
        model.updateOrderStatus(orderId, status)
                .subscribe(new RxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if(view != null)
                            view.updateSuccess(s);
                    }

                    @Override
                    protected void onFail(String errMsg) {
                        if(view != null)
                            view.updateFail(errMsg);
                    }
                });
    }
}
