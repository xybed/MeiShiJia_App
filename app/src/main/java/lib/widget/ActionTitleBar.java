package lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mumu.meishijia.R;


/**
 * Created by lq on 2017/11/21 0021.
 */

public class ActionTitleBar extends FrameLayout{
    /**
     * 用TAG来查找Activity中的ActionTitleBar
     */
    public static final String TAG = "ActionTitleBar";

    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;

    public ActionTitleBar(@NonNull Context context) {
        this(context, null);
    }

    public ActionTitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionTitleBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(@NonNull Context context, @Nullable AttributeSet attrs){
        //设置tag用于查找
        setTag(TAG);
        View view = LayoutInflater.from(context).inflate(R.layout.view_title_bar, this, false);
        addView(view);
        imgLeft = view.findViewById(R.id.img_left);
        txtTitle = view.findViewById(R.id.txt_title);
        imgRight = view.findViewById(R.id.img_right);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ActionTitleBar);
        String title = a.getString(R.styleable.ActionTitleBar_title);
        int backgroundColor = a.getResourceId(R.styleable.ActionTitleBar_title_color, R.color.title_bar_bg_color);
        setBackgroundResource(backgroundColor);
        txtTitle.setText(title);
        a.recycle();
    }

    public ImageView getImgLeft() {
        return imgLeft;
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public ImageView getImgRight() {
        return imgRight;
    }
}
