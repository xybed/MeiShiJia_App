package lib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.mumu.meishijia.R;

import lib.utils.DensityUtil;

/**
 * 一般用于右侧的字母选择
 * Created by Administrator on 2017/4/6.
 */

public class LetterSide extends View {
    private Context context;
    public static String[] b1 = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private Paint paint;
    // 选中
    private int choose = -1;

    private TextView textView;

    private OnTouchLetterChangedListener listener;

    public LetterSide(Context context) {
        this(context, null);
    }

    public LetterSide(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSide(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
    }

    private void initPaint(){
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取对应高度
        int height = getHeight();
        // 获取对应宽度
        int width = getWidth();
        // 获取每一个字母的高度
        int singleHeight = height / b1.length;
        for(int i=0;i<b1.length;i++){
            paint.setColor(context.getResources().getColor(R.color.black_a));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(DensityUtil.sp2px(context, 12));
            // 选中的状态
            if (i == choose) {
                paint.setColor(context.getResources().getColor(R.color.theme_color));
                paint.setFakeBoldText(true);
            }
            //x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(b1[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            //绘画文字的起始位置是在字的左下角
            canvas.drawText(b1[i], xPos, yPos, paint);
            //重置画笔
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float y = event.getY();
        //触摸到第几个
        int touchPos = (int) (y / getHeight() * b1.length);
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                //松手后，中间的文字要隐藏
                setBackgroundResource(R.color.transparent);
                invalidate();
                if(textView != null)
                    textView.setVisibility(GONE);
                break;
            default:
                setBackgroundResource(R.color.gray_d);
                if(touchPos != choose){
                    //如果触摸的地方和上次相同，就没必要再走一遍代码
                    if(textView != null){
                        textView.setText(b1[touchPos]);
                        textView.setVisibility(VISIBLE);
                    }
                    if(listener != null)
                        listener.onTouchLetterChanged(b1[touchPos]);
                    choose = touchPos;
                    invalidate();
                }
                break;
        }
        return true;
    }

    public void setTextView(TextView textView){
        this.textView = textView;
    }

    public void setOnTouchLetterChangedListener(OnTouchLetterChangedListener listener){
        this.listener = listener;
    }

    public interface OnTouchLetterChangedListener {
        void onTouchLetterChanged(String s);
    }
}
