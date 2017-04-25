package com.mumu.meishijia.viewmodel.im;

import com.mumu.meishijia.model.im.ConversationRealmModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import lib.realm.MyRealm;

/**
 * 会话列表的viewModel
 * Created by Administrator on 2017/4/25.
 */

public class ConversationViewModel {

    public ConversationViewModel(ConversationListener listener){
        this.listener = listener;
    }

    public void getConversation(int userId){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        RealmResults<ConversationRealmModel> conversationList = realm.where(ConversationRealmModel.class).equalTo("user_id", userId).findAll();
        if(listener != null)
            listener.getSuccess(conversationList);
    }

    private ConversationListener listener;
    public interface ConversationListener{
        void getSuccess(List<ConversationRealmModel> conversationList);
    }
}
