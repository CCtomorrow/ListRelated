package com.ai.listrelated.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2016/12/31 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> {@link android.widget.AbsListView}的Container <br>
 */
public abstract class LoadMoreAbsListViewContainer extends LoadMoreContainerBase {

    private AbsListView mAbsListView;
    private AbsListView.OnScrollListener mOnScrollListener;

    public LoadMoreAbsListViewContainer(Context context) {
        super(context);
    }

    public LoadMoreAbsListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreAbsListViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initAbsListView();
    }

    private void initAbsListView() {
        Object object = retrieveListView();
        if (object == null || !(object instanceof AbsListView)) return;
        mAbsListView = (AbsListView) retrieveListView();
        mAbsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private boolean mIsEnd = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 考虑把ScrollerListener事件放出去
                if (null != mOnScrollListener) {
                    mOnScrollListener.onScrollStateChanged(view, scrollState);
                }
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (mIsEnd) {
                        onReachBottom();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 考虑把ScrollerListener事件放出去
                if (null != mOnScrollListener) {
                    mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
                if (firstVisibleItem + visibleItemCount >= totalItemCount - 1) {
                    mIsEnd = true;
                } else {
                    mIsEnd = false;
                }
            }
        });
    }

    public void setOnScrollListener(AbsListView.OnScrollListener l) {
        mOnScrollListener = l;
    }

}
