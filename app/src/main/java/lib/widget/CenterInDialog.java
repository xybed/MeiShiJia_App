package lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mumu.meishijia.R;

/**
 * Created by 李琪 on 2016/4/20.
 * 从中间出现的对话框，用于通用的中间浮现，使用时外部传入view
 */
public class CenterInDialog {
    private Dialog mDialog;

    public CenterInDialog(Context context, View contentView, boolean cancelable, boolean otoCancelable){
        if (context == null)
            return;
        mDialog = new Dialog(context, R.style.custom_dialog_type);
        mDialog.setContentView(contentView);
        mDialog.setCancelable(cancelable);
        mDialog.setCanceledOnTouchOutside(otoCancelable);
        Window window = mDialog.getWindow();
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = d.getWidth();
//        p.height = (int)(d.getHeight()*0.5);
        window.setAttributes(p);
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.center_popwindow_anim_style);  //添加动画
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void show(){
        if (mDialog != null && !mDialog.isShowing()){
            mDialog.show();
        }
    }

    /**
     * 动态设置动画
     * @param styleResId 动画id
     */
    public void show(int styleResId){
        if(mDialog != null && !mDialog.isShowing()){
            Window window = mDialog.getWindow();
            window.setWindowAnimations(styleResId);
            mDialog.show();
        }
    }
}
