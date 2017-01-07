package com.ai.listrelated.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ai.listrelated.ui.fragment.iface.IFragmentInflate;
import com.ai.listrelated.ui.fragment.iface.IFragmentVisibleCallbak;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/4 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> BaseLazyFragment懒加载Fragment，这个的用途是配合ViewPager来使用 <br>
 * 注意:如果是普通的不是放在ViewPager中的Fragment的话，用这个不行，需要使用onHiddenChanged方法
 */
public abstract class BaseLazyFragment extends BaseFragment
        implements IFragmentVisibleCallbak, IFragmentInflate {

    //分别表示当前Fragment是否可见,是否已准备(表示已经走过onCreateView方法)以及是否数据已加载
    protected boolean isVisible = false;
    protected boolean isPrepared = false;
    protected boolean isLoaded = false;

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logLifeCycle("onCreateView");
        mView = onInflaterRootView(inflater, container, savedInstanceState);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //只要走过这个方法就认为已经加载了根View了
        isPrepared = true;
        //如果可见说明这个Fragment初次加载就是可见，应立即初始化布局
        if (isVisible) {
            onFindViews(view);
            onBindContent();
            isLoaded = true;
        }
    }

    @Override
    public void onFragmentVisible() {
        logLifeCycle("onFragmentVisible");
    }

    @Override
    public void onFragmentInvisible() {
        logLifeCycle("onFragmentInvisible");
    }

    @Override
    public void onLoadedData() {
        if (!isPrepared) return;
        //只有没有加载才去初始化View和绑定数据
        if (!isLoaded) {
            onFindViews(mView);
            onBindContent();
            isLoaded = true;
        }
    }

    /**
     * 不提供覆写，需监听可见性的子类可覆写{@link #onFragmentVisible()}和{@link #onFragmentInvisible()}方法
     *
     * @param isVisibleToUser 当前Fragment的可见性，onCreateView之前调用
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
