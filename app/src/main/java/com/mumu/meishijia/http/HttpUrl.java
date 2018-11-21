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
    public static final String UploadImage = "msj-user/upload/image";

    //注册
    public static final String Register = "msj-user/user/register";
    //登录
    public static final String Login = "msj-user/user/login";
    //退出登录
    public static final String Logout = "msj-user/user/logout";
    //修改密码
    public static final String ModifyPwd = "msj-user/user/modifyPwd";
    //修改个人信息
    public static final String ModifyUserInfo = "msj-user/user/modifyUserInfo";
    //获取收货地址列表
    public static final String GetReceivingAddress = "msj-order/addresses";
    //添加、更改收货地址、获取默认地址
    public static final String ReceivingAddress = "msj-order/address";
    //删除收货地址
    public static final String DeleteReceivingAddress = "msj-order/addresses/{id}";

    //获取联系人
    public static final String GetContacts = "msj-user/im/contacts";
    //获取联系人详细信息
    public static final String GetContactsDetail = "msj-user/im/contactsDetail";
    //修改联系人的备注名
    public static final String ModifyRemark = "msj-user/im/modifyRemark";
    //获取消息主体的信息
    public static final String GetPrincipalInfo = "msj-user/im/principalInfo";

    //获取联赛排名
    public static final String GetRanking = "msj-football/football/ranking";
    //获取球队球员
    public static final String GetTeam = "msj-football/football/team";

    //获取商品分类
    public static final String GetProductCategory = "msj-product/product/category/{id}";
    //获取商品列表
    public static final String GetProductList = "msj-product/product";
    //获取商品详情
    public static final String GetProductDetail = "msj-product/product/{id}";
    //搜索商品
    public static final String SearchProductList = "msj-product/product/search";

    //获取购物车列表
    public static final String GetShoppingCart = "msj-order/shopping/carts";
    //添加购物车、修改购物车、删除购物车
    public static final String ShoppingCart = "msj-order/shopping/cart";
    //清除失效商品
    public static final String ClearShoppingCart = "msj-order/shopping/cart/invalid";
    //下单、修改订单状态
    public static final String Order = "msj-order/order";
    //获取订单列表
    public static final String Orders = "msj-order/orders";
    //订单详情
    public static final String OrderDetail = "msj-order/orders/{id}";
}
