package com.mumu.meishijia.view.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.product.ProductCategoryOneAdapter;
import com.mumu.meishijia.adapter.product.ProductCategoryThreeAdapter;
import com.mumu.meishijia.adapter.product.ProductCategoryTwoAdapter;
import com.mumu.meishijia.model.product.ProductCategory;
import com.mumu.meishijia.presenter.product.ProductCategoryPresenter;
import com.mumu.meishijia.view.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductCategoryActivity extends BaseActivity<ProductCategoryPresenter> implements ProductCategoryView{

    @BindView(R.id.recycler_view_one)
    RecyclerView recyclerViewOne;
    @BindView(R.id.recycler_view_two)
    RecyclerView recyclerViewTwo;
    @BindView(R.id.recycler_view_three)
    RecyclerView recyclerViewThree;

    private List<ProductCategory> categoryList;
    private ProductCategoryOneAdapter oneAdapter;
    private ProductCategoryTwoAdapter twoAdapter;
    private ProductCategoryThreeAdapter threeAdapter;
    //请求分类的级别
    private int level = 1;
    //现选中的一、二级分类的pos
    private int onePos = 0;
    private int twoPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        ButterKnife.bind(this);
        initUI();
        presenter.getProductCategory(0);
    }

    private void initUI(){
        LinearLayoutManager layoutManagerOne = new LinearLayoutManager(this);
        oneAdapter = new ProductCategoryOneAdapter(this);
        oneAdapter.setOnItemClickListener(new ProductCategoryOneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onePos = position;
                //设置选中状态
                for(ProductCategory category : categoryList){
                    category.setSelected(false);
                }
                ProductCategory category = categoryList.get(position);
                category.setSelected(true);
                //刷新一级分类
                oneAdapter.setData(categoryList);

                //刷新二级分类
                List<ProductCategory> childList = category.getChildList();
                if(childList != null){
                    //刷新二级列表时，还要刷新三级列表
                    for(ProductCategory child : childList){
                        child.setSelected(false);
                    }
                    twoPos = 0;
                    ProductCategory child = childList.get(0);
                    child.setSelected(true);
                    twoAdapter.setData(childList);
                    //刷新三级列表
                    if(child.getChildList() != null){
                        threeAdapter.setData(child.getChildList());
                    }else {
                        level = 3;
                        presenter.getProductCategory(child.getId());
                    }
                }else {
                    level = 2;
                    presenter.getProductCategory(category.getId());
                }
            }
        });
        recyclerViewOne.setLayoutManager(layoutManagerOne);
        recyclerViewOne.setAdapter(oneAdapter);

        LinearLayoutManager layoutManagerTwo = new LinearLayoutManager(this);
        twoAdapter = new ProductCategoryTwoAdapter(this);
        twoAdapter.setOnItemClickListener(new ProductCategoryTwoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                twoPos = position;
                //设置选中状态
                List<ProductCategory> twoCategoryList = categoryList.get(onePos).getChildList();
                for(ProductCategory category : twoCategoryList){
                    category.setSelected(false);
                }
                ProductCategory category = twoCategoryList.get(position);
                category.setSelected(true);
                //刷新二级分类
                twoAdapter.setData(twoCategoryList);

                //刷新三级分类
                List<ProductCategory> childList = category.getChildList();
                if(childList != null){
                    threeAdapter.setData(childList);
                }else {
                    level = 3;
                    presenter.getProductCategory(category.getId());
                }
            }
        });
        recyclerViewTwo.setLayoutManager(layoutManagerTwo);
        recyclerViewTwo.setAdapter(twoAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        threeAdapter = new ProductCategoryThreeAdapter(this);
        threeAdapter.setOnItemClickListener(new ProductCategoryThreeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductCategory category) {
                Intent intent = new Intent(ProductCategoryActivity.this, ProductListActivity.class);
                intent.putExtra(ProductListActivity.CATEGORY_ID, category.getId());
                startActivity(intent);
            }
        });
        recyclerViewThree.setLayoutManager(gridLayoutManager);
        recyclerViewThree.setAdapter(threeAdapter);
    }

    @Override
    public void getSuccess(List<ProductCategory> categoryList) {
        categoryList.get(0).setSelected(true);
        switch (level){
            case 1:
                this.categoryList = categoryList;
                oneAdapter.setData(categoryList);
                level = 2;
                presenter.getProductCategory(categoryList.get(0).getId());
                break;
            case 2:
                //一级分类设置子列表
                this.categoryList.get(onePos).setChildList(categoryList);
                twoAdapter.setData(categoryList);
                level = 3;
                twoPos = 0;
                presenter.getProductCategory(categoryList.get(0).getId());
                break;
            case 3:
                //二级分类设置子列表
                this.categoryList.get(onePos).getChildList().get(twoPos).setChildList(categoryList);
                threeAdapter.setData(categoryList);
                break;
        }
    }
}
