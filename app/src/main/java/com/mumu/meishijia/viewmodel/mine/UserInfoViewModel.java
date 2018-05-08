package com.mumu.meishijia.viewmodel.mine;

/**
 * 个人资料的viewModel
 * Created by Administrator on 2017/3/30.
 */

public class UserInfoViewModel {

    public UserInfoViewModel(UserInfoListener listener){
        this.listener = listener;
    }

    public void modifyUserInfo(String id, String nickname, String realName,
                               String sex, String birthday, String email, String province, String city, String signature){
//        HttpRequestParams params = new HttpRequestParams();
//        params.put("id", id);
//        params.put("nickname", nickname);
//        params.put("real_name", realName);
//        params.put("sex", sex);
//        params.put("birthday", birthday);
//        params.put("email", email);
//        params.put("province", province);
//        params.put("city", city);
//        params.put("signature", signature);
//        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
//        httpRetrofit.getModel(httpRetrofit.getApiService(UserApi.class, HttpUrl.ModifyUserInfo, params).modifyUserInfo(params.urlParams), "", new RetroResListener<String>() {
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

    public void modifyAvatar(String filePath){
//        HttpRequestParams params = new HttpRequestParams();
//        params.put("id", MyApplication.getInstance().getUser().getId());
//        HttpRetrofit httpRetrofit = HttpRetrofit.getInstance();
//        MultipartBody.Part part = httpRetrofit.createImageMultipart(filePath);
//        httpRetrofit.getModel(httpRetrofit.getApiService(UserApi.class, HttpUrl.ModifyAvatar, params).modifyAvatar(part, params.urlParams), "", new RetroResListener<String>() {
//            @Override
//            protected void onSuccess(String result) {
//                if(listener != null)
//                    listener.modifyAvatarSuccess(result);
//            }
//
//            @Override
//            protected void onFailure(String errMsg) {
//                if(listener != null)
//                    listener.modifyAvatarFail(errMsg);
//            }
//        });
    }

    private UserInfoListener listener;
    public interface UserInfoListener{
        void modifySuccess(String result);
        void modifyFail(String errMsg);
        void modifyAvatarSuccess(String result);
        void modifyAvatarFail(String errMsg);
    }
}
