package lib.widget;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.model.RegionModel;

import java.util.List;

import lib.wheel.adapters.AbstractWheelTextAdapter;

public class SelectCityAdapter extends AbstractWheelTextAdapter {
	private List<RegionModel> list;
	
	public SelectCityAdapter(Context context, List<RegionModel> list) {
		super(context, R.layout.city_holo_layout, NO_RESOURCE);
		this.list = list;
		setItemTextResource(R.id.city_name);
	}
	
	public void setSelect(int oldValue, int newValue){
		if(oldValue != -1)
			list.get(oldValue).setSelected(false);
		if(newValue != -1)
			list.get(newValue).setSelected(true);
		notifyDataChangedEvent();
	}
	
	public RegionModel getCurrent_region(int index) {
		if(list == null)
			return null;
		return list.get(index);
	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		View view = super.getItem(index, cachedView, parent);
		TextView text = (TextView) view.findViewById(getItemTextResource());
		if(list.get(index).isSelected()){
			text.setTextColor(0xFF333333);
			text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		}else{
			text.setTextColor(0xFFB3B3B3);
			text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		}
		text.setText(getItemText(index));
		return view;
	}
	
	@Override
	public int getItemsCount() {
		if(list == null)
			return 0;
		return list.size();
	}
	

	@Override
	protected CharSequence getItemText(int index) {
		if(list == null)
			return "";
		return list.get(index).getName();
	}
	
}
