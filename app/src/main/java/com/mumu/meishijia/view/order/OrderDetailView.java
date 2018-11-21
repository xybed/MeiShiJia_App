package com.mumu.meishijia.view.order;

import com.mumu.meishijia.model.order.Order;
import com.mumu.meishijia.view.BaseView;

/**
 * Created by 77 on 2018/11/21 0021.
 * 订单详情view
 */

public interface OrderDetailView extends BaseView{
    void getSuccess(Order order);

    void updateSuccess(String s);
    void updateFail(String errMsg);
}
