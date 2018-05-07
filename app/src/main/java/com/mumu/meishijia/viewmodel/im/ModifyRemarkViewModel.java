package com.mumu.meishijia.viewmodel.im;

import com.mumu.meishijia.api.im.ImService;
import com.mumu.meishijia.http.HttpRequestParams;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.http.RetroResListener;

/**
 * 备注信息的viewModel
 * Created by Administrator on 2017/4/11.
 */

public class ModifyRemarkViewModel {

    public ModifyRemarkViewModel(ModifyRemarkListener listener){
        this.listener = listener;
    }

    public void modifyRemark(int userId, int friendId, String remark){
//        HttpRequestParams params = new HttpRequestParams();
//        params.put("user_id", userId);
//        params.put("friend_id", friendId);
//        params.put("remark", remark);
//        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
//        httpRetrofit.getModel(httpRetrofit.getApiService(ImService.class, HttpUrl.ModifyRemark, params).modifyRemark(params.urlParams), "", new RetroResListener<String>() {
//            @Override
//            protected void onSuccess(String result) {
//                if(listener != null)
//                    listener.modifySuccess(result);
//            }
//
//            @Override
//            protected void onFailure(String errMsg) {
//                if(listener != null)
//                    listener.modifyFail(errMsg);
//            }
//        });
    }

    private ModifyRemarkListener listener;
    public interface ModifyRemarkListener{
        void modifySuccess(String result);
        void modifyFail(String errMsg);
    }
}
