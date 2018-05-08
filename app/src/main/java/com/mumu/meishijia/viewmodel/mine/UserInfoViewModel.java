package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.api.mine.UserApi;
import com.mumu.meishijia.http.HttpResultFunc;
import com.mumu.meishijia.http.HttpRetrofit;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * 个人资料的viewModel
 * Created by Administrator on 2017/3/30.
 */

public class UserInfoViewModel extends BaseViewModel{

    public Observable<String> modifyUserInfo(String id, String nickname, String realName,
                                     String sex, String birthday, String email, String province, String city, String signature){
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("nickname", nickname);
        params.put("real_name", realName);
        params.put("sex", sex);
        params.put("birthday", birthday);
        params.put("email", email);
        params.put("province", province);
        params.put("city", city);
        params.put("signature", signature);
        return HttpRetrofit.create(UserApi.class)
                .modifyUserInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<String>());
    }

    public Observable<String> modifyAvatar(String filePath){
        Map<String, Integer> params = new HashMap<>();
        params.put("id", MyApplication.getInstance().getUser().getId());
        MultipartBody.Part part = HttpRetrofit.createImageMultipart(filePath);
        return HttpRetrofit.create(UserApi.class)
                .modifyAvatar(part, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<String>());
    }

}
