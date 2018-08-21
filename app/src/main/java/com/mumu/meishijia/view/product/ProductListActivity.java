package com.mumu.meishijia.view.product;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.product.ProductListAdapter;
import com.mumu.meishijia.model.product.Product;
import com.mumu.meishijia.presenter.product.ProductListPresenter;
import com.mumu.meishijia.view.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.utils.DensityUtil;

public class ProductListActivity extends BaseActivity<ProductListPresenter> implements ProductListView{
    public static final String CATEGORY_ID = "category_id";
    public static final String KEYWORD = "keyword";

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ProductListAdapter adapter;
    private int categoryId;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ButterKnife.bind(this);
        initUI();
        categoryId = getIntent().getIntExtra(CATEGORY_ID, 0);
        keyword = getIntent().getStringExtra(KEYWORD);
        refreshLayout.autoRefresh();
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
        adapter.setOnItemClickListener(new ProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra(ProductDetailActivity.PRODUCT_ID, product.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                presenter.getProductList(categoryId, keyword, pageIndex, pageSize);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getProductList(categoryId, keyword, pageIndex, pageSize);
            }
        });
    }

    @Override
    public void getListSuccess(List<Product> productList) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();

        if(productList.size() < pageSize){
            refreshLayout.setNoMoreData(true);
        }else {
            refreshLayout.setNoMoreData(false);
        }
        if(pageIndex == 1){
            adapter.setData(productList);
        }else {
            adapter.addData(productList);
        }

        pageIndex++;
    }

    @Override
    public void getListFail(String errMsg) {
        if(pageIndex == 1){
            refreshLayout.finishRefresh(false);
        }else {
            refreshLayout.finishLoadMore(false);
        }
        toast(errMsg);
    }
}
