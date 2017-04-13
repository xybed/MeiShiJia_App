package lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mumu.meishijia.R;

/**
 * Created by 7mu on 2016/7/6.
 * 和轮播图配合用的移动圆点
 */
public class PagerIndicator extends View {
    private Paint selectPaint;//选中的画笔
    private Paint unselectPaint;//未选中的画笔
    private Paint strokePaint;//外圈实线画笔
    private int selectColor;//选中颜色
    private int unselectColor;//未选中颜色
    private float radius;//半径
    private float gap;//间距
    private int number;//圆的个数
    private int offset;//移动的圆的偏移量

    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaint();
    }

    /**
     * 给属性赋上默认值
     * @param context context
     * @param attrs attrs
     */
    private void initAttrs(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PagerIndicator);
        selectColor = typedArray.getColor(R.styleable.PagerIndicator_selectColor, getResources().getColor(R.color.theme_color));
        unselectColor = typedArray.getColor(R.styleable.PagerIndicator_unselectColor, 0x77f2f2f2);
        radius = typedArray.getDimension(R.styleable.PagerIndicator_radius, 10);
        gap = typedArray.getDimension(R.styleable.PagerIndicator_gap, 16);
    }

    /**
     * 初始化各画笔的颜色和属性
     */
    private void initPaint(){
        selectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        selectPaint.setColor(selectColor);

        unselectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        unselectPaint.setColor(unselectColor);

        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(0xff808080);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //第一个圆距画布的x轴距离
        int cx = (int) (getWidth() / 2 - (radius + 0.5 * gap) * (number - 1));
        //第一个圆距画布的y轴距离
        int cy = getHeight() / 2;
        //循环计算每个圆的圆心距画布的距离，并画出
        if(number > 1){//在大于1个圆的时候，才有必要用圆来标明图片的位置
            for(int i=0;i<number;i++){
                canvas.drawCircle(cx + i * (2 * radius + gap), cy, radius, unselectPaint);
                canvas.drawCircle(cx + i * (2 * radius + gap), cy, radius, strokePaint);
            }
            canvas.drawCircle(cx + offset, cy, radius, selectPaint);
        }
        super.onDraw(canvas);
    }

    /**
     * 在viewpager移动时，时刻画出圆的偏移动画
     * 这里主要是计算偏移量
     * @param position viewpager的position
     * @param positionOffset 这个参数貌似是圆心之间的距离偏移比，值在0-1之间，移到一个圆至另一个圆的中间时，就为0.5
     */
    public void move(int position, float positionOffset){
        if(number != 0)
            position %= number;
        if(position == (number - 1) && positionOffset != 0)
            return;
        //距离第一个圆的偏移量，为第position个圆距第一个圆的距离加上，每个圆心之间距离positionOffset比
        offset = (int) ((position + positionOffset) * (2 * radius + gap));
        invalidate();
    }

    /**
     * 在viewpager移动后，画出当前圆应该在的位置
     * 这里主要是计算偏移量
     * @param position viewpager的position
     */
    public void setPosition(int position){
        if(number != 0)
            position %= number;
        offset = (int) (position * (2 * radius + gap));
        invalidate();
    }

    /**
     * 暴露给外部的方法，让外部可以动态设置数量
     * (这里直接传数量是因为可能PagerIndicator不一定会和ViewPager一起使用，还有GridView等其他情况，这样写更通用)
     * @param number number
     */
    public void setNumber(int number){
        this.number = number;
    }
}
