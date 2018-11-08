package com.mumu.meishijia.view.order;

import com.mumu.meishijia.model.mine.ReceivingAddress;
import com.mumu.meishijia.view.BaseView;

/**
 * Created by 77 on 2018/11/8 0008.
 * 确认订单view
 */

public interface OrderConfirmView extends BaseView{
    void getAddressSuccess(ReceivingAddress receivingAddress);
}
