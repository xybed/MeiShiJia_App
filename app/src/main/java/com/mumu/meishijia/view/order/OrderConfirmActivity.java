package com.mumu.meishijia.view.order;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.order.OrderConfirmAdapter;
import com.mumu.meishijia.model.mine.ReceivingAddress;
import com.mumu.meishijia.model.product.Product;
import com.mumu.meishijia.presenter.order.OrderConfirmPresenter;
import com.mumu.meishijia.view.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.DensityUtil;

public class OrderConfirmActivity extends BaseActivity<OrderConfirmPresenter> implements OrderConfirmView{
    public static final String PRODUCT_LIST = "product_list";

    @BindView(R.id.txt_select_receiving_address)
    TextView txtSelectReceivingAddress;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.llay_receiving_address_info)
    LinearLayout llayReceivingAddressInfo;
    @BindView(R.id.llay_receiving_address)
    LinearLayout llayReceivingAddress;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.txt_total_amount)
    TextView txtTotalAmount;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private List<Product> productList;
    private OrderConfirmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        ButterKnife.bind(this);
        initUI();
        presenter.getDefaultReceivingAddress(MyApplication.getInstance().getUser().getId());
    }

    private void initUI(){
        //地址布局
        txtSelectReceivingAddress.setVisibility(View.VISIBLE);
        llayReceivingAddressInfo.setVisibility(View.GONE);

        //购买商品列表布局
        productList = (List<Product>) getIntent().getSerializableExtra(PRODUCT_LIST);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new OrderConfirmAdapter(this);
        adapter.setData(productList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = DensityUtil.dip2px(OrderConfirmActivity.this, 10);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.llay_receiving_address, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llay_receiving_address://选择地址
                break;
            case R.id.btn_submit://提交订单
                break;
        }
    }

    @Override
    public void getAddressSuccess(ReceivingAddress receivingAddress) {
        txtSelectReceivingAddress.setVisibility(View.GONE);
        llayReceivingAddressInfo.setVisibility(View.VISIBLE);
        txtName.setText(receivingAddress.getName());
        txtPhone.setText(receivingAddress.getPhone());
        txtAddress.setText(getString(R.string.order_receiving_address_placeholder,
                receivingAddress.getProvince(), receivingAddress.getCity(), receivingAddress.getAddress()));
    }
}
