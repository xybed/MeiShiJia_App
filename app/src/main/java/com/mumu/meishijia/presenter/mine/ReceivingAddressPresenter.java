package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.model.mine.ReceivingAddress;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.mine.ReceivingAddressView;
import com.mumu.meishijia.viewmodel.mine.ReceivingAddressViewModel;

import java.util.List;

/**
 * Created by 77 on 2018/10/23 0023.
 * 收货地址列表presenter
 */

public class ReceivingAddressPresenter extends BasePresenter<ReceivingAddressView, ReceivingAddressViewModel>{
    public ReceivingAddressPresenter(BaseView view) {
        super(view);
    }

    public void getReceivingAddress(Integer userId){
        model.getReceivingAddress(userId)
                .subscribe(new RxObserver<List<ReceivingAddress>>() {
                    @Override
                    protected void onSuccess(List<ReceivingAddress> receivingAddressList) {
                        if(view != null)
                            view.getSuccess(receivingAddressList);
                    }
                });
    }
}
