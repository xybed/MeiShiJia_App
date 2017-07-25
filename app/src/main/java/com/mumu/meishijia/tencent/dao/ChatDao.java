package com.mumu.meishijia.tencent.dao;

import com.mumu.meishijia.tencent.dbmodel.ChatRealmModel;

import io.realm.Realm;
import lib.realm.MyRealm;

/**
 * 有关消息的数据库处理操作
 * Created by Administrator on 2017/7/24.
 */

public class ChatDao {

    public static void insertMsg(ChatRealmModel chatRealmModel){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        realm.beginTransaction();
        realm.copyToRealm(chatRealmModel);
        realm.commitTransaction();
    }
}
