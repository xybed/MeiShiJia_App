package com.mumu.meishijia.constant;

/**
 * RxBus的tag
 * Created by Administrator on 2017/3/29.
 */
public class RxBusAction {
    //注册、登录成功时，跳转主界面，需要刷新数据
    public static final String MineUserData = "rxBusMineUserData";
    //查看联系人资料、修改备注名之后，需要刷新联系人列表
    public static final String ContactsList = "rxContactsList";
    //接收到新消息、刷新聊天界面
    public static final String ChatList = "rxChatList";
}