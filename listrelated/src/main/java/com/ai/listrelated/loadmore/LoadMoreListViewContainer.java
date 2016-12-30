package com.ai.listrelated.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2016/12/30 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> Loadmore包裹的Layout <br>
 */
public class LoadMoreListViewContainer extends LoadMoreContainerBase {

    private ListView mListView;
    private AbsListView.OnScrollListener mOnScrollListener;

    public LoadMoreListViewContainer(Context context) {
        super(context);
    }

    public LoadMoreListViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreListViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initListView();
    }

    private void initListView() {
        View view = null;
        for (int i = 0; i < getChildCount(); i++) {
            View tempView = getChildAt(i);
            if (tempView instanceof ListView) {
                view = tempView;
                break;
            }
        }
        mListView = (ListView) view;
        if (mListView == null) return;
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    public ListView getListView() {
        return mListView;
    }

    @Override
    protected void addFooterView(View view) {
        if (mListView != null) {
            mListView.addFooterView(view);
        }
    }

    @Override
    protected void removeFooterView(View view) {
        if (mListView != null) {
            mListView.removeFooterView(view);
        }
    }

    @Override
    protected Object retrieveListView() {
        return getListView();
    }

}
