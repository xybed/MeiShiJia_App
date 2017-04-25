package com.mumu.meishijia.view.im;

import android.os.Bundle;
import android.widget.ListView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.im.ConversationAdapter;
import com.mumu.meishijia.constant.RxBusAction;
import com.mumu.meishijia.model.im.ConversationRealmModel;
import com.mumu.meishijia.presenter.im.ConversationPresenter;
import com.mumu.meishijia.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.widget.FrameProgressLayout;

public class ConversationActivity extends BaseActivity implements ConversationView {

    @BindView(R.id.frame_progress)
    FrameProgressLayout frameProgress;
    @BindView(R.id.list_view)
    ListView listView;

    private ConversationPresenter presenter;

    private ConversationAdapter adapter;
    private List<ConversationRealmModel> conversationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        initUI();
        RxBus.get().register(this);
        presenter = new ConversationPresenter(this);
        presenter.getConversation(MyApplication.getInstance().getUser().getId());
    }

    private void initUI() {
        ButterKnife.bind(this);
        conversationList = new ArrayList<>();
        adapter = new ConversationAdapter(this, conversationList);
        listView.setAdapter(adapter);
    }

    @OnClick(R.id.btn_left)
    public void onClick() {
        finish();
    }

    @Override
    public void getSuccess(List<ConversationRealmModel> conversationList) {
        if (conversationList == null || conversationList.size() < 1){
            frameProgress.noData(getString(R.string.im_no_conversation));
            return;
        }
        frameProgress.dismiss();
        this.conversationList = conversationList;
        adapter.setData(conversationList);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(RxBusAction.ConversationList)
            }
    )
    public void rbRefreshConversationList(String s){
        presenter.getConversation(MyApplication.getInstance().getUser().getId());
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
        super.onDestroy();
    }
}
