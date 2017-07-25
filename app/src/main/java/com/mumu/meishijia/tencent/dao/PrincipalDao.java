package com.mumu.meishijia.tencent.dao;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.tencent.dbmodel.PrincipalRealmModel;

import io.realm.Realm;
import lib.realm.MyRealm;

/**
 * 有关联系人的数据库操作
 * Created by Administrator on 2017/7/25.
 */

public class PrincipalDao {

    public static void insertPrincipalInfo(PrincipalRealmModel principalRealmModel){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        realm.beginTransaction();
        realm.copyToRealm(principalRealmModel);
        realm.commitTransaction();
    }

    public static PrincipalRealmModel queryPrincipalInfo(int principalId){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        return realm.where(PrincipalRealmModel.class)
                .equalTo("user_id", MyApplication.getInstance().getUser().getId())
                .equalTo("principal_id", principalId)
                .findFirst();
    }
}
