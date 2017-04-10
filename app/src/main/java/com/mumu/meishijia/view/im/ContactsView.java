package com.mumu.meishijia.view.im;

import com.mumu.meishijia.model.im.ContactsRealmModel;

import java.util.List;

/**
 * 联系人的view
 * Created by Administrator on 2017/4/10.
 */

public interface ContactsView {
    void getContactsSuccess(List<ContactsRealmModel> contactsList);
    void getContactsFail(String errMsg);
}
