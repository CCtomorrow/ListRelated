package com.ai.listrelated.adapter.abslistview.base;

import com.ai.listrelated.adapter.abslistview.ViewHolder;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/01/02 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> item的接口，如果要实现MutiType的item必须实现这个接口 <br>
 */
public interface ItemViewDelegate<T> {

    /**
     * 获取item的layout的资源布局id
     */
    int getItemViewLayoutId();

    /**
     * 确定当前位置的item的类型，只有根据对应的类型才能生成对应的ViewHolder
     */
    boolean isForViewType(T item, int position);

    /**
     * 生成ViewHolder
     */
    void convert(ViewHolder holder, T t, int position);

}
