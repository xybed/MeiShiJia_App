package com.mumu.meishijia.view.order;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.order.ShoppingCartAdapter;
import com.mumu.meishijia.model.order.ShoppingCart;
import com.mumu.meishijia.presenter.order.ShoppingCartPresenter;
import com.mumu.meishijia.view.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.DensityUtil;

public class ShoppingCartActivity extends BaseActivity<ShoppingCartPresenter> implements ShoppingCartView{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.txt_clear_product)
    TextView txtClearProduct;
    @BindView(R.id.rb_all_select)
    RadioButton rbAllSelect;
    @BindView(R.id.txt_total_amount)
    TextView txtTotalAmount;
    @BindView(R.id.btn_settlement)
    Button btnSettlement;

    private ShoppingCartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        initUI();
        presenter.getShoppingCart(MyApplication.getInstance().getUser().getId(), pageIndex, pageSize);
    }

    private void initUI(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ShoppingCartAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = DensityUtil.sp2px(ShoppingCartActivity.this, 10);
            }
        });
        recyclerView.setAdapter(adapter);

        //底部布局初始化
        txtTotalAmount.setText(getString(R.string.order_total_amount_placeholder, 0f));
        btnSettlement.setText(getString(R.string.order_settlement_placeholder, 0));
    }

    @OnClick({R.id.txt_clear_product, R.id.rb_all_select, R.id.btn_settlement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_clear_product:
                break;
            case R.id.rb_all_select:
                break;
            case R.id.btn_settlement:
                break;
        }
    }

    @Override
    public void getSuccess(List<ShoppingCart> shoppingCartList) {
        adapter.setData(shoppingCartList);
    }
}
