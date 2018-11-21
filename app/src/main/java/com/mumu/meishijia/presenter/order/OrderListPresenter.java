package com.mumu.meishijia.presenter.order;

import com.mumu.meishijia.model.order.Order;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.order.OrderListView;
import com.mumu.meishijia.viewmodel.order.OrderListViewModel;

import java.util.List;

/**
 * Created by 77 on 2018/11/20 0020.
 * 订单列表presenter
 */

public class OrderListPresenter extends BasePresenter<OrderListView, OrderListViewModel>{
    public OrderListPresenter(BaseView view) {
        super(view);
    }

    public void getOrderList(int userId, int type, int pageIndex, int pageSize){
        model.getOrderList(userId, type, pageIndex, pageSize)
                .subscribe(new RxObserver<List<Order>>() {
                    @Override
                    protected void onSuccess(List<Order> orders) {
                        if(view != null)
                            view.getSuccess(orders);
                    }

                    @Override
                    protected void onFail(String errMsg) {
                        if(view != null)
                            view.getFail(errMsg);
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
