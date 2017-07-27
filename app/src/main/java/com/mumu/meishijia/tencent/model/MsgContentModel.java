package com.mumu.meishijia.tencent.model;

/**
 * 消息内容的model
 * Created by Administrator on 2017/4/18.
 */

public class MsgContentModel {
    //文本
    private String text;

    //图片
    private String img_url;

    //语音
    private String voice_url;
    private long second;

    //提示
    private String title;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getVoice_url() {
        return voice_url;
    }

    public void setVoice_url(String voice_url) {
        this.voice_url = voice_url;
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(long second) {
        this.second = second;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
