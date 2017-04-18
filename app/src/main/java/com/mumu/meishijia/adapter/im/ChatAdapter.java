package com.mumu.meishijia.adapter.im;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseMyAdapter;
import com.mumu.meishijia.im.model.BaseMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 聊天消息的adapter
 * Created by Administrator on 2017/4/17.
 */

public class ChatAdapter extends BaseMyAdapter<BaseMessage> {

    public ChatAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_chat_item;
    }

    @Override
    public Object initView(int position, View convertView) {
        return new Holder(convertView);
    }

    @Override
    public void fillView(int position, View convertView, Object mHolder) {
        Holder holder = (Holder) mHolder;
        BaseMessage message = datas.get(position);
        message.showMessage(holder, context);
    }

    public static class Holder {
        @BindView(R.id.txt_time)
        public TextView txtTime;
        @BindView(R.id.txt_tip)
        public TextView txtTip;
        @BindView(R.id.llay_left_panel)
        public LinearLayout leftPanel;
        @BindView(R.id.img_friend_avatar)
        public ImageView imgFriendAvatar;
        @BindView(R.id.rlay_left_message)
        public RelativeLayout rlayLeftMessage;
        @BindView(R.id.rlay_right_panel)
        public RelativeLayout rightPanel;
        @BindView(R.id.img_avatar)
        public ImageView imgAvatar;
        @BindView(R.id.rlay_right_message)
        public RelativeLayout rlayRightMessage;
        @BindView(R.id.progress_send)
        public ProgressBar progressSend;
        @BindView(R.id.img_error)
        public ImageView imgError;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
