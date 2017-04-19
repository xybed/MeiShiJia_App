package com.mumu.meishijia.view.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.im.ChatAdapter;
import com.mumu.meishijia.adapter.im.ImPagerAdapter;
import com.mumu.meishijia.im.IMConstant;
import com.mumu.meishijia.im.model.BaseMessage;
import com.mumu.meishijia.im.model.MessageFactory;
import com.mumu.meishijia.im.model.MsgContentModel;
import com.mumu.meishijia.im.model.MsgDataModel;
import com.mumu.meishijia.im.model.MsgJsonModel;
import com.mumu.meishijia.model.im.ChatRealmModel;
import com.mumu.meishijia.presenter.im.ChatPresenter;
import com.mumu.meishijia.view.BaseActivity;

import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import lib.realm.MyRealm;
import lib.utils.ToastUtil;
import lib.widget.PagerIndicator;

public class ChatActivity extends BaseActivity implements ChatView,View.OnClickListener{
    public static final String FRIEND_ID = "friend_id";
    public static final String PRINCIPLE_ID = "principle_id";

    @BindView(R.id.txt_remark)
    TextView txtRemark;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.img_sound)
    ImageView imgSound;
    @BindView(R.id.img_keyboard)
    ImageView imgKeyboard;
    @BindView(R.id.edit_msg)
    EditText editMsg;
    @BindView(R.id.txt_speak)
    TextView txtSpeak;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.btn_send_msg)
    Button btnSendMsg;
    @BindView(R.id.llay_more)
    LinearLayout llayMore;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.pager_indicator)
    PagerIndicator pagerIndicator;

    private ChatPresenter presenter;

    private int friend_id;
    private int principle_id;
    //加号部分的viewPager的adapter
    private ImPagerAdapter pagerAdapter;
    //点击加号出现的布局，有两部分，设置成成员变量，方便设置监听
    private View view1;
    private View view2;
    private WebSocketClient webSocket;

    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initUI();
        initListener();
        Intent intent = getIntent();
        friend_id = intent.getIntExtra(FRIEND_ID, 0);
        principle_id = intent.getIntExtra(PRINCIPLE_ID, 0);
        webSocket = MyApplication.getInstance().getWebSocket();
        presenter = new ChatPresenter(this);
        presenter.getMessage();
    }

    private void initUI(){
        ButterKnife.bind(this);
        List<View> viewList = new ArrayList<>();
        view1 = LayoutInflater.from(this).inflate(R.layout.layout_chat_viewpager_1, null);
        view2 = LayoutInflater.from(this).inflate(R.layout.layout_chat_viewpager_2, null);
        viewList.add(view1);
        viewList.add(view2);
        pagerAdapter = new ImPagerAdapter(this, viewList);
        viewPager.setAdapter(pagerAdapter);
        pagerIndicator.setNumber(viewList.size());

        adapter = new ChatAdapter(this);
        listView.setAdapter(adapter);
    }

    private void initListener(){
        ButterKnife.findById(view1, R.id.llay_album).setOnClickListener(this);
        ButterKnife.findById(view1, R.id.llay_take_photo).setOnClickListener(this);
        ButterKnife.findById(view1, R.id.llay_video_chat).setOnClickListener(this);
        ButterKnife.findById(view1, R.id.llay_location).setOnClickListener(this);
        ButterKnife.findById(view1, R.id.llay_red_packet).setOnClickListener(this);
        ButterKnife.findById(view1, R.id.llay_transfer_accounts).setOnClickListener(this);
        ButterKnife.findById(view1, R.id.llay_business_card).setOnClickListener(this);
        ButterKnife.findById(view1, R.id.llay_voice).setOnClickListener(this);
        ButterKnife.findById(view2, R.id.llay_my_favorite).setOnClickListener(this);
        editMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)){
                    imgAdd.setVisibility(View.GONE);
                    btnSendMsg.setVisibility(View.VISIBLE);
                }else {
                    imgAdd.setVisibility(View.VISIBLE);
                    btnSendMsg.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                pagerIndicator.move(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                pagerIndicator.setPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        llayMore.setVisibility(View.GONE);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @OnClick({R.id.btn_left, R.id.btn_friend_info, R.id.img_sound, R.id.img_keyboard, R.id.img_add, R.id.btn_send_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_friend_info:
                break;
            case R.id.img_sound:
                imgSound.setVisibility(View.GONE);
                editMsg.setVisibility(View.GONE);
                imgKeyboard.setVisibility(View.VISIBLE);
                txtSpeak.setVisibility(View.VISIBLE);
                break;
            case R.id.img_keyboard:
                imgKeyboard.setVisibility(View.GONE);
                txtSpeak.setVisibility(View.GONE);
                imgSound.setVisibility(View.VISIBLE);
                editMsg.setVisibility(View.VISIBLE);
                break;
            case R.id.img_add:
                llayMore.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_send_msg:
                sendMessage();
                break;
            //以下的资源id为more中的功能，所以在onclick注释中没有加入这些id，而通过手动设置onClick
            case R.id.llay_album:
                ToastUtil.show("相册");
                break;
            case R.id.llay_take_photo:
                ToastUtil.show("拍摄");
                break;
            case R.id.llay_video_chat:
                ToastUtil.show("视频聊天");
                break;
            case R.id.llay_location:
                ToastUtil.show("位置");
                break;
            case R.id.llay_red_packet:
                ToastUtil.show("红包");
                break;
            case R.id.llay_transfer_accounts:
                ToastUtil.show("转账");
                break;
            case R.id.llay_business_card:
                ToastUtil.show("名片");
                break;
            case R.id.llay_voice:
                ToastUtil.show("语音输入");
                break;
            case R.id.llay_my_favorite:
                ToastUtil.show("我的收藏");
                break;
        }
    }

    /**
     * 发送文本消息，构建json
     */
    private void sendMessage(){
        MsgContentModel msgContent = new MsgContentModel();
        msgContent.setText(editMsg.getText().toString());
        MsgDataModel msgData = new MsgDataModel();
        msgData.setFrom_id(MyApplication.getInstance().getUser().getPrinciple_id());
        msgData.setTo_id(principle_id);
        long time = System.currentTimeMillis();
        msgData.setTime(time);
        msgData.setMsg_content(JSON.toJSONString(msgContent));
        //其他未设置的msgData字段，交由后台设置
        MsgJsonModel msgJson = new MsgJsonModel();
        msgJson.setMsg_type(IMConstant.MSG_TYPE_TEXT);
        msgJson.setData(msgData);
        //其他未设置的msgJson字段，交由后台设置
        //先存数据库，再发送

        //存数据库
        ChatRealmModel chatRealmModel = new ChatRealmModel();
        chatRealmModel.setUser_id(MyApplication.getInstance().getUser().getId());
        chatRealmModel.setConversation_id(principle_id);
        chatRealmModel.setFrom_id(MyApplication.getInstance().getUser().getPrinciple_id());
        chatRealmModel.setTo_id(principle_id);
        chatRealmModel.setTime(time);
        chatRealmModel.setMsg_type(IMConstant.MSG_TYPE_TEXT);
        chatRealmModel.setMsg_status(IMConstant.MSG_STATUS_SEND);
        chatRealmModel.setMsg_content(JSON.toJSONString(msgContent));
        chatRealmModel.setSystem_attach(1);

        saveMessage(chatRealmModel);
        //刷新界面
        List<BaseMessage> datas = new ArrayList<>();
        datas.add(MessageFactory.productMessage(chatRealmModel));
        adapter.addData(datas);
        webSocket.send(JSON.toJSONString(msgJson));
    }

    private void saveMessage(ChatRealmModel chatRealmModel){
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        realm.beginTransaction();
        realm.insert(chatRealmModel);
        realm.commitTransaction();
    }

    @Override
    public void getMessageSuccess(List<ChatRealmModel> messageList) {
        List<BaseMessage> datas = new ArrayList<>();
        for(ChatRealmModel message : messageList){
            datas.add(MessageFactory.productMessage(message));
        }
        adapter.setData(datas);
    }
}
