package com.mumu.meishijia.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.hwangjr.rxbus.RxBus;
import com.mumu.meishijia.MyApplication;
import com.mumu.meishijia.R;
import com.mumu.meishijia.constant.RxBusAction;
import com.mumu.meishijia.model.mine.UserModel;
import com.mumu.meishijia.presenter.BasePresenter;
import com.mumu.meishijia.view.mine.LoginActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import lib.cache.CacheJsonMgr;
import lib.widget.FrameProgressLayout;
import lib.widget.LoadingDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView,
        SwipeRefreshLayout.OnRefreshListener, FrameProgressLayout.OnClickRefreshListener{

    protected P presenter;

    private LoadingDialog loadingDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FrameProgressLayout frameProgressLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();
    }

    @SuppressWarnings("unchecked")
    private void createPresenter(){
        try {
            presenter = ((Class<P>) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]).getConstructor(BaseView.class).newInstance(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (ClassCastException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSwipeRefresh(view);
        getFrameProgress(view);
    }

    private void getSwipeRefresh(View view){
        if(swipeRefreshLayout == null)
            swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setColorSchemeResources(R.color.theme_color);
            swipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    protected void startRefresh(){
        if(swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(true);
    }

    protected void stopRefresh(){
        if(swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

    }

    private void getFrameProgress(View view){
        if(frameProgressLayout == null)
            frameProgressLayout = view.findViewWithTag(FrameProgressLayout.TAG);
        if(frameProgressLayout != null)
            frameProgressLayout.setOnClickRefreshListener(this);
    }

    protected void showFrameProgress(){
        if(frameProgressLayout != null)
            frameProgressLayout.show();
    }

    protected void dismissFrameProgress(){
        if(frameProgressLayout != null)
            frameProgressLayout.dismiss();
    }

    protected void noData(String str){
        if(frameProgressLayout != null){
            if(str != null){
                frameProgressLayout.noData(str);
            }else {
                frameProgressLayout.noData();
            }
        }
    }

    protected void loadFail(String str){
        if(frameProgressLayout != null){
            if(str != null){
                frameProgressLayout.loadFail(str);
            }else {
                frameProgressLayout.loadFail();
            }
        }
    }

    @Override
    public void onClickRefresh() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideSoftInput();
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        /** 隐藏软键盘 **/
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void toastErr(String msg) {
        dismissLoadingDialog();
        stopRefresh();
        toast(msg);
    }

    protected void toast(String msg){
        if(!TextUtils.isEmpty(msg)){
            /*
            * 因为在主题里设置了fitsSystemWindows为true，所以toast的文字位置不居中
            * 把application当作context的值传入可解决这个问题*/
            Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void goLogin() {
        MyApplication.getInstance().setUser(null);
        MyApplication.getInstance().setLogin(false);
        MyApplication.getInstance().setIMLogin(false);
        //清除用户信息
        CacheJsonMgr.getInstance(getActivity()).deleteJson(UserModel.class.getSimpleName());

        //通知我的界面刷新数据
        RxBus.get().post(RxBusAction.MineUserData, "");

        //跳转到LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /****end***/

    public void showLoadingDialog(String message, boolean cancelable, boolean otoCancelable){
        loadingDialog = new LoadingDialog(getActivity(), message, cancelable, otoCancelable);
        loadingDialog.show();
    }

    public void showLoadingDialog(String message){
        showLoadingDialog(message, true, true);
    }

    public void dismissLoadingDialog(){
        if(loadingDialog != null)
            loadingDialog.dismiss();
    }

}
