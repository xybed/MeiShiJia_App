package com.mumu.meishijia.view.food;

import android.os.Bundle;
import android.widget.ListView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.food.RecipeAdapter;
import com.mumu.meishijia.model.food.RecipeModel;
import com.mumu.meishijia.presenter.food.RecipePresenter;
import com.mumu.meishijia.view.BaseActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.ToastUtil;
import lib.widget.FrameProgressLayout;

public class RecipeActivity extends BaseActivity implements RecipeView {

    @Bind(R.id.frame_progress)
    FrameProgressLayout frameProgress;
    @Bind(R.id.list_view)
    ListView listView;

    private RecipePresenter presenter;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        initUI();
        presenter.getRecipe();
    }

    private void initUI(){
        ButterKnife.bind(this);
        presenter = new RecipePresenter(this);
        adapter = new RecipeAdapter(this);
        frameProgress.show();
        frameProgress.setOnClickRefreshListener(new FrameProgressLayout.OnClickRefreshListener() {
            @Override
            public void onClickRefresh() {
                presenter.getRecipe();
            }
        });
        listView.setAdapter(adapter);
    }

    @OnClick(R.id.btn_left)
    public void onClick() {
        finish();
    }

    @Override
    public void getSuccess(List<RecipeModel> result) {
        if(result != null && result.size() > 0){
            frameProgress.dismiss();
            adapter.setData(result);
        }else {
            frameProgress.noData();
        }
    }

    @Override
    public void getFail(String errMsg) {
        frameProgress.loadFail();
        ToastUtil.show(errMsg);
    }
}
