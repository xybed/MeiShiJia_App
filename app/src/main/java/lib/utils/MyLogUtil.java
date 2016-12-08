package lib.utils;

import android.support.compat.BuildConfig;
import android.util.Log;

import lib.crash.MyCrashHandler;

/**
 * Created by 7mu on 2016/5/17.
 * 自己封装的日志打印类，方便在调试和开发模式下打印，其他模式下不打印
 */
public class MyLogUtil {
    public static void e(String tag, String message){
        if(BuildConfig.DEBUG || MyCrashHandler.IM_TESTOR){
            Log.e(tag, message);
        }
    }

    public static void d(String tag, String message){
        if(BuildConfig.DEBUG || MyCrashHandler.IM_TESTOR){
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message){
        if(BuildConfig.DEBUG || MyCrashHandler.IM_TESTOR){
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message){
        if(BuildConfig.DEBUG || MyCrashHandler.IM_TESTOR){
            Log.w(tag, message);
        }
    }

}
