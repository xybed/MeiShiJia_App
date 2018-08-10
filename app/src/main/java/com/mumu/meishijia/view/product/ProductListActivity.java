package com.mumu.meishijia.view.product;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.product.ProductListAdapter;
import com.mumu.meishijia.presenter.product.ProductListPresenter;
import com.mumu.meishijia.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.utils.DensityUtil;

public class ProductListActivity extends BaseActivity<ProductListPresenter> {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ProductListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ButterKnife.bind(this);
        initUI();
    }

    private void initUI(){
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        adapter = new ProductListAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = DensityUtil.dip2px(ProductListActivity.this, 15);
                outRect.left = DensityUtil.dip2px(ProductListActivity.this, 6);
                outRect.right = DensityUtil.dip2px(ProductListActivity.this, 6);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
