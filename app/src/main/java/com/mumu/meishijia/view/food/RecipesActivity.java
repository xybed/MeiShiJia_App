package com.mumu.meishijia.view.food;

import android.os.Bundle;
import android.widget.ListView;

import com.mumu.meishijia.R;
import com.mumu.meishijia.view.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.widget.FrameProgressLayout;

public class RecipesActivity extends BaseActivity implements RecipesView{

    @Bind(R.id.frame_progress)
    FrameProgressLayout frameProgress;
    @Bind(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        initUI();
    }

    private void initUI(){
        ButterKnife.bind(this);
        frameProgress.show();
        frameProgress.setOnClickRefreshListener(new FrameProgressLayout.OnClickRefreshListener() {
            @Override
            public void onClickRefresh() {

            }
        });
    }

    @OnClick(R.id.btn_left)
    public void onClick() {
        finish();
    }
}
