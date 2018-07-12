package com.mumu.meishijia.view.im;

import com.mumu.meishijia.model.im.ContactsDetail;
import com.mumu.meishijia.view.BaseView;

/**
 * 联系人详细资料的view
 * Created by Administrator on 2017/4/10.
 */

public interface ContactsDetailView extends BaseView{
    void getSuccess(ContactsDetail result);
}
