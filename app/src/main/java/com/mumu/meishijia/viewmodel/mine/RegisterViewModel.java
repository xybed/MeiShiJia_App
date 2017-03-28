package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.api.mine.UserService;
import com.mumu.meishijia.http.HttpRequestParams;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.http.HttpUrl;
import com.mumu.meishijia.http.RetroResListener;

/**
 * 注册的viewModel
 * Created by Administrator on 2017/3/28.
 */
public class RegisterViewModel {

    public RegisterViewModel(RegisterListener listener){
        this.listener = listener;
    }

    public void register(String username, String password, String verifyCode){
        HttpRequestParams params = new HttpRequestParams();
        params.put("username", username);
        params.put("password", password);
        params.put("verify_code", verifyCode);
        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
        httpRetrofit.getModel(httpRetrofit.getApiService(UserService.class, HttpUrl.Register, params).register(params.urlParams), "", new RetroResListener<String>() {
            @Override
            protected void onSuccess(String result) {
                if(listener != null)
                    listener.registerSuccess(result);
            }

            @Override
            protected void onFailure(String errMsg) {
                if(listener != null)
                    listener.registerFail(errMsg);
            }
        });
    }

    private RegisterListener listener;
    public interface RegisterListener{
        void registerSuccess(String result);
        void registerFail(String errMsg);
    }
}
