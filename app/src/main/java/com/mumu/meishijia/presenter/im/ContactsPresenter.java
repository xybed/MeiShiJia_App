package com.mumu.meishijia.presenter.im;

import com.mumu.meishijia.model.im.ContactsModel;
import com.mumu.meishijia.view.im.ContactsView;
import com.mumu.meishijia.viewmodel.im.ContactsViewModel;

import java.util.List;

/**
 * 联系人的presenter
 * Created by Administrator on 2017/4/10.
 */

public class ContactsPresenter implements ContactsViewModel.ContactsListener{
    private ContactsView view;
    private ContactsViewModel viewModel;

    public ContactsPresenter(ContactsView view){
        this.view = view;
        viewModel = new ContactsViewModel(this);
    }

    public void getContacts(){
        viewModel.getContacts();
    }

    @Override
    public void getSuccess(List<ContactsModel> result) {
        view.getContactsSuccess(result);
    }

    @Override
    public void getFail(String errMsg) {
        view.getContactsFail(errMsg);
    }
}
