package lib.crash;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.mumu.meishijia.BuildConfig;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;

import lib.utils.FileUtil;

/**
 * Created by 7mu on 2016/5/4.
 * 自定义的 异常处理类 , 实现了 UncaughtExceptionHandler接口
 */
public class MyCrashHandler implements UncaughtExceptionHandler {
    private static MyCrashHandler myCrashHandler;
    private Context context;
    public static boolean IM_TESTOR = false;

    private MyCrashHandler(){}
    
    public static MyCrashHandler getInstance(){
        if(myCrashHandler == null)
            myCrashHandler = new MyCrashHandler();
        return myCrashHandler;
    }

    public void init(Context context) {
        this.context = context;
        //检测是否为测试人员
        checkIsTestor();
        //获取系统默认的UncaughtException处理器
        //mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 1.获取当前程序的版本号. 版本的id
        String versioninfo = getVersionInfo();
        // 2.获取手机的硬件信息.
        String mobileInfo = getMobileInfo();
        // 3.把错误的堆栈信息 获取出来
        String errorInfo = getErrorInfo(ex);

//        if(IM_TESTOR || BuildConfig.DEBUG) {
            FileUtil.saveBug(errorInfo);
//        }

        if(!BuildConfig.DEBUG && !IM_TESTOR) {
            // 干掉当前的程序
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 验证是否为测试人员
     */
    public void checkIsTestor(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("IM_TESTOR", Context.MODE_PRIVATE); //私有数据
        //如果没有取到数据，就就代表是第一次启动
        boolean imTestor = sharedPreferences.getBoolean("IM_TESTOR", false);
        if(imTestor){
            IM_TESTOR = true;
        }
    }

    /**
     * 获取错误的信息
     *
     * @param arg1
     * @return
     */
    private String getErrorInfo(Throwable arg1) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        arg1.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }

    /**
     * 获取手机的硬件信息
     *
     * @return
     */
    private String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        // 通过反射获取系统的硬件信息
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                // 暴力反射 ,获取私有的信息
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取手机的版本信息
     *
     * @return
     */
    private String getVersionInfo() {
        try {
            PackageManager pm = this.context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(this.context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }
}
