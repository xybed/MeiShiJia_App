package com.mumu.meishijia.view.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.model.food.RecipeSubModel;
import com.mumu.meishijia.presenter.food.RecipePresenter;
import com.mumu.meishijia.view.BaseActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.utils.ToastUtil;
import lib.widget.FlowLayout;
import lib.widget.FrameProgressLayout;

public class RecipeActivity extends BaseActivity implements RecipeView {

    @Bind(R.id.frame_progress)
    FrameProgressLayout frameProgress;
    @Bind(R.id.flow_layout)
    FlowLayout flowLayout;

    private RecipePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        initUI();
        presenter.getRecipe();
    }

    private void initUI() {
        ButterKnife.bind(this);
        presenter = new RecipePresenter(this);
        frameProgress.show();
        frameProgress.setOnClickRefreshListener(new FrameProgressLayout.OnClickRefreshListener() {
            @Override
            public void onClickRefresh() {
                presenter.getRecipe();
            }
        });
    }

    @OnClick(R.id.btn_left)
    public void onClick() {
        finish();
    }

    @Override
    public void getSuccess(List<RecipeSubModel> result) {
        if (result != null && result.size() > 0) {
            frameProgress.dismiss();
            for(final RecipeSubModel recipeSubModel : result){
                TextView textView = (TextView) LayoutInflater.from(this).inflate(
                        R.layout.layout_recipe_flowlayout_item,
                        flowLayout,
                        false);
                textView.setText(recipeSubModel.getValue());
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = recipeSubModel.getId();
                        ToastUtil.show(""+id);
                    }
                });
                flowLayout.addView(textView);
            }
        } else {
            frameProgress.noData();
        }
    }

    @Override
    public void getFail(String errMsg) {
        frameProgress.loadFail();
        ToastUtil.show(errMsg);
    }
}
