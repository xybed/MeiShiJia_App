package com.mumu.meishijia.adapter.im;

import android.content.Context;
import android.view.View;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.BaseMyAdapter;

/**
 * 聊天消息的adapter
 * Created by Administrator on 2017/4/17.
 */

public class ChatAdapter extends BaseMyAdapter<String>{

    public ChatAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_chat_item;
    }

    @Override
    public Object initView(int position, View convertView) {
        return null;
    }

    @Override
    public void fillView(int position, View convertView, Object mHolder) {

    }
}
