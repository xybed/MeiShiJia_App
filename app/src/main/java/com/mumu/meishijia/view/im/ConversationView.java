package com.mumu.meishijia.view.im;

import com.mumu.meishijia.model.im.ConversationRealmModel;

import java.util.List;

/**
 * 会话列表
 * Created by Administrator on 2017/4/25.
 */

public interface ConversationView {
    void getSuccess(List<ConversationRealmModel> conversationList);
}
