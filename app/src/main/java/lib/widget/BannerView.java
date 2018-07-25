package lib.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.bumptech.glide.Glide;
import com.mumu.meishijia.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 77 on 2018/1/15 0015.
 */

public class BannerView extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private Context context;

    private ViewPager viewPager;//轮播图的viewpager
    private BannerPagerAdapter adapter;//viewpager的adapter
    private List<View> viewList;//每个pager要显示的view的集合
    private int currentItem;//当前viewpager的页数

    private Timer timer;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(currentItem == 0){
                viewPager.setCurrentItem(currentItem, false);//参数false代表不需要中间的动画效果
            }else {
                viewPager.setCurrentItem(currentItem);
            }
        }
    };
    private boolean isStop;//是否被用户拖拽来判断自动轮播是否停止


    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        viewList = new ArrayList<View>();//初始化viewList，保证不为空
        initUI();
    }

    /**
     * 初始化UI
     */
    private void initUI(){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_banner, this, true);
        /*
        初始化viewpager
         */
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        adapter = new BannerPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        /*
        以下这段代码是控件viewpager切换的动画，使之切换更平滑
         */
        Field field = null;
        try {
            field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(),
                    new LinearInterpolator());
            field.set(viewPager, scroller);
            scroller.setmDuration(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //用于Viewpager切换时间控制
    public class FixedSpeedScroller extends Scroller {
        private int mDuration = 1500;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }
    }

    /**
     * 自动滑动
     */
    private void autoScroll(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentItem = viewPager.getCurrentItem() + 1;
                if(currentItem >= adapter.getCount())
                    currentItem = 0;
                if(!isStop)
                    handler.sendEmptyMessage(1);
            }
        }, 2000, 4000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state){
            case ViewPager.SCROLL_STATE_DRAGGING://手势滑动，正在被用户拖拽
                isStop = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE://滑动结束，即切换完毕，空闲中
                isStop = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING://界面切换中
                isStop = false;
                break;
        }
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size() <= 1 ? viewList.size(): viewList.size() * 100;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= viewList.size();
            View view = viewList.get(position);
            /*
            在测试时，发现一个异常闪退
            IllegalStateException:The specified child already has a parent.You must call removeView() on the child's parent first.
            虽然不懂PagerAdapter的instantiateItem和destroyItem机制，但也能明白在container在addView时，这个view已经有父母了，需要让它的父母先移除view
            所以有了以下代码
             */
            ViewParent viewParent = view.getParent();
            if(viewParent != null){
                ViewGroup viewGroup = (ViewGroup) viewParent;
                viewGroup.removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            /*
            这里不加这句，因为发现有了以上代码时，在左滑回到之前浏览过的图片，view变成空白了
            试着注释这行代码，解决了空白问题。不懂为什么（应该要去弄懂PagerAdapter的instantiateItem和destroyItem机制）
             */
//            container.removeView(viewList.get(position % viewList.size()));
        }
    }

    /**
     * 以下三个方法是针对timer的优化
     * 界面重新回到前台的时候重新开始轮播
     */
    public void onResume(){
        isStop = false;
    }

    public void onPause(){
        isStop = true;
    }

    public void onDestroy(){
        if(timer != null)
            timer.cancel();
    }

    /**
     * 暴露给外部的方法，使可以设置图片的url
     * @param imgs 需要显示的图片id数组
     */
    public void setImgData(int[] imgs){
        viewList.clear();
        if(imgs == null){
            adapter.notifyDataSetChanged();
            return;
        }
        for(int i=0;i<imgs.length;i++){
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(imgs[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            viewList.add(imageView);
        }
        adapter.notifyDataSetChanged();
        autoScroll();//在设置了图片之后再定时自动轮播比较好，没有图片时也不存在轮播
    }

    public void setImgUrl(List<String> urls){
        viewList.clear();
        if(urls == null){
            adapter.notifyDataSetChanged();
            return;
        }
        for(String url : urls){
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null)
                        listener.onBannerClick();
                }
            });
            Glide.with(context).load(url).into(imageView);
            viewList.add(imageView);
        }
        adapter.notifyDataSetChanged();
        autoScroll();
    }

    public void setOnBannerClickListener(OnBannerClickListener listener){
        this.listener = listener;
    }

    private OnBannerClickListener listener;
    public interface OnBannerClickListener{
        void onBannerClick();
    }
}
