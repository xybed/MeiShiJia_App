package com.mumu.meishijia.tencent.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.adapter.im.ChatAdapter;
import com.mumu.meishijia.tencent.IMConstant;
import com.mumu.meishijia.tencent.dbmodel.ChatRealmModel;
import com.mumu.meishijia.model.im.ContactsRealmModel;
import com.mumu.meishijia.model.mine.User;

import io.realm.Realm;
import lib.realm.MyRealm;

/**
 * 基础消息模型
 * Created by Administrator on 2017/4/17.
 */

public abstract class BaseMessage {

    public Context context;
    public ChatRealmModel message;
    public MsgContentModel msgContent;

    public abstract void showMessage(ChatAdapter.Holder holder, Context context);

    public RelativeLayout getBubbleView(ChatAdapter.Holder holder){
        //TODO 时间的显示逻辑稍后再理，先隐藏
        holder.txtTime.setVisibility(View.GONE);
        holder.txtTip.setVisibility(View.GONE);

        User user = MyApplication.getInstance().getUser();
        //显示头像
        if(isSelf()){
            Glide.with(context)
                .load(user.getAvatar())
                .into(holder.imgAvatar);
        }else {
            Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
            ContactsRealmModel contact = realm.where(ContactsRealmModel.class)
                    .equalTo("user_id", user.getId())
                    .equalTo("principal_id", message.getConversation_id())
                    .findFirst();
            Glide.with(context)
                .load(contact.getAvatar())
                .into(holder.imgFriendAvatar);
        }

        if(isSelf()){
            holder.rightPanel.setVisibility(View.VISIBLE);
            holder.leftPanel.setVisibility(View.GONE);
            return holder.rlayRightMessage;
        }else {
            holder.leftPanel.setVisibility(View.VISIBLE);
            holder.rightPanel.setVisibility(View.GONE);
            return holder.rlayLeftMessage;
        }
    }

    private boolean isSelf(){
        return message.getFrom_id() == MyApplication.getInstance().getUser().getPrincipalId();
    }

    protected void showStatus(ChatAdapter.Holder holder){
        switch (message.getMsg_status()){
            case IMConstant.MSG_STATUS_FAIL://失败
                holder.imgError.setVisibility(View.VISIBLE);
                holder.progressSend.setVisibility(View.GONE);
                break;
            case IMConstant.MSG_STATUS_SUCCESS://成功
                holder.imgError.setVisibility(View.GONE);
                holder.progressSend.setVisibility(View.GONE);
                break;
            case IMConstant.MSG_STATUS_SEND://发送中
                holder.imgError.setVisibility(View.GONE);
                holder.progressSend.setVisibility(View.VISIBLE);
                break;
        }
    }

    protected void clearView(ViewGroup viewGroup){
        viewGroup.removeAllViews();
        viewGroup.setOnClickListener(null);
    }

}
