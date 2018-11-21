package com.mumu.meishijia.view.order;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.order.ShoppingCartAdapter;
import com.mumu.meishijia.constant.ProductStatus;
import com.mumu.meishijia.model.order.ShoppingCart;
import com.mumu.meishijia.presenter.order.ShoppingCartPresenter;
import com.mumu.meishijia.view.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.DensityUtil;
import lib.utils.NumberUtil;

public class ShoppingCartActivity extends BaseActivity<ShoppingCartPresenter> implements ShoppingCartView{

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
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
    private List<ShoppingCart> shoppingCartList;
    private int productShelfSize = 0;//记录有效商品的条目数量
    private double totalAmount;//合计总价
    private int totalCount;//总商品数量
    private int selectedCount;//选中条目数量，结合productShelfSize，用于判断底部全选rb状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        initUI();
        presenter.getShoppingCart(MyApplication.getInstance().getUser().getId());
    }

    private void initUI(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ShoppingCartAdapter(this);
        adapter.setOnItemClickListener(new ShoppingCartAdapter.OnItemClickListener() {
            @Override
            public void onRbClick(double price, int num, boolean isSelected) {
                //选中一件商品
                if(isSelected){
                    totalAmount = NumberUtil.add(totalAmount, NumberUtil.multiply(price, num));
                    totalCount += num;
                    selectedCount++;
                }else {
                    totalAmount = NumberUtil.sub(totalAmount, NumberUtil.multiply(price, num));
                    totalCount -= num;
                    selectedCount--;
                }
                rbAllSelect.setChecked(selectedCount == productShelfSize);
                txtTotalAmount.setText(getString(R.string.order_total_amount_placeholder, totalAmount));
                btnSettlement.setText(isEdit ? getString(R.string.com_delete) : getString(R.string.order_settlement_placeholder, totalCount));
            }

            @Override
            public void onAddClick(double price) {
                //计算增加1件商品
                totalAmount = NumberUtil.add(totalAmount, price);
                totalCount++;
                txtTotalAmount.setText(getString(R.string.order_total_amount_placeholder, totalAmount));
                btnSettlement.setText(isEdit ? getString(R.string.com_delete) : getString(R.string.order_settlement_placeholder, totalCount));
            }

            @Override
            public void onSubClick(double price) {
                //计算减少1件商品
                totalAmount = NumberUtil.sub(totalAmount, price);
                totalCount--;
                txtTotalAmount.setText(getString(R.string.order_total_amount_placeholder, totalAmount));
                btnSettlement.setText(isEdit ? getString(R.string.com_delete) : getString(R.string.order_settlement_placeholder, totalCount));
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = DensityUtil.dip2px(ShoppingCartActivity.this, 15);
            }
        });
        recyclerView.setAdapter(adapter);

        //底部布局初始化
        txtClearProduct.setVisibility(View.GONE);
        txtTotalAmount.setText(getString(R.string.order_total_amount_placeholder, 0f));
        btnSettlement.setText(getString(R.string.order_settlement_placeholder, 0));

        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getShoppingCart(MyApplication.getInstance().getUser().getId());
            }
        });
        refreshLayout.setNoMoreData(true);
    }

    private boolean isEdit;//是否是编辑状态，初始是false

    /**
     * 1.更改是否编辑状态
     * 2.设置标题右边文字
     * 3.是否显示购物车item的加减按钮
     * 4.是否隐藏合计，结算按钮，删除按钮
     */
    @Override
    protected void onRightButtonClick() {
        isEdit = !isEdit;
        setRightTxt(isEdit ? getString(R.string.com_complete) : getString(R.string.com_edit));
        adapter.setShow(isEdit);
        adapter.notifyDataSetChanged();
        txtTotalAmount.setVisibility(isEdit ? View.GONE : View.VISIBLE);
        txtTotalAmount.setText(getString(R.string.order_total_amount_placeholder, totalAmount));
        btnSettlement.setText(isEdit ? getString(R.string.com_delete) : getString(R.string.order_settlement_placeholder, totalCount));
        if(!isEdit){
            showLoadingDialog(getString(R.string.com_wait));
            presenter.updateShoppingCart(shoppingCartList);
        }
    }

    private boolean isAllSelected;
    @OnClick({R.id.txt_clear_product, R.id.rb_all_select, R.id.btn_settlement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_clear_product:
                showLoadingDialog(getString(R.string.com_clearing));
                presenter.clearShoppingCart(MyApplication.getInstance().getUser().getId());
                break;
            case R.id.rb_all_select:
                isAllSelected = !isAllSelected;
                rbAllSelect.setChecked(isAllSelected);
                //重新计算总的价格和数量
                totalAmount = 0;
                totalCount = 0;
                selectedCount = productShelfSize;
                for(ShoppingCart shoppingCart : shoppingCartList){
                    //如果商品是上架的
                    if(shoppingCart.getStatus().intValue() == ProductStatus.SHELF.getCode()){
                        shoppingCart.setSelected(isAllSelected);
                        totalAmount = NumberUtil.add(totalAmount,
                                NumberUtil.multiply(shoppingCart.getPrice().doubleValue(), shoppingCart.getNum()));
                        totalCount += shoppingCart.getNum();
                    }
                }
                adapter.setData(shoppingCartList);
                if(!isAllSelected){
                    totalAmount = 0;
                    totalCount = 0;
                    selectedCount = 0;
                }
                txtTotalAmount.setText(getString(R.string.order_total_amount_placeholder, totalAmount));
                btnSettlement.setText(isEdit ? getString(R.string.com_delete) : getString(R.string.order_settlement_placeholder, totalCount));
                break;
            case R.id.btn_settlement:
                if(isEdit){
                    List<Integer> idList = new ArrayList<>();
                    for(ShoppingCart shoppingCart : shoppingCartList){
                        if(shoppingCart.isSelected()){
                            idList.add(shoppingCart.getId());
                        }
                    }
                    showLoadingDialog(getString(R.string.com_deleting));
                    presenter.deleteShoppingCart(idList);
                }else {
                    //跳转确认订单界面
                    ArrayList<ShoppingCart> shoppingCartArrayList = new ArrayList<>();
                    for(ShoppingCart shoppingCart : shoppingCartList){
                        if(shoppingCart.isSelected()){
                            shoppingCartArrayList.add(shoppingCart);
                        }
                    }
                    if(shoppingCartArrayList.size() > 0){
                        Intent intent = new Intent(this, OrderConfirmActivity.class);
                        intent.putExtra(OrderConfirmActivity.SHOPPING_CART_LIST, shoppingCartArrayList);
                        startActivity(intent);
                    }else {
                        toast(R.string.order_pls_select_shopping_cart_tip);
                    }
                }
                break;
        }
    }

    @Override
    public void getSuccess(List<ShoppingCart> shoppingCartList) {
        refreshLayout.finishRefresh();
        this.shoppingCartList = shoppingCartList;
        adapter.setData(shoppingCartList);
        for(ShoppingCart shoppingCart : shoppingCartList){
            if(shoppingCart.getStatus().intValue() == ProductStatus.SHELF.getCode()){
                productShelfSize++;
            }else {
                txtClearProduct.setVisibility(View.VISIBLE);
            }
        }
        //初始化下数据
        totalAmount = 0;
        totalCount = 0;
        selectedCount = 0;
        rbAllSelect.setChecked(false);
        txtTotalAmount.setText(getString(R.string.order_total_amount_placeholder, 0f));
        btnSettlement.setText(getString(R.string.order_settlement_placeholder, 0));
    }

    @Override
    public void updateSuccess(String s) {
        dismissLoadingDialog();
        toast(s);
    }

    @Override
    public void updateFail(String errMsg) {
        dismissLoadingDialog();
        toast(errMsg);
        presenter.getShoppingCart(MyApplication.getInstance().getUser().getId());
    }

    @Override
    public void deleteSuccess(String s) {
        dismissLoadingDialog();
        toast(s);
        presenter.getShoppingCart(MyApplication.getInstance().getUser().getId());
    }

    @Override
    public void clearSuccess(String s) {
        dismissLoadingDialog();
        toast(s);
        presenter.getShoppingCart(MyApplication.getInstance().getUser().getId());
    }
}
