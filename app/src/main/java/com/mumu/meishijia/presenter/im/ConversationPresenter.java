package com.mumu.meishijia.presenter.im;

import com.mumu.meishijia.tencent.dbmodel.ConversationRealmModel;
import com.mumu.meishijia.view.im.ConversationView;
import com.mumu.meishijia.viewmodel.im.ConversationViewModel;

import java.util.List;

/**
 * 会话列表的presenter
 * Created by Administrator on 2017/4/25.
 */

public class ConversationPresenter implements ConversationViewModel.ConversationListener{

    private ConversationView view;
    private ConversationViewModel viewModel;

    public ConversationPresenter(ConversationView view){
        this.view = view;
        viewModel = new ConversationViewModel(this);
    }

    public void getConversation(int userId){
        viewModel.getConversation(userId);
    }

    @Override
    public void getSuccess(List<ConversationRealmModel> conversationList) {
        view.getSuccess(conversationList);
    }
}
