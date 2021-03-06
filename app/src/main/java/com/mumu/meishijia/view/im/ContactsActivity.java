package com.mumu.meishijia.view.im;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.im.ContactsAdapter;
import com.mumu.meishijia.constant.RxBusAction;
import com.mumu.meishijia.model.im.Contacts;
import com.mumu.meishijia.presenter.im.ContactsPresenter;
import com.mumu.meishijia.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.widget.LetterSide;

public class ContactsActivity extends BaseActivity<ContactsPresenter> implements ContactsView{

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.txt_center)
    TextView txtCenter;
    @BindView(R.id.letter_side)
    LetterSide letterSide;

    private ContactsAdapter adapter;
    private List<Contacts> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        initUI();
        getContacts();
        registerRxBus();
    }

    private void initUI(){
        ButterKnife.bind(this);
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
        //联系人列表
        contactsList = new ArrayList<>();
        adapter = new ContactsAdapter(this, contactsList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //因为有headerView，所以这里的position从1开始
                Contacts contacts = (Contacts) adapter.getItem(position-1);
                Intent intent = new Intent(ContactsActivity.this, ContactsDetailActivity.class);
                intent.putExtra(ContactsDetailActivity.FRIEND_ID, contacts.getFriendId());
                intent.putExtra(ContactsDetailActivity.PRINCIPAL_ID, contacts.getPrincipalId());
                startActivity(intent);
            }
        });
    }

    private void getContacts(){
        showLoadingDialog(getString(R.string.com_wait));
        presenter.getContacts();
    }

    @Override
    protected void onRightButtonClick() {
        toast("加新朋友");
    }

    @Override
    public void getContactsSuccess(List<Contacts> contactsList) {
        dismissLoadingDialog();
        this.contactsList = contactsList;
        if(contactsList == null || contactsList.size() <= 0)
            return;
        adapter.setData(contactsList);
    }

    @Subscribe(
        thread = EventThread.MAIN_THREAD,
        tags = {
                @Tag(RxBusAction.ContactsList)
        }
    )
    public void rbRefreshContactsList(String s){
        presenter.getContacts();
    }

}
