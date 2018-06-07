package com.mumu.meishijia.viewmodel.mine;

import com.mumu.meishijia.api.CommonApi;
import com.mumu.meishijia.api.UserApi;
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

    public Observable<String> modifyUserInfo(String id, String avatar, String nickname, String realName,
                                     String sex, String birthday, String email, String province, String city, String signature){
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("avatar", avatar);
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

    /**
     * 这里只做上传图片，并未更改数据库中的头像地址
     * 做在另外的接口里更改数据库中的地址
     * @param filePath 本地文件路径
     */
    public Observable<String> uploadImage(String filePath){
        MultipartBody.Part part = HttpRetrofit.createImageMultipart(filePath);
        return HttpRetrofit.create(CommonApi.class)
                .uploadImage(part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HttpResultFunc<String>());
    }

}
