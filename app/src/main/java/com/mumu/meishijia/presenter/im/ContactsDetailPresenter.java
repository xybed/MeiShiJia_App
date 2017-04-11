package com.mumu.meishijia.presenter.im;

import com.mumu.meishijia.model.im.ContactsDetailModel;
import com.mumu.meishijia.view.im.ContactsDetailView;
import com.mumu.meishijia.viewmodel.im.ContactsDetailViewModel;

/**
 * 联系人详细资料的presenter
 * Created by Administrator on 2017/4/10.
 */

public class ContactsDetailPresenter implements ContactsDetailViewModel.ContactsDetailListener{

    private ContactsDetailView view;
    private ContactsDetailViewModel viewModel;

    public ContactsDetailPresenter(ContactsDetailView view){
        this.view = view;
        viewModel = new ContactsDetailViewModel(this);
    }

    public void getContactsDetail(int friendId){
        viewModel.getContactsDetail(friendId);
    }

    @Override
    public void getSuccess(ContactsDetailModel result) {
        view.getSuccess(result);
    }

    @Override
    public void getFail(String errMsg) {
        view.getFail(errMsg);
    }
}
