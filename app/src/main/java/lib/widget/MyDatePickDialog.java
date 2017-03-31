package lib.widget;

import android.app.DatePickerDialog;
import android.content.Context;

/**
 * Created by 7mu on 2016/3/30.
 * 为了解决在低版本，时间选择器取消时也会回调onDataSet方法的问题
 * 重写onStop方法
 */
public class MyDatePickDialog extends DatePickerDialog {
    public MyDatePickDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
    }

    public MyDatePickDialog(Context context, int theme, OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, theme, listener, year, monthOfYear, dayOfMonth);
    }

    @Override
    protected void onStop() {
    }
}
