package com.mumu.meishijia.view.im;

import com.mumu.meishijia.model.im.Contacts;
import com.mumu.meishijia.view.BaseView;

import java.util.List;

/**
 * 联系人的view
 * Created by Administrator on 2017/4/10.
 */

public interface ContactsView extends BaseView{
    void getContactsSuccess(List<Contacts> contactsList);
}
