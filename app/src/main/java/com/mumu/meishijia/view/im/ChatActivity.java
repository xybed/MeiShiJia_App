package com.mumu.meishijia.view.im;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.im.ImPagerAdapter;
import com.mumu.meishijia.view.BaseActivity;

import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import lib.utils.ToastUtil;
import lib.widget.PagerIndicator;

public class ChatActivity extends BaseActivity implements View.OnClickListener{

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

    private ImPagerAdapter pagerAdapter;
    //点击加号出现的布局，有两部分，设置成成员变量，方便设置监听
    private View view1;
    private View view2;
    private WebSocketClient webSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initUI();
        initListener();
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
                webSocket = MyApplication.getInstance().getWebSocket();
                webSocket.send(editMsg.getText().toString());
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

}
