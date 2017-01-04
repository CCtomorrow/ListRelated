package com.ai.listrelated.loadmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.ai.listrelated.adapter.recyclerview.OnLoadMoreListener;
import com.ai.listrelated.adapter.recyclerview.wrapper.LoadMoreWrapper;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/4 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> {@link android.support.v7.widget.RecyclerView}的Container <br>
 */
public class LoadMoreRecyclerViewContainer extends LoadMoreContainerBase {

    private RecyclerView mRecyclerView;
    private LoadMoreWrapper mAdapter;

    public LoadMoreRecyclerViewContainer(Context context) {
        super(context);
    }

    public LoadMoreRecyclerViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRecyclerViewAdapter(LoadMoreWrapper adapter) {
        mAdapter = adapter;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initRecyclerView();
    }

    private void initRecyclerView() {
        Object object = retrieveListView();
        if (object == null || !(object instanceof RecyclerView)) return;
        mRecyclerView = (RecyclerView) retrieveListView();
        mRecyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                onReachBottom();
            }
        });
    }

    @Override
    protected void addFooterView(View view) {
        if (mAdapter != null) {
            mAdapter.setLoadMoreView(view);
        }
    }

    @Override
    protected void removeFooterView(View view) {
        if (mAdapter != null) {
            mAdapter.setLoadMoreView(null);
        }
    }

    @Override
    protected Object retrieveListView() {
        View view = null;
        for (int i = 0; i < getChildCount(); i++) {
            View tempView = getChildAt(i);
            if (tempView instanceof RecyclerView) {
                view = tempView;
                break;
            }
        }
        if (view != null) {
            mRecyclerView = (RecyclerView) view;
            return mRecyclerView;
        } else {
            return null;
        }
    }

}
