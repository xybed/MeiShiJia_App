package lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.model.RegionModel;

import java.util.List;

import lib.wheel.OnWheelChangedListener;
import lib.wheel.WheelView;

/**
 * 选择城市
 * @author dongmei zhang
 *@createdate 2015-2-12
 */
public class SelectCityDialog {
	
	private Context mContext;
	private Dialog mDialog;
	//选择城市操作
	TextView titleText;
	TextView cancelBtn;
	TextView confrimBtn;
	WheelView cityWheelView;
	private OnButtonClickListener onButtonClickListener;
	private SelectCityAdapter cityAdapter;
	
	/**
	 * @param onButtonClickListener
	 * 设置按钮点击的监听事件
	 */
	public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
		this.onButtonClickListener = onButtonClickListener;
	}
	
	public SelectCityDialog(Context context, List<RegionModel> list){
		
		mContext = context;
		initViews(list);
		initListener();
	}
	
	/**
	 * @param context： 环境上下文
	 */
	public SelectCityDialog(Context context, OnButtonClickListener onButtonClickListener, List<RegionModel> list){
		this(context,list);
		this.onButtonClickListener = onButtonClickListener;
	}

	/**
	 * 初始化View
	 */
	private void initViews(List<RegionModel> list) {
		LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contentView = mInflater.inflate(R.layout.dialog_select_city, null);
		titleText = (TextView) contentView.findViewById(R.id.txtv_title);
		cancelBtn = (TextView) contentView.findViewById(R.id.cancel_text);
		confrimBtn = (TextView) contentView.findViewById(R.id.confirm_text);
		cityWheelView = (WheelView) contentView.findViewById(R.id.select_wheelView);
		
		list.get(list.size()/2).setSelected(true);
		cityAdapter = new SelectCityAdapter(mContext, list);
		
		cityWheelView.setVisibleItems(list.size()); // Number of items
		cityWheelView.setWheelBackground(R.drawable.wheel_bg_holo);
		cityWheelView.setWheelForeground(R.drawable.wheel_val_holo);
//		cityWheelView.setShadowColor(0xFFFFFFFF, 0x00AAAAAA, 0x00AAAAAA);
		cityWheelView.setShadowColor(0x00000000, 0x00000000, 0x00000000);
		cityWheelView.setViewAdapter(cityAdapter);
		cityWheelView.setCurrentItem(list.size()/2);
		
		mDialog = new Dialog(mContext, R.style.custom_dialog_type);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setContentView(contentView);
		Window window = mDialog.getWindow();
	    WindowManager m = window.getWindowManager();
	    Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
	    WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
	    p.width = d.getWidth();
	    p.height = (int)(d.getHeight()*0.5);
	    window.setAttributes(p);
	    window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
		window.setWindowAnimations(R.style.comment_popwindow_anim_style);  //添加动画
	}
	
	public void showDialog(String title){
		titleText.setText(title);
		mDialog.show();
	}
	
	/**
	 * 设置监听
	 */
	private void initListener() {
		
		
		cityWheelView.addChangingListener(new OnWheelChangedListener() {
			
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				cityWheelView.setCurrentItem(newValue);
				cityAdapter.setSelect(oldValue, newValue);
			}
		});
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
				int index = cityWheelView.getCurrentItem();
				cityAdapter.setSelect(index, -1);
			}
		});
		
		confrimBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDialog.dismiss();
				int index = cityWheelView.getCurrentItem();
				cityAdapter.setSelect(index, -1);
				RegionModel current_bean = cityAdapter.getCurrent_region(index);
				if(current_bean == null)
					return;
				if(onButtonClickListener!=null){
					onButtonClickListener.backData(v, current_bean);
				}
			}
		});
	}
	
	/**
	 * @author dongmei zhang
	 * 设置按钮监听，返回选中的属性值
	 */
	public interface OnButtonClickListener{
		
		/**
		 * 返回选中的属性值
		 * @param v 点击的View 
		 * @param object 选中的值
		 */
		void backData(View v, RegionModel object);
	}
}
