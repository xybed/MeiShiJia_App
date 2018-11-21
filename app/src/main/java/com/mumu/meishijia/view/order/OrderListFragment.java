package com.mumu.meishijia.view.order;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.adapter.order.OrderListAdapter;
import com.mumu.meishijia.model.order.Order;
import com.mumu.meishijia.presenter.order.OrderListPresenter;
import com.mumu.meishijia.view.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lib.utils.DensityUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends BaseFragment<OrderListPresenter> implements OrderListView{
    public static final String ORDER_STATUS = "order_status";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;

    private int orderStatus;
    private OrderListAdapter adapter;

    public OrderListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            orderStatus = bundle.getInt(ORDER_STATUS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initUI();
        showFrameProgress();
        presenter.getOrderList(MyApplication.getInstance().getUser().getId(), orderStatus, pageIndex, pageSize);
        return view;
    }

    private void initUI(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new OrderListAdapter(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = DensityUtil.dip2px(getActivity(), 15);
            }
        });
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                presenter.getOrderList(MyApplication.getInstance().getUser().getId(), orderStatus, pageIndex, pageSize);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getOrderList(MyApplication.getInstance().getUser().getId(), orderStatus, pageIndex, pageSize);
            }
        });
    }

    @Override
    public void onClickRefresh() {
        pageIndex = 1;
        presenter.getOrderList(MyApplication.getInstance().getUser().getId(), orderStatus, pageIndex, pageSize);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getSuccess(List<Order> orderList) {
        dismissFrameProgress();
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();

        if(orderList.size() == 0){
            refreshLayout.setNoMoreData(true);
            if(pageIndex == 1){
                noData(getString(R.string.order_no_order));
            }
            return;
        }
        if(orderList.size() < pageSize){
            refreshLayout.setNoMoreData(true);
        }else {
            refreshLayout.setNoMoreData(false);
        }
        if(pageIndex == 1){
            adapter.setData(orderList);
        }else {
            adapter.addData(orderList);
        }

        pageIndex++;
    }

    @Override
    public void getFail(String errMsg) {
        dismissFrameProgress();
        refreshLayout.finishRefresh();
        if(pageIndex == 1){
            loadFail(null);
        }else {
            refreshLayout.finishLoadMore(false);
        }
        toast(errMsg);
    }
}
