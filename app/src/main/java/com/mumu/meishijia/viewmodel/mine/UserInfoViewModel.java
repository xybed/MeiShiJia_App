package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.api.mine.UserService;
import com.mumu.meishijia.http.HttpRequestParams;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.http.RetroResListener;

/**
 * 个人资料的viewModel
 * Created by Administrator on 2017/3/30.
 */

public class UserInfoViewModel {

    public UserInfoViewModel(UserInfoListener listener){
        this.listener = listener;
    }

    public void modifyUserInfo(String id, String nickname, String realName,
                               String sex, String birthday, String email, String city){
        HttpRequestParams params = new HttpRequestParams();
        params.put("id", id);
        params.put("nickname", nickname);
        params.put("real_name", realName);
        params.put("sex", sex);
        params.put("birthday", birthday);
        params.put("email", email);
        params.put("city", city);
        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
        httpRetrofit.getModel(httpRetrofit.getApiService(UserService.class, HttpUrl.ModifyUserInfo, params).modifyUserInfo(params.urlParams), "", new RetroResListener<String>() {
            @Override
            protected void onSuccess(String result) {
                if(listener != null)
                    listener.modifySuccess(result);
            }

            @Override
            protected void onFailure(String errMsg) {
                if(listener != null)
                    listener.modifyFail(errMsg);
            }
        });
    }

    private UserInfoListener listener;
    public interface UserInfoListener{
        void modifySuccess(String result);
        void modifyFail(String errMsg);
    }
}
