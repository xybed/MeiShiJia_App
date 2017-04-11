package com.mumu.meishijia.view.im;

import com.mumu.meishijia.model.im.ContactsDetailModel;

/**
 * 联系人详细资料的view
 * Created by Administrator on 2017/4/10.
 */

public interface ContactsDetailView {
    void getSuccess(ContactsDetailModel result);
    void getFail(String errMsg);
}
