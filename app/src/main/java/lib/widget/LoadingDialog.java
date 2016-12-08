package lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mumu.meishijia.R;

/**
 * Created by 7mu on 2016/6/28.
 * 加载的dialog
 */
public class LoadingDialog {
    private Dialog dialog;

    public LoadingDialog(Context context, String message, boolean cancelable, boolean otoCancelable){
        View view = View.inflate(context, R.layout.dialog_loading, null);
        TextView title = (TextView) view.findViewById(R.id.id_dialog_loading_msg);
        title.setText(message);
        dialog = new Dialog(context, R.style.Loading_Dialog);
        dialog.setContentView(view);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(otoCancelable);
    }

    public void show(){
        if(dialog != null && !dialog.isShowing())
            dialog.show();
    }

    public void dismiss(){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
