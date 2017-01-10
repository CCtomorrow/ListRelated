package com.ai.listrelated.ui.fragment.iface;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/10 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> Fragment状态监听 <br>
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
