package com.mumu.meishijia.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.SelectCityGridAdapter;
import com.mumu.meishijia.model.RegionModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.PullParseUtil;
import lib.utils.ToastUtil;
import lib.widget.FrameProgressLayout;
import lib.widget.SelectCityDialog;

public class SelectCityActivity extends BaseActivity {

    @BindView(R.id.frame_progress)
    FrameProgressLayout frameProgress;
    @BindView(R.id.txt_gps)
    TextView txtGps;
    @BindView(R.id.txt_province)
    TextView txtProvince;
    @BindView(R.id.txt_city)
    TextView txtCity;
    @BindView(R.id.grid_view)
    GridView gridView;

    private SelectCityGridAdapter adapter;
    private List<RegionModel> provinceList;
    private List<RegionModel> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        ButterKnife.bind(this);
        initUI();
        provinceList = getCityList("0");
    }

    private void initUI() {
        adapter = new SelectCityGridAdapter(this);
        gridView.setAdapter(adapter);
        adapter.setData(Arrays.asList(getResources().getStringArray(R.array.com_hot_city_array)));
    }

    /**
     * 获取城市列表
     *
     * @return 城市的id
     */
    private List<RegionModel> getCityList(String parentId) {
        List<RegionModel> mSortList = new ArrayList<>();
        try {
            InputStream input = getAssets().open("bas_region.xml");
            mSortList = PullParseUtil.getCityBeans(input, parentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mSortList;
    }

    @OnClick({R.id.btn_left, R.id.txt_whole_country, R.id.llay_gps, R.id.llay_province, R.id.llay_city})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.txt_whole_country:
                break;
            case R.id.llay_gps:
                break;
            case R.id.llay_province:
                if(provinceList == null || provinceList.size() == 0)
                    return;
                SelectCityDialog provinceDialog = new SelectCityDialog(this, provinceList);
                provinceDialog.setOnButtonClickListener(new SelectCityDialog.OnButtonClickListener(){
                    @Override
                    public void backData(View v, RegionModel object) {
                        txtProvince.setText(object.getName());
                        cityList = getCityList(object.getId());
                    }
                });
                provinceDialog.showDialog(getResources().getString(R.string.com_select_province));
                break;
            case R.id.llay_city:
                //选择城市
                if(cityList == null || cityList.size() == 0){
                    ToastUtil.show(R.string.com_pls_select_province);
                    return;
                }
                SelectCityDialog cityDialog = new SelectCityDialog(this, cityList);
                cityDialog.setOnButtonClickListener(new SelectCityDialog.OnButtonClickListener(){
                    @Override
                    public void backData(View v, RegionModel object) {
                        txtCity.setText(object.getName());
                    }
                });
                cityDialog.showDialog(getResources().getString(R.string.com_select_city));
                break;
        }
    }


}
