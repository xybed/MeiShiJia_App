package com.mumu.meishijia.view.order;

import com.mumu.meishijia.model.order.Order;
import com.mumu.meishijia.view.BaseView;

import java.util.List;

/**
 * Created by 77 on 2018/11/20 0020.
 * 订单列表view
 */

public interface OrderListView extends BaseView{
    void getSuccess(List<Order> orderList);
    void getFail(String errMsg);

    void updateSuccess(String s);
    void updateFail(String errMsg);
}
