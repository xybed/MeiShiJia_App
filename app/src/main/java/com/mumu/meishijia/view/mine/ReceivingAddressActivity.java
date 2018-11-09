package com.mumu.meishijia.view.mine;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.mine.ReceivingAddressAdapter;
import com.mumu.meishijia.model.mine.ReceivingAddress;
import com.mumu.meishijia.presenter.mine.ReceivingAddressPresenter;
import com.mumu.meishijia.view.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.DensityUtil;

public class ReceivingAddressActivity extends BaseActivity<ReceivingAddressPresenter> implements ReceivingAddressView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ReceivingAddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving_address);
        ButterKnife.bind(this);
        initUI();
        presenter.getReceivingAddress(MyApplication.getInstance().getUser().getId());
    }

    private void initUI(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ReceivingAddressAdapter(this);
        adapter.setOnItemClickListener(new ReceivingAddressAdapter.OnItemClickListener() {
            @Override
            public void onClickEdit(ReceivingAddress receivingAddress) {
                Intent intent = new Intent(ReceivingAddressActivity.this, ReceivingAddressEditActivity.class);
                intent.putExtra(ReceivingAddressEditActivity.TITLE, getString(R.string.user_edit_receiving_address));
                intent.putExtra(ReceivingAddressEditActivity.RECEIVING_ADDRESS, receivingAddress);
                startActivity(intent);
            }

            @Override
            public void onClickDelete(int addressId) {

            }

            @Override
            public void onClickItem(ReceivingAddress receivingAddress) {

            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = DensityUtil.dip2px(ReceivingAddressActivity.this, 10);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        Intent intent = new Intent(this, ReceivingAddressEditActivity.class);
        intent.putExtra(ReceivingAddressEditActivity.TITLE, getString(R.string.user_add_receiving_address));
        startActivity(intent);
    }

    @Override
    public void getSuccess(List<ReceivingAddress> receivingAddressList) {
        adapter.setData(receivingAddressList);
    }
}
