package com.mumu.meishijia.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.SelectCityGridAdapter;
import com.mumu.meishijia.model.LocationModel;
import com.mumu.meishijia.model.RegionModel;
import com.mumu.meishijia.view.mine.UserInfoActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.baidu.MyLocation;
import lib.utils.PullParseUtil;
import lib.utils.ToastUtil;
import lib.widget.FrameProgressLayout;
import lib.widget.SelectCityDialog;

public class SelectCityActivity extends BaseActivity {

    @BindView(R.id.txt_whole_country)
    TextView txtWholeCountry;
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
        getPermission();
        provinceList = getCityList("0");
    }

    private void initUI() {
        adapter = new SelectCityGridAdapter(this);
        gridView.setAdapter(adapter);
        adapter.setData(Arrays.asList(getResources().getStringArray(R.array.com_hot_city_array)));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = (String) adapter.getItem(position);
                setUserInfoCity(city);
            }
        });
    }

    private void getPermission(){
        if(permissionIsGet(REQ_LOCATION_PMS, Manifest.permission.ACCESS_FINE_LOCATION)){
            LocationModel locationModel = MyLocation.getInstance().getLocationData();
            txtGps.setText(locationModel.getCity());
        }
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
                setUserInfoCity(txtWholeCountry.getText().toString());
                break;
            case R.id.llay_gps:
                setUserInfoCity(txtGps.getText().toString());
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
                        setUserInfoCity(object.getName());
                    }
                });
                cityDialog.showDialog(getResources().getString(R.string.com_select_city));
                break;
        }
    }

    private void setUserInfoCity(String city){
        Intent intent = new Intent();
        intent.putExtra(UserInfoActivity.RESULT_CITY, city);
        setResult(RESULT_OK, intent);
        finish();
    }
}
