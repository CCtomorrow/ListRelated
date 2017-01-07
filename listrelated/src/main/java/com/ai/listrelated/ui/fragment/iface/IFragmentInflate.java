package com.ai.listrelated.ui.fragment.iface;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/4 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> Fragment View 加载以及事件 <br>
 */
public interface IFragmentInflate {

    /**
     * 请务必在此方法中返回此Fragment提供的根视图，返回结果不可为空
     *
     * @param inflater           用于实例化layout文件的Inflater
     * @param container          父容器
     * @param savedInstanceState 有能为空，使用之前请先进行判断
     * @return 不可为空
     */
    @NonNull
    View onInflaterRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 根据提供的根视图，找到当前页面需要使用的一些视图控件
     *
     * @param rootview 提供给子类查找视图控件所用
     */
    void onFindViews(View rootview);

    /**
     * 将数据与视图控件进行绑定，以显示内容
     */
    void onBindContent();

}
