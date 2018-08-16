package com.mumu.meishijia.view.product;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.product.ProductListAdapter;
import com.mumu.meishijia.model.product.Product;
import com.mumu.meishijia.presenter.product.ProductListPresenter;
import com.mumu.meishijia.view.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.utils.DensityUtil;

public class ProductListActivity extends BaseActivity<ProductListPresenter> implements ProductListView{
    public static final String CATEGORY_ID = "category_id";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ProductListAdapter adapter;
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ButterKnife.bind(this);
        initUI();
        categoryId = getIntent().getIntExtra(CATEGORY_ID, 0);
        startRefresh();
        presenter.getProductList(categoryId, pageIndex, pageSize);
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

    @Override
    public void onRefresh() {
        pageIndex = 1;
        presenter.getProductList(categoryId, pageIndex, pageSize);
    }

    @Override
    public void getListSuccess(List<Product> productList) {
        stopRefresh();
        adapter.setData(productList);
    }
}
