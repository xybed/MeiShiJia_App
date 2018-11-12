package com.mumu.meishijia.presenter;

import com.google.gson.JsonSyntaxException;
import com.mumu.meishijia.view.BaseView;
import com.mumu.meishijia.viewmodel.BaseViewModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by qiqi on 2018/2/8.
 * Presenter的基类，用于创建model，并作为观察者
 */

public class BasePresenter<V extends BaseView, M extends BaseViewModel> {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected V view;
    protected M model;

    @SuppressWarnings("unchecked")
    public BasePresenter(BaseView view){
        this.view = (V) view;
        createModel();
    }

    @SuppressWarnings("unchecked")
    private void createModel(){
        try {
            model = ((Class<M>) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[1]).getConstructor().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassCastException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void unSubscribe(){
        compositeDisposable.clear();
        view = null;
    }

    /**
     * Created by qiqi on 2017/11/8.
     * Retrofit的观察者
     */
    protected abstract class RxObserver<T> implements Observer<T> {
        @Override
        public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
        }

        @Override
        public void onNext(T value) {
            onSuccess(value);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            if(e instanceof SocketTimeoutException){
                onFail("连接超时");
            }else if(e instanceof JsonSyntaxException){
                onFail("json格式不符");
            }else if("登录过期".equals(e.getMessage())){
                onFail(e.getMessage());
                goLogin();
            }else if(e instanceof NullPointerException){
                onFail("暂无数据");
            }else{
                onFail(e.getMessage());
            }
        }

        @Override
        public void onComplete() {

        }

        protected abstract void onSuccess(T t);
        protected void onFail(String errMsg){
            view.toastErr(errMsg);
        }
        private void goLogin(){view.goLogin();}
    }
}
