package com.mumu.meishijia.view.im;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.widget.LetterSide;

public class ContactsActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.txt_center)
    TextView txtCenter;
    @BindView(R.id.letter_side)
    LetterSide letterSide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ButterKnife.bind(this);
        initUI();
    }

    private void initUI(){
        letterSide.setTextView(txtCenter);
        //增加listView头部view新的朋友
        LinearLayout headerView = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_contacts_header_view, null);
        LinearLayout llayNewFriend = (LinearLayout) headerView.findViewById(R.id.llay_new_friend);
        llayNewFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 要跳转到新的朋友界面
            }
        });
        listView.addHeaderView(headerView);
    }

    @OnClick({R.id.btn_left, R.id.btn_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_add:
                break;
        }
    }
}
