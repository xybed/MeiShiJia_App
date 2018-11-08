package com.mumu.meishijia.presenter.mine;

import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.mine.ReceivingAddressEditView;
import com.mumu.meishijia.viewmodel.mine.ReceivingAddressEditViewModel;

/**
 * Created by 77 on 2018/10/24 0024.
 * 收货地址信息presenter
 */

public class ReceivingAddressEditPresenter extends BasePresenter<ReceivingAddressEditView, ReceivingAddressEditViewModel>{
    public ReceivingAddressEditPresenter(BaseView view) {
        super(view);
    }

    public void addReceivingAddress(int userId, String name, String phone,
                    String province, String city, String address, boolean type){
        model.addReceivingAddress(userId, name, phone, province, city, address, type)
                .subscribe(new RxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if(view != null)
                            view.addSuccess(s);
                    }
                });
    }

    public void updateReceivingAddress(int id, String name, String phone,
                   String province, String city, String address, boolean type){
        model.updateReceivingAddress(id, name, phone, province, city, address, type)
                .subscribe(new RxObserver<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if(view != null)
                            view.updateSuccess(s);
                    }
                });
    }
}
