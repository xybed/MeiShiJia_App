package com.mumu.meishijia.viewmodel.im;

import io.realm.Realm;
import lib.realm.MyRealm;

/**
 * 联系人的viewModel
 * Created by Administrator on 2017/4/7.
 */

public class ContactsViewModel {
    private Realm realm;

    public ContactsViewModel(ContactsListener listener){
        this.listener = listener;
        realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
    }

    public void getContacts(){

    }

    private ContactsListener listener;
    public interface ContactsListener{}
}
