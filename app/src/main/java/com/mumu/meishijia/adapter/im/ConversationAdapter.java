package com.mumu.meishijia.adapter.im;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mumu.meishijia.R;
import com.mumu.meishijia.tencent.dbmodel.ConversationRealmModel;

import java.util.List;

import lib.swipelayout.BaseSwipeAdapter;
import lib.utils.DateUtil;
import lib.utils.ToastUtil;

/**
 * 会话的adapter
 * Created by Administrator on 2017/4/12.
 */

public class ConversationAdapter extends BaseSwipeAdapter {

    private Context context;
    private List<ConversationRealmModel> conversationList;

    public ConversationAdapter(Context context, List<ConversationRealmModel> conversationList){
        this.context = context;
        this.conversationList = conversationList;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.layout_conversation_item, null);
    }

    @Override
    public void fillValues(int position, View convertView) {
        ConversationRealmModel model = conversationList.get(position);

        Holder holder = new Holder();
        holder.imgAvatar = (ImageView) convertView.findViewById(R.id.img_avatar);
        holder.txtRemark = (TextView) convertView.findViewById(R.id.txt_remark);
        holder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
        holder.txtMsgContent = (TextView) convertView.findViewById(R.id.txt_msg_content);
        holder.txtUnreadMsg = (TextView) convertView.findViewById(R.id.txt_unread_msg);
        holder.imgDelete = (ImageView) convertView.findViewById(R.id.img_delete);

        Glide.with(context).load(model.getAvatar()).placeholder(R.drawable.icon_default_avatar).into(holder.imgAvatar);
        if(!TextUtils.isEmpty(model.getRemark())){
            holder.txtRemark.setText(model.getRemark());
        }else {
            holder.txtRemark.setText("");
        }
        holder.txtTime.setText(DateUtil.getChatTimeStr(model.getTime()));
        if(!TextUtils.isEmpty(model.getContent())){
            holder.txtMsgContent.setText(model.getContent());
        }else {
            holder.txtMsgContent.setText("");
        }
        if(model.getUnread_msg() >= 1){
            holder.txtUnreadMsg.setText(model.getUnread_msg()+"");
            holder.txtUnreadMsg.setVisibility(View.VISIBLE);
        }else {
            holder.txtUnreadMsg.setVisibility(View.GONE);
        }

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("删除聊天");
            }
        });
    }

    @Override
    public int getCount() {
        if(conversationList == null)
            return 0;
        return conversationList.size();
    }

    @Override
    public Object getItem(int position) {
        if(conversationList == null){
            return null;
        }
        return conversationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Holder{
        ImageView imgAvatar;
        TextView txtRemark;
        TextView txtTime;
        TextView txtMsgContent;
        TextView txtUnreadMsg;
        ImageView imgDelete;
    }

    /**
     * 设置数据
     * @param list
     */
    public void setData(List<ConversationRealmModel> list) {
        if (list == null){
            this.conversationList.clear();
            notifyDataSetChanged();
            return;
        }
        this.conversationList.clear();
        for (ConversationRealmModel t : list) {
            this.conversationList.add(t);
        }
        notifyDataSetChanged();
    }

    /**
     * 增加数据
     * @param list
     */
    public void addData(List<ConversationRealmModel> list){
        if(list == null)
            return;
        this.conversationList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 获取数据
     * @return
     */
    public List<ConversationRealmModel> getData() {
        return this.conversationList;
    }

    /**
     * 清除数据
     */
    public void clearData(){
        conversationList.clear();
        notifyDataSetChanged();
    }
}
