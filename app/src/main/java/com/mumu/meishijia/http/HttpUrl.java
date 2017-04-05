package com.mumu.meishijia.http;

import com.mumu.meishijia.BuildConfig;

/**
 * api接口
 * Created by 7mu on 2016/5/12.
 */
public class HttpUrl {
    public static final String BASE_API_URL = BuildConfig.Base_Api_Url;
    public static final String TOKEN_KEY = "MeiShiJia";

    //获取菜谱分类
    public static final String Get_Recipe = "food/recipe";
    //注册
    public static final String Register = "user/register";
    //登录
    public static final String Login = "user/login";
    //退出登录
    public static final String Logout = "user/logout";
    //修改密码
    public static final String ModifyPwd = "user/modifyPwd";
    //修改个人信息
    public static final String ModifyUserInfo = "user/modifyUserInfo";
    //修改头像
    public static final String ModifyAvatar = "user/modifyAvatar";
}
