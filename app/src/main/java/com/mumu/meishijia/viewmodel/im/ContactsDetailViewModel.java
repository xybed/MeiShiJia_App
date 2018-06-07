package com.mumu.meishijia.viewmodel.im;

import com.mumu.meishijia.model.im.ContactsDetailModel;

/**
 * 联系人详细资料viewModel
 * Created by Administrator on 2017/4/10.
 */

public class ContactsDetailViewModel {

    public ContactsDetailViewModel(ContactsDetailListener listener){
        this.listener = listener;
    }

    public void getContactsDetail(int friendId){
//        HttpRequestParams params = new HttpRequestParams();
//        params.put("user_id", MyApplication.getInstance().getUser().getId());
//        params.put("friend_id", friendId);
//        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
//        httpRetrofit.getModel(httpRetrofit.getApiService(ImApi.class, HttpUrl.GetContactsDetail, params).getContactsDetail(params.urlParams), "", new RetroResListener<ContactsDetailModel>() {
//            @Override
//            protected void onSuccess(ContactsDetailModel result) {
//                if(listener != null)
//                    listener.getSuccess(result);
//            }
//
//            @Override
//            protected void onFailure(String errMsg) {
//                if(listener != null)
//                    listener.getFail(errMsg);
//            }
//        });
    }

    private ContactsDetailListener listener;
    public interface ContactsDetailListener{
        void getSuccess(ContactsDetailModel result);
        void getFail(String errMsg);
    }
}
