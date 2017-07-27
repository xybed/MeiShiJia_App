package com.mumu.meishijia.tencent.model;

import com.alibaba.fastjson.JSON;
import com.mumu.meishijia.tencent.IMConstant;
import com.mumu.meishijia.tencent.dbmodel.ChatRealmModel;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;

/**
 * 消息工厂类
 * Created by Administrator on 2017/4/18.
 */

public class MessageFactory {

    public static TIMElem getCanShowElem(TIMMessage timMessage){
        long elemCount = timMessage.getElementCount();
        //当消息中只有一个消息元素，且消息类型不等于自定义的，就可以显示
        //自定义消息有个system_attach字段，判断消息是否可以显示
        if(elemCount <= 1){
            TIMElem elem = timMessage.getElement(0);
            if(elem.getType() != TIMElemType.Custom){
                return elem;
            }else {
                //TODO 这里处理自定义消息的显示，先为null
                return null;
            }
        }
        //当有多个消息元素时（暂且不会有这种情况，手机上互发只会有一种消息类型）
        //以后有电脑端，比如QQ的可以发图文消息，就会有多种消息元素
        //TODO 多种情况的也先为null
        return null;
    }

    public static ChatRealmModel transTIMTextElem2RealmModel(TIMTextElem elem){
        //数据库的model
        ChatRealmModel chatRealmModel = new ChatRealmModel();
        //因为自己定的消息类型是int，而腾讯的是字符串枚举，所以在这里设置消息类型
        chatRealmModel.setMsg_type(IMConstant.MSG_TYPE_TEXT);

        //消息内容的model
        MsgContentModel msgContent = new MsgContentModel();
        msgContent.setText(elem.getText());

        //消息内容model转为json字符串，设置到数据库model的消息内容字段
        chatRealmModel.setMsg_content(JSON.toJSONString(msgContent));

        return chatRealmModel;
    }

    public static BaseMessage productMessage(ChatRealmModel chatRealmModel){
        switch (chatRealmModel.getMsg_type()){
            case IMConstant.MSG_TYPE_TEXT:
                return new TextMessage(chatRealmModel);

        }
        return null;
    }
}
