package com.mumu.meishijia.viewmodel.im;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.api.im.ImService;
import com.mumu.meishijia.http.HttpRequestParams;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.http.RetroResListener;
import com.mumu.meishijia.model.im.ContactsModel;

import java.util.List;

/**
 * 联系人的viewModel
 * Created by Administrator on 2017/4/7.
 */

public class ContactsViewModel {

    public ContactsViewModel(ContactsListener listener){
        this.listener = listener;
    }

    public void getContacts(){
        int userId = MyApplication.getInstance().getUser().getId();
        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
        HttpRequestParams params = new HttpRequestParams();
        params.put("id", userId);
        httpRetrofit.getList(httpRetrofit.getApiService(ImService.class, HttpUrl.GetContacts, params).getContacts(params.urlParams), "", new RetroResListener<List<ContactsModel>>() {
            @Override
            protected void onSuccess(List<ContactsModel> result) {
                if(listener != null)
                    listener.getSuccess(result);
            }

            @Override
            protected void onFailure(String errMsg) {
                if(listener != null)
                    listener.getFail(errMsg);
            }
        });
    }

    private ContactsListener listener;
    public interface ContactsListener{
        void getSuccess(List<ContactsModel> result);
        void getFail(String errMsg);
    }
}
