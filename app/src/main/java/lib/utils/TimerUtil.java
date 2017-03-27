package lib.utils;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 7mu on 2016/6/16.
 * 倒计时工具类
 */
public class TimerUtil {

    private int totalTime = 60;
    private int initTime = 60;//用于重置totalTime
    private Timer timer;
    private boolean isRunning = false;//此字段避免重复调用计时、重复取消计时
    private static final int RUNNING = 1;
    private static final int END = 0;
    private TimerListener listener;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case RUNNING:
                    if(listener != null)
                        listener.onTimerRunning(totalTime);
                    break;
                case END:
                    if(listener != null)
                        listener.onTimerEnd();
                    break;
            }
        }
    };

    public TimerUtil(int totalTime, int initTime, TimerListener listener){
        this.totalTime = totalTime;
        this.initTime = initTime;
        this.listener = listener;
    }

    public TimerUtil(TimerListener listener){
        this.listener = listener;
    }

    public void startTimer(){
        if(!isRunning){
            isRunning = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(totalTime <= 0){
                        isRunning = false;
                        totalTime = initTime;
                        timer.cancel();
                        handler.sendEmptyMessage(END);
                        return;
                    }
                    handler.sendEmptyMessage(RUNNING);
                    totalTime -= 1;
                }
            }, 0, 1000);//延迟0秒，周期1秒
        }
    }

    public void stopTimer(){
        if(isRunning && timer != null){
            isRunning = false;
            totalTime = initTime;
            timer.cancel();
            handler.sendEmptyMessage(END);
        }
    }

    public interface TimerListener{
        void onTimerRunning(int totalTime);
        void onTimerEnd();
    }
}
