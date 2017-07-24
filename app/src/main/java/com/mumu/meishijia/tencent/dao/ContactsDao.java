package com.mumu.meishijia.tencent.dao;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.model.im.ContactsRealmModel;

import io.realm.Realm;
import lib.realm.MyRealm;

/**
 * 有关联系人(通讯录)的数据库操作
 * Created by Administrator on 2017/7/24.
 */

public class ContactsDao {

    public static ContactsRealmModel queryContacts(int principalId){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        return realm.where(ContactsRealmModel.class)
                .equalTo("user_id", MyApplication.getInstance().getUser().getId())
                .equalTo("principal_id", principalId)
                .findFirst();
    }
}
