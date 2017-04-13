package com.mumu.meishijia.view.im;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.im.ImPagerAdapter;
import com.mumu.meishijia.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.widget.PagerIndicator;

public class ChatActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initUI();
    }

    private void initUI(){
        ButterKnife.bind(this);
        List<View> viewList = new ArrayList<>();
        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_chat_viewpager_1, null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.layout_chat_viewpager_2, null);
        viewList.add(view1);
        viewList.add(view2);
        pagerAdapter = new ImPagerAdapter(this, viewList);
        viewPager.setAdapter(pagerAdapter);
        pagerIndicator.setNumber(viewList.size());
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
                llayMore.setVisibility(View.GONE);
                return true;
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
                break;
        }
    }


}
