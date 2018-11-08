package com.mumu.meishijia.view.product;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.model.product.Product;
import com.mumu.meishijia.presenter.product.ProductDetailPresenter;
import com.mumu.meishijia.view.BaseActivity;
import com.mumu.meishijia.view.mine.LoginActivity;
import com.mumu.meishijia.view.order.OrderConfirmActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.StringUtil;
import lib.widget.BannerView;

public class ProductDetailActivity extends BaseActivity<ProductDetailPresenter> implements ProductDetailView{
    public static final String PRODUCT_ID = "product_id";

    @BindView(R.id.banner_view)
    BannerView bannerView;
    @BindView(R.id.txt_total_sale)
    TextView txtTotalSale;
    @BindView(R.id.txt_stock)
    TextView txtStock;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.txt_discount_price)
    TextView txtDiscountPrice;
    @BindView(R.id.txt_original_price)
    TextView txtOriginalPrice;
    @BindView(R.id.txt_remark)
    TextView txtRemark;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ButterKnife.bind(this);
        int productId = getIntent().getIntExtra(PRODUCT_ID, 0);
        showFrameProgress();
        presenter.getProducDetail(productId);
    }

    @OnClick({R.id.txt_add_shopping_cart, R.id.txt_buy_immediately})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.txt_add_shopping_cart:
                break;
            case R.id.txt_buy_immediately:
                if(!MyApplication.getInstance().isLogin()){
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(this, OrderConfirmActivity.class);
                    ArrayList<Product> productList = new ArrayList<>();
                    product.setNum(1);
                    productList.add(product);
                    intent.putExtra(OrderConfirmActivity.PRODUCT_LIST, productList);
                }
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getSuccess(Product product) {
        this.product = product;
        dismissFrameProgress();
        bannerView.setImgUrl(product.getImages());
        txtTotalSale.setText(getString(R.string.com_placeholder, product.getStock()));
        txtStock.setText(getString(R.string.com_placeholder, product.getStock()));
        txtName.setText(getString(R.string.product_name_placeholder, product.getName()));
        txtDescription.setText(product.getDescription());
        txtDiscountPrice.setText(getString(R.string.product_price_placeholder, product.getDiscountPrice().doubleValue()));
        txtOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if(product.getOriginalPrice().doubleValue() == product.getDiscountPrice().doubleValue()){
            txtOriginalPrice.setVisibility(View.INVISIBLE);
        }else {
            txtOriginalPrice.setVisibility(View.VISIBLE);
            txtOriginalPrice.setText(getString(R.string.product_price_placeholder, product.getOriginalPrice().doubleValue()));
        }
        if(StringUtil.isEmpty(product.getRemark())){
            txtRemark.setVisibility(View.GONE);
        }else {
            txtRemark.setVisibility(View.VISIBLE);
            txtRemark.setText(product.getRemark());
        }
    }
}
