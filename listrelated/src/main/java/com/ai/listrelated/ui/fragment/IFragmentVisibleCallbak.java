package com.ai.listrelated.ui.fragment;

/**
 * Fragment状态监听
 */
public interface IFragmentVisibleCallbak {

    /**
     * Fragment可见
     */
    void onFragmentVisible();

    /**
     * Fragment不可见
     */
    void onFragmentInvisible();

    /**
     * 可以加载数据，Fragment可见，并且onCreateView已经调用
     */
    void onLoadedData();

}
