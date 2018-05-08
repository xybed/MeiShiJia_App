package com.mumu.meishijia.viewmodel.mine;

/**
 * 忘记密码的viewModel
 * Created by Administrator on 2017/3/29.
 */

public class ForgetPwdViewModel {

    public ForgetPwdViewModel(ForgetPwdListener listener){
        this.listener = listener;
    }

    public void modifyPwd(String username, String password){
//        HttpRequestParams params = new HttpRequestParams();
//        params.put("username", username);
//        params.put("password", password);
//        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
//        httpRetrofit.getModel(httpRetrofit.getApiService(UserApi.class, HttpUrl.ModifyPwd, params).modifyPwd(params.urlParams), "", new RetroResListener<String>() {
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

    private ForgetPwdListener listener;
    public interface ForgetPwdListener{
        void modifySuccess(String result);
        void modifyFail(String errMsg);
    }
}
