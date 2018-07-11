package com.mumu.meishijia.presenter.im;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.model.im.ContactsModel;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.im.ContactsView;
import com.mumu.meishijia.viewmodel.im.ContactsViewModel;

import java.util.List;

/**
 * 联系人的presenter
 * Created by Administrator on 2017/4/10.
 */

public class ContactsPresenter extends BasePresenter<ContactsView, ContactsViewModel>{
    public ContactsPresenter(BaseView view) {
        super(view);
    }

    public void getContacts(){
        int userId = MyApplication.getInstance().getUser().getId();
        model.getContacts(userId)
                .subscribe(new RxObserver<List<ContactsModel>>() {
                    @Override
                    protected void onSuccess(List<ContactsModel> contactsModels) {
                        if(view != null)
                            view.getContactsSuccess(contactsModels);
                    }
                });
    }
}
