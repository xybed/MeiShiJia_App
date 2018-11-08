package com.mumu.meishijia.view.common;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.model.LocationModel;
import com.mumu.meishijia.view.BaseFragment;
import com.mumu.meishijia.view.mine.LoginActivity;
import com.mumu.meishijia.view.order.ShoppingCartActivity;
import com.mumu.meishijia.view.product.ProductCategoryActivity;
import com.mumu.meishijia.view.product.ProductListActivity;
import com.mumu.meishijia.view.product.SearchActivity;

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

    @OnClick({R.id.llay_search_product, R.id.llay_cloth, R.id.llay_snacks, R.id.llay_kitchen,
            R.id.llay_shampoo, R.id.llay_wine, R.id.llay_guitar, R.id.llay_jia_love,
            R.id.llay_category, R.id.img_shopping_cart})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.llay_search_product:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.llay_cloth://衣服
                intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra(ProductListActivity.CATEGORY_ID, 11);
                startActivity(intent);
                break;
            case R.id.llay_snacks://零食
                intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra(ProductListActivity.CATEGORY_ID, 21);
                startActivity(intent);
                break;
            case R.id.llay_kitchen://厨具
                intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra(ProductListActivity.CATEGORY_ID, 9);
                startActivity(intent);
                break;
            case R.id.llay_shampoo://洗发沐浴
                intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra(ProductListActivity.CATEGORY_ID, 969);
                startActivity(intent);
                break;
            case R.id.llay_wine://酒水
                intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra(ProductListActivity.CATEGORY_ID, 22);
                startActivity(intent);
                break;
            case R.id.llay_guitar://吉他
                intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra(ProductListActivity.CATEGORY_ID, 744);
                startActivity(intent);
                break;
            case R.id.llay_jia_love:
//                intent = new Intent(getActivity(), ProductListActivity.class);
//                intent.putExtra(ProductListActivity.CATEGORY_ID, 11);
//                startActivity(intent);
                break;
            case R.id.llay_category:
                intent = new Intent(getActivity(), ProductCategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.img_shopping_cart:
                if(MyApplication.getInstance().isLogin()){
                    intent = new Intent(getActivity(), ShoppingCartActivity.class);
                }else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                }
                startActivity(intent);
                break;
        }
    }
}
