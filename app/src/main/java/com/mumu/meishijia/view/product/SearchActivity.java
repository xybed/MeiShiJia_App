package com.mumu.meishijia.view.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.product.SearchAdapter;
import com.mumu.meishijia.model.product.SearchHistoryRealm;
import com.mumu.meishijia.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import lib.realm.MyRealm;
import lib.utils.StringUtil;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<SearchHistoryRealm> dataList;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);
        initUI();
        initData();
    }

    private void initUI(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new SearchAdapter(this);
        adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name) {
                //跳转至商品列表
                goProductList(name);
            }

            @Override
            public void onItemDelete(SearchHistoryRealm searchHistoryRealm) {
                dataList.remove(searchHistoryRealm);
                adapter.setData(dataList);
                //删除此条历史记录
                Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
                SearchHistoryRealm model = realm.where(SearchHistoryRealm.class)
                        .equalTo("id", searchHistoryRealm.getId())
                        .findFirst();
                realm.beginTransaction();
                model.deleteFromRealm();
                realm.commitTransaction();
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initData(){
        dataList = new ArrayList<>();
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        RealmResults<SearchHistoryRealm> realmResults = realm
                .where(SearchHistoryRealm.class)
                .findAll()
                .sort("id", Sort.DESCENDING);
        dataList.addAll(realmResults);
        adapter.setData(dataList);
    }

    @OnClick({R.id.imb_left, R.id.txt_right, R.id.llay_clean_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imb_left:
                onLeftButtonClick();
                break;
            case R.id.txt_right:
                search();
                break;
            case R.id.llay_clean_history:
                Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
                realm.beginTransaction();
                realm.delete(SearchHistoryRealm.class);
                realm.commitTransaction();
                dataList.clear();
                adapter.setData(dataList);
                break;
        }
    }

    private void search(){
        if(StringUtil.isEmpty(editSearch.getText().toString())){
            toast(R.string.product_pls_input_search_content);
            return;
        }
        //搜索记录存储
        Realm realm = Realm.getInstance(MyRealm.getInstance().getMyConfig());
        SearchHistoryRealm searchHistoryRealm = new SearchHistoryRealm();
        searchHistoryRealm.setId(System.currentTimeMillis());
        searchHistoryRealm.setName(editSearch.getText().toString());
        realm.beginTransaction();
        realm.insertOrUpdate(searchHistoryRealm);
        realm.commitTransaction();
        //刷新界面
        dataList.add(0, searchHistoryRealm);
        adapter.setData(dataList);
        //跳转至商品列表
        goProductList(editSearch.getText().toString());
    }

    private void goProductList(String name){
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra(ProductListActivity.KEYWORD, name);
        startActivity(intent);
    }
}
