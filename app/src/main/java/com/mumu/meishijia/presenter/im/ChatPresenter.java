package com.mumu.meishijia.presenter.im;

import com.mumu.meishijia.tencent.dbmodel.ChatRealmModel;
import com.mumu.meishijia.view.im.ChatView;
import com.mumu.meishijia.viewmodel.im.ChatViewModel;

import java.util.List;

/**
 * 聊天的presenter
 * Created by Administrator on 2017/4/18.
 */

public class ChatPresenter implements ChatViewModel.ChatListener{

    private ChatView view;
    private ChatViewModel viewModel;

    public ChatPresenter(ChatView view){
        this.view = view;
        viewModel = new ChatViewModel(this);
    }

    public void getMessage(int conversationId){
        viewModel.getMessage(conversationId);
    }

    @Override
    public void getMessageSuccess(List<ChatRealmModel> messageList) {
        view.getMessageSuccess(messageList);
    }
}
