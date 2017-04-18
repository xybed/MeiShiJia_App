package com.mumu.meishijia.im.model;

import com.mumu.meishijia.im.IMConstant;
import com.mumu.meishijia.model.im.ChatRealmModel;

/**
 * 消息工厂类
 * Created by Administrator on 2017/4/18.
 */

public class MessageFactory {

    public static BaseMessage productMessage(ChatRealmModel chatRealmModel){
        switch (chatRealmModel.getMsg_type()){
            case IMConstant.MSG_TYPE_TEXT:
                return new TextMessage(chatRealmModel);

        }
        return null;
    }
}
