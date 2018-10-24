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
    public static final String GetRecipe = "food/recipe";

    //上传头像
    public static final String UploadImage = "upload/image";

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
    //获取收货地址列表
    public static final String GetReceivingAddress = "addresses";
    //添加、更改收货地址
    public static final String ReceivingAddress = "address";

    //获取联系人
    public static final String GetContacts = "im/contacts";
    //获取联系人详细信息
    public static final String GetContactsDetail = "im/contactsDetail";
    //修改联系人的备注名
    public static final String ModifyRemark = "im/modifyRemark";
    //获取消息主体的信息
    public static final String GetPrincipalInfo = "im/principalInfo";

    //获取联赛排名
    public static final String GetRanking = "football/ranking";
    //获取球队球员
    public static final String GetTeam = "football/team";

    //获取商品分类
    public static final String GetProductCategory = "product/category/{id}";
    //获取商品列表
    public static final String GetProductList = "product";
    //获取商品详情
    public static final String GetProductDetail = "product/{id}";
    //搜索商品
    public static final String SearchProductList = "product/search";
}
