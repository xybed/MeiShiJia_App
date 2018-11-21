package com.mumu.meishijia.view.order;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.order.OrderAdapter;
import com.mumu.meishijia.constant.OrderStatus;
import com.mumu.meishijia.model.order.Order;
import com.mumu.meishijia.presenter.order.OrderDetailPresenter;
import com.mumu.meishijia.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.DensityUtil;
import lib.utils.StringUtil;

public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements OrderDetailView{
    public static final String ORDER_ID = "order_id";

    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.txt_true_pay_amount)
    TextView txtTruePayAmount;
    @BindView(R.id.txt_total_amount)
    TextView txtTotalAmount;
    @BindView(R.id.txt_order_number)
    TextView txtOrderNumber;
    @BindView(R.id.txt_create_time)
    TextView txtCreateTime;
    @BindView(R.id.txt_pay_time)
    TextView txtPayTime;
    @BindView(R.id.llay_pay_time)
    LinearLayout llayPayTime;
    @BindView(R.id.txt_delivery_time)
    TextView txtDeliveryTime;
    @BindView(R.id.llay_delivery_time)
    LinearLayout llayDeliveryTime;
    @BindView(R.id.txt_deal_time)
    TextView txtDealTime;
    @BindView(R.id.llay_deal_time)
    LinearLayout llayDealTime;
    @BindView(R.id.txt_pay)
    TextView txtPay;
    @BindView(R.id.txt_cancel_order)
    TextView txtCancelOrder;
    @BindView(R.id.txt_confirm_of_receive)
    TextView txtConfirmOfReceive;
    @BindView(R.id.txt_refund)
    TextView txtRefund;
    @BindView(R.id.txt_comment)
    TextView txtComment;
    @BindView(R.id.txt_delete_order)
    TextView txtDeleteOrder;
    
    private int orderId;
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initUI();
        showFrameProgress();
        presenter.getOrderDetail(orderId);
    }
    
    private void initUI(){
        orderId = getIntent().getIntExtra(ORDER_ID, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new OrderAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = DensityUtil.dip2px(OrderDetailActivity.this, 10);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickRefresh() {
        presenter.getOrderDetail(orderId);
    }

    @OnClick({R.id.txt_pay, R.id.txt_cancel_order, R.id.txt_confirm_of_receive, R.id.txt_refund, R.id.txt_comment, R.id.txt_delete_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_pay:
                break;
            case R.id.txt_cancel_order:
                break;
            case R.id.txt_confirm_of_receive:
                break;
            case R.id.txt_refund:
                break;
            case R.id.txt_comment:
                break;
            case R.id.txt_delete_order:
                break;
        }
    }

    @Override
    public void getSuccess(Order order) {
        dismissFrameProgress();
        //收货地址
        txtName.setText(order.getReceivingAddress().getName());
        txtPhone.setText(order.getReceivingAddress().getPhone());
        txtAddress.setText(getString(R.string.order_receiving_address_placeholder, 
                order.getReceivingAddress().getProvince(), order.getReceivingAddress().getCity(), order.getReceivingAddress().getAddress()));
        
        //商品列表
        adapter.setData(order.getProducts());
        
        //费用
        txtTruePayAmount.setText(getString(R.string.product_price_placeholder, order.getPayAmount().doubleValue()));
        txtTotalAmount.setText(getString(R.string.product_price_placeholder, order.getPayAmount().doubleValue()));
        
        //订单信息
        txtOrderNumber.setText(order.getOrderNumber());
        txtCreateTime.setText(order.getCreateTime());
        if(StringUtil.isEmpty(order.getPayTime())){
            llayPayTime.setVisibility(View.GONE);
        }else {
            llayPayTime.setVisibility(View.VISIBLE);
            txtPayTime.setText(order.getPayTime());
        }
        if(StringUtil.isEmpty(order.getDeliveryTime())){
            llayDeliveryTime.setVisibility(View.GONE);
        }else {
            llayDeliveryTime.setVisibility(View.VISIBLE);
            txtDeliveryTime.setText(order.getDeliveryTime());
        }
        if(StringUtil.isEmpty(order.getDealTime())){
            llayDealTime.setVisibility(View.GONE);
        }else {
            llayDealTime.setVisibility(View.VISIBLE);
            txtDealTime.setText(order.getDealTime());
        }
        
        //按钮显示
        if(order.getStatus().intValue() == OrderStatus.WAIT_PAY.getCode()){
            txtPay.setVisibility(View.VISIBLE);
            txtCancelOrder.setVisibility(View.VISIBLE);
            txtConfirmOfReceive.setVisibility(View.GONE);
            txtRefund.setVisibility(View.GONE);
            txtComment.setVisibility(View.GONE);
            txtDeleteOrder.setVisibility(View.GONE);
        }else if(order.getStatus().intValue() == OrderStatus.WAIT_SEND.getCode()){
            txtPay.setVisibility(View.GONE);
            txtCancelOrder.setVisibility(View.GONE);
            txtConfirmOfReceive.setVisibility(View.GONE);
            txtRefund.setVisibility(View.VISIBLE);
            txtComment.setVisibility(View.GONE);
            txtDeleteOrder.setVisibility(View.GONE);
        }else if(order.getStatus().intValue() == OrderStatus.WAIT_DELIVERY.getCode()){
            txtPay.setVisibility(View.GONE);
            txtCancelOrder.setVisibility(View.GONE);
            txtConfirmOfReceive.setVisibility(View.VISIBLE);
            txtRefund.setVisibility(View.VISIBLE);
            txtComment.setVisibility(View.GONE);
            txtDeleteOrder.setVisibility(View.GONE);
        }else if(order.getStatus().intValue() == OrderStatus.WAIT_COMMENT.getCode()){
            txtPay.setVisibility(View.GONE);
            txtCancelOrder.setVisibility(View.GONE);
            txtConfirmOfReceive.setVisibility(View.GONE);
            txtRefund.setVisibility(View.GONE);
            txtComment.setVisibility(View.VISIBLE);
            txtDeleteOrder.setVisibility(View.VISIBLE);
        }else if(order.getStatus().intValue() == OrderStatus.REFUND.getCode()){
            txtPay.setVisibility(View.GONE);
            txtCancelOrder.setVisibility(View.GONE);
            txtConfirmOfReceive.setVisibility(View.GONE);
            txtRefund.setVisibility(View.GONE);
            txtComment.setVisibility(View.GONE);
            txtDeleteOrder.setVisibility(View.VISIBLE);
        }else if(order.getStatus().intValue() == OrderStatus.SUCCESS.getCode()){
            txtPay.setVisibility(View.GONE);
            txtCancelOrder.setVisibility(View.GONE);
            txtConfirmOfReceive.setVisibility(View.GONE);
            txtRefund.setVisibility(View.GONE);
            txtComment.setVisibility(View.GONE);
            txtDeleteOrder.setVisibility(View.VISIBLE);
        }else {
            txtPay.setVisibility(View.GONE);
            txtCancelOrder.setVisibility(View.GONE);
            txtConfirmOfReceive.setVisibility(View.GONE);
            txtRefund.setVisibility(View.GONE);
            txtComment.setVisibility(View.GONE);
            txtDeleteOrder.setVisibility(View.GONE);
        }
    }
}
