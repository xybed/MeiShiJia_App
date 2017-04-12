package com.mumu.meishijia.view.im;

import android.os.Bundle;
import android.widget.ListView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.im.ConversationAdapter;
import com.mumu.meishijia.model.im.ConversationRealmModel;
import com.mumu.meishijia.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConversationActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView listView;

    private ConversationAdapter adapter;
    private List<ConversationRealmModel> conversationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        initUI();
    }

    private void initUI(){
        ButterKnife.bind(this);
        conversationList = new ArrayList<>();
        adapter = new ConversationAdapter(this, conversationList);
        listView.setAdapter(adapter);
    }

    @OnClick(R.id.btn_left)
    public void onClick() {
        finish();
    }
}
