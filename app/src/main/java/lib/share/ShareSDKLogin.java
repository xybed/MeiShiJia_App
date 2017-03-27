package lib.share;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import lib.utils.MyLogUtil;
import lib.utils.ToastUtil;

/**
 * Created by 7mu on 2016/5/23.
 * 实现PlatformActionListener是授权后的回调，把授权信息带回来
 */
public class ShareSDKLogin implements Handler.Callback, PlatformActionListener{
    private static final int MSG_SMSSDK_CALLBACK = 1;
    private static final int MSG_AUTH_CANCEL = 2;
    private static final int MSG_AUTH_ERROR= 3;
    private static final int MSG_AUTH_COMPLETE = 4;

    private Context context;
    private SMSListener listener;

    public ShareSDKLogin(Context context){
        ShareSDK.initSDK(context);
        this.context = context;
    }

    /**
     * 短信注册需要的构造器
     * @param context 上下文
     * @param listener 短信向外的回调
     */
    public ShareSDKLogin(Context context, SMSListener listener){
        this(context);
        this.listener = listener;
        //初始化短信sdk，appkey，appsecret
        SMSSDK.initSDK(context, "1c753ad9824ae", "968446cf7dbe50dc25659a1ecb1362c9");
        EventHandler eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object object) {
                Message message = new Message();
                message.what = MSG_SMSSDK_CALLBACK;
                message.arg1 = event;
                message.arg2 = result;
                message.obj = object;
                UIHandler.sendMessage(message, ShareSDKLogin.this);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    public void authorize(Platform platform){
        //设置false表示使用SSO授权方式，有客户端的都会优先启用客户端授权，没客户端的则仍然使用网页版进行授权
        //需要注意的是，新浪微博需要应用过了新浪的审核
        platform.SSOSetting(true);
        platform.setPlatformActionListener(this);
        platform.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if(i == Platform.ACTION_USER_INFOR){
            login(platform, hashMap);
        }
        if(platform.isValid()){
            platform.removeAccount();
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        if(i == Platform.ACTION_USER_INFOR){
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        throwable.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        if(i == Platform.ACTION_USER_INFOR){
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }

    private void login(Platform platform, HashMap<String, Object> hashMap){
        String platname = platform.getDb().getPlatformNname();
        /*
         * TODO 组建一些想要的数据传给后台
         */
        MyLogUtil.e("http", platname);
        MyLogUtil.e("http", platform.getDb().getToken());
        MyLogUtil.e("http", platform.getDb().getUserIcon());
        MyLogUtil.e("http", platform.getDb().getUserGender());
        MyLogUtil.e("http", platform.getDb().getUserId());
        MyLogUtil.e("http", platform.getDb().getUserName());
        UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_AUTH_CANCEL:
                ToastUtil.show("取消授权");
                break;
            case MSG_AUTH_ERROR:
                ToastUtil.show("授权失败");
                break;
            case MSG_AUTH_COMPLETE:
                //TODO 向后台发起请求传送数据
                break;
            case MSG_SMSSDK_CALLBACK://短信的回调
                if(msg.arg2 == SMSSDK.RESULT_ERROR){
                    if(listener != null)
                        listener.smsError();
                    ToastUtil.show("操作失败");
                }else{
                    switch (msg.arg1){
                        case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                            ToastUtil.show("验证码已经发送");
                            break;
                        case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                            if(listener != null)
                                listener.submitSuccess();
                            ToastUtil.show("验证成功");
                            break;
                    }
                }
                break;
        }
        return false;
    }

    public interface SMSListener{
        void submitSuccess();//验证成功
        void smsError();//短信失败
    }
}
