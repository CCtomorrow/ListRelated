package com.ai.listrelated.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends LifecycleFragment implements IFragmentVisibleCallbak {

    //分别表示当前Fragment是否可见,是否已准备(表示已经走过onCreateView方法)以及是否数据已加载
    protected boolean isVisible = false;
    protected boolean isPrepared = false;
    protected boolean isLoaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onFragmentVisible() {

    }

    @Override
    public void onFragmentInvisible() {

    }

    @Override
    public void onLoadedData() {

    }

    /**
     * 不提供覆写，需监听可见性的子类可覆写{@link #onFragmentVisible()}和{@link #onFragmentInvisible()}方法
     *
     * @param isVisibleToUser 当前Fragment的可见性
     */
    @Override
    public final void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (getUserVisibleHint()) {
            onLoadedData();
            onFragmentVisible();
        } else {
            onFragmentInvisible();
        }
    }

}
