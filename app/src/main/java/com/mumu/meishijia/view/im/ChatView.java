package com.mumu.meishijia.view.im;

import com.mumu.meishijia.model.im.ChatRealmModel;

import java.util.List;

/**
 * 聊天的view
 * Created by Administrator on 2017/4/18.
 */

public interface ChatView {
    void getMessageSuccess(List<ChatRealmModel> messageList);
}
