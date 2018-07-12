package com.mumu.meishijia.presenter.im;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.model.im.ContactsDetail;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.view.im.ContactsDetailView;
import com.mumu.meishijia.viewmodel.im.ContactsDetailViewModel;

/**
 * 联系人详细资料的presenter
 * Created by 77 on 2017/4/10.
 */

public class ContactsDetailPresenter extends BasePresenter<ContactsDetailView, ContactsDetailViewModel>{

    public ContactsDetailPresenter(BaseView view) {
        super(view);
    }

    public void getContactsDetail(Integer friendId){
        Integer userId = MyApplication.getInstance().getUser().getId();
        model.getContactsDetail(userId, friendId)
                .subscribe(new RxObserver<ContactsDetail>() {
                    @Override
                    protected void onSuccess(ContactsDetail contactsDetail) {
                        if(view != null)
                            view.getSuccess(contactsDetail);
                    }
                });
    }

}
