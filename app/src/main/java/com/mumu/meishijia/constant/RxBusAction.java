package com.mumu.meishijia.constant;

/**
 * RxBus的tag
 * Created by Administrator on 2017/3/29.
 */
public class RxBusAction {
    //注册、登录成功时，跳转主界面，需要刷新数据
    public static final String MineUserData = "rxMineUserData";
    //我的界面，消息的未读消息
    public static final String MineUnreadMsg = "rxMineUnreadMsg";

    //查看联系人资料、修改备注名之后，需要刷新联系人列表
    public static final String ContactsList = "rxContactsList";
    //接收到新消息、刷新会话列表界面
    public static final String ConversationList = "rxConversationList";
    //接收到新消息、刷新聊天界面
    public static final String ChatList = "rxChatList";

    //添加收货地址、编辑收货地址后，刷新收货地址列表界面
    public static final String ReceivingAddressList = "rxReceivingAddressList";
    //选择收货地址后，刷新确认订单界面的地址信息
    public static final String ReceivingAddressData = "rxReceivingAddressData";

    //修改订单状态后，刷新订单列表
    public static final String OrderList = "rxOrderList";
}
