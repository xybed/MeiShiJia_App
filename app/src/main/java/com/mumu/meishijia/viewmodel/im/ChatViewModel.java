package com.mumu.meishijia.viewmodel.im;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.model.im.ChatRealmModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import lib.realm.MyRealm;

/**
 * 聊天的viewModel
 * Created by Administrator on 2017/4/18.
 */

public class ChatViewModel {

    public ChatViewModel(ChatListener listener){
        this.listener = listener;
    }

    public void getMessage(int conversationId){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        RealmResults<ChatRealmModel> messageList = realm.where(ChatRealmModel.class)
                .equalTo("user_id", MyApplication.getInstance().getUser().getId())
                .equalTo("conversation_id", conversationId)
                .findAllSorted("time");
        if(messageList != null && messageList.size() > 0 && listener != null)
            listener.getMessageSuccess(messageList);
    }

    private ChatListener listener;
    public interface ChatListener{
        void getMessageSuccess(List<ChatRealmModel> messageList);
    }
}
