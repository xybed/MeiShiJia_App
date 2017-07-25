package com.mumu.meishijia.view.im;

import com.mumu.meishijia.model.im.ContactsModel;

import java.util.List;

/**
 * 联系人的view
 * Created by Administrator on 2017/4/10.
 */

public interface ContactsView {
    void getContactsSuccess(List<ContactsModel> contactsList);
    void getContactsFail(String errMsg);
}
