package com.ai.listrelated.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;

import com.ai.listrelated.adapter.recyclerview.base.ItemViewDelegate;
import com.ai.listrelated.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/01/03 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 单个item类型的adapter <br>
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonAdapter(final Context context, final int layoutId, final List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);

}
