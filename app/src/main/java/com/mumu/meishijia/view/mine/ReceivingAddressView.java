package com.mumu.meishijia.view.mine;

import com.mumu.meishijia.model.mine.ReceivingAddress;
import com.mumu.meishijia.view.BaseView;

import java.util.List;

/**
 * Created by 77 on 2018/10/23 0023.
 */

public interface ReceivingAddressView extends BaseView{
    void getSuccess(List<ReceivingAddress> receivingAddressList);
}
