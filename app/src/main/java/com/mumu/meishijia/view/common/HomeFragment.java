package com.mumu.meishijia.view.common;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.model.LocationModel;
import com.mumu.meishijia.view.BaseFragment;
import com.mumu.meishijia.view.product.ProductCategoryActivity;
import com.mumu.meishijia.view.product.ProductListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import lib.baidu.MyLocation;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.txt_location)
    TextView txtLocation;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initUI();
        return view;
    }

    private void initUI(){
        LocationModel locationModel = MyLocation.getInstance().getLocationData();
        txtLocation.setText(locationModel.getCity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llay_cloth, R.id.llay_snacks, R.id.llay_kitchen, R.id.llay_shampoo, R.id.llay_wine, R.id.llay_guitar, R.id.llay_jia_love, R.id.llay_category})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.llay_cloth:
                intent = new Intent(getActivity(), ProductListActivity.class);
                startActivity(intent);
                break;
            case R.id.llay_snacks:
                break;
            case R.id.llay_kitchen:
                break;
            case R.id.llay_shampoo:
                break;
            case R.id.llay_wine:
                break;
            case R.id.llay_guitar:
                break;
            case R.id.llay_jia_love:
                break;
            case R.id.llay_category:
                intent = new Intent(getActivity(), ProductCategoryActivity.class);
                startActivity(intent);
                break;
        }
    }
}
