package com.mumu.meishijia.im.model;

import android.content.Context;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.im.ChatAdapter;
import com.mumu.meishijia.tencent.dbmodel.ChatRealmModel;

import lib.utils.DensityUtil;

/**
 * 文本类型的消息
 * Created by Administrator on 2017/4/17.
 */

public class TextMessage extends BaseMessage{

    public TextMessage(ChatRealmModel message){
        this.message = message;
    }

    @Override
    public void showMessage(ChatAdapter.Holder holder, Context context) {
        this.context = context;
        RelativeLayout bubbleView = getBubbleView(holder);
        clearView(bubbleView);
        TextView tv = new TextView(context);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        tv.setTextColor(context.getResources().getColor(R.color.theme_color_black_a));
        tv.setMaxWidth(DensityUtil.dip2px(context, 250));
        int dp5 = DensityUtil.dip2px(context, 5);
        tv.setPadding(dp5, 0, dp5, 0);
        tv.setText(getMsgContent().getText());

        bubbleView.addView(tv);
        showStatus(holder);
    }

    private MsgContentModel getMsgContent(){
        return JSON.parseObject(message.getMsg_content(), MsgContentModel.class, Feature.IgnoreNotMatch, Feature.InitStringFieldAsEmpty);
    }
}
