package com.mumu.meishijia.presenter.im;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.model.im.ContactsModel;
import com.mumu.meishijia.model.im.ContactsRealmModel;
import com.mumu.meishijia.view.im.ContactsView;
import com.mumu.meishijia.viewmodel.im.ContactsViewModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import lib.realm.MyRealm;

/**
 * 联系人的presenter
 * Created by Administrator on 2017/4/10.
 */

public class ContactsPresenter implements ContactsViewModel.ContactsListener{
    private ContactsView view;
    private ContactsViewModel viewModel;
    private Realm realm;

    public ContactsPresenter(ContactsView view){
        this.view = view;
        viewModel = new ContactsViewModel(this);
        realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
    }

    public void getContacts(){
        viewModel.getContacts();
    }

    @Override
    public void getDBSuccess(RealmResults<ContactsRealmModel> contactsRealmList) {
        view.getContactsSuccess(contactsRealmList);
    }

    @Override
    public void getSuccess(List<ContactsModel> result) {
        /*
        请求下来的联系人列表，要转换成本地存储的realmModel
        存数据库
        显示到view
         */
        List<ContactsRealmModel> contactsList = new ArrayList<>();
        ContactsRealmModel realmModel;
        for(ContactsModel model : result){
            realmModel = new ContactsRealmModel();
            realmModel.setUser_id(MyApplication.getInstance().getUser().getId());
            realmModel.setFriend_id(model.getFriend_id());
            realmModel.setAvatar(model.getAvatar());
            realmModel.setRemark(model.getRemark());
            realmModel.setSort_letter(model.getSort_letter());
            realmModel.setPrinciple_id(model.getPrinciple_id());
            contactsList.add(realmModel);
        }
        realm.beginTransaction();
        realm.copyToRealm(contactsList);
        realm.commitTransaction();
        view.getContactsSuccess(contactsList);
    }

    @Override
    public void getFail(String errMsg) {
        view.getContactsFail(errMsg);
    }
}
