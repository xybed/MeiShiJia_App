package com.mumu.meishijia.view.order;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.order.OrderConfirmAdapter;
import com.mumu.meishijia.constant.RxBusAction;
import com.mumu.meishijia.model.mine.ReceivingAddress;
import com.mumu.meishijia.model.order.ShoppingCart;
import com.mumu.meishijia.model.order.ShoppingCartDto;
import com.mumu.meishijia.presenter.order.OrderConfirmPresenter;
import com.mumu.meishijia.view.BaseActivity;
import com.mumu.meishijia.view.mine.ReceivingAddressActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.DensityUtil;
import lib.utils.NumberUtil;

public class OrderConfirmActivity extends BaseActivity<OrderConfirmPresenter> implements OrderConfirmView{
    public static final String SHOPPING_CART_LIST = "shopping_cart_list";

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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.txt_total_amount)
    TextView txtTotalAmount;

    private List<ShoppingCart> shoppingCartList;
    private ReceivingAddress receivingAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        ButterKnife.bind(this);
        initUI();
        registerRxBus();
        presenter.getDefaultReceivingAddress(MyApplication.getInstance().getUser().getId());
    }

    private void initUI(){
        //地址布局
        txtSelectReceivingAddress.setVisibility(View.VISIBLE);
        llayReceivingAddressInfo.setVisibility(View.GONE);

        //购买商品列表布局
        shoppingCartList = (List<ShoppingCart>) getIntent().getSerializableExtra(SHOPPING_CART_LIST);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        OrderConfirmAdapter adapter = new OrderConfirmAdapter(this);
        adapter.setData(shoppingCartList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = DensityUtil.dip2px(OrderConfirmActivity.this, 15);
            }
        });
        recyclerView.setAdapter(adapter);

        //底部布局
        double totalAmount = 0;
        for(ShoppingCart shoppingCart : shoppingCartList){
            totalAmount += NumberUtil.multiply(shoppingCart.getNum(), shoppingCart.getPrice().doubleValue());
        }
        txtTotalAmount.setText(getString(R.string.order_total_amount_placeholder, totalAmount));
    }

    @OnClick({R.id.llay_receiving_address, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llay_receiving_address://选择地址
                Intent intent = new Intent(this, ReceivingAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_submit://提交订单
                List<ShoppingCartDto> dtoList = new ArrayList<>();
                for(ShoppingCart shoppingCart : shoppingCartList){
                    ShoppingCartDto dto = new ShoppingCartDto();
                    dto.setId(shoppingCart.getId());
                    dto.setRemark(shoppingCart.getRemark());
                    dtoList.add(dto);
                }
                showLoadingDialog(getString(R.string.order_placing_order));
                presenter.placeOrder(dtoList, receivingAddress.getId());
                break;
        }
    }

    @Override
    public void getAddressSuccess(ReceivingAddress receivingAddress) {
        refreshReceivingAddressInfo(receivingAddress);
    }

    @Override
    public void orderSuccess(Integer id) {
        dismissLoadingDialog();
        Logger.d("订单id："+id);
    }

    @Subscribe(
        thread = EventThread.MAIN_THREAD,
        tags = {
                @Tag(RxBusAction.ReceivingAddressData)
        }
    )
    public void refreshReceivingAddressInfo(ReceivingAddress receivingAddress){
        this.receivingAddress = receivingAddress;
        txtSelectReceivingAddress.setVisibility(View.GONE);
        llayReceivingAddressInfo.setVisibility(View.VISIBLE);
        txtName.setText(receivingAddress.getName());
        txtPhone.setText(receivingAddress.getPhone());
        txtAddress.setText(getString(R.string.order_receiving_address_placeholder,
                receivingAddress.getProvince(), receivingAddress.getCity(), receivingAddress.getAddress()));
    }
}
