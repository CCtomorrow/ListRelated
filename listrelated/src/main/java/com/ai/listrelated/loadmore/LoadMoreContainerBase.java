package com.ai.listrelated.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ai.listrelated.loadmore.iface.LoadMoreContainer;
import com.ai.listrelated.loadmore.iface.LoadMoreHandler;
import com.ai.listrelated.loadmore.iface.LoadMoreUIHandler;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2016/12/29 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> Loadmore包裹的Layout <br>
 */
public abstract class LoadMoreContainerBase extends FrameLayout implements LoadMoreContainer {

    private LoadMoreUIHandler mLoadMoreUIHandler;
    private LoadMoreHandler mLoadMoreHandler;

    private boolean mHasMore = false;
    private boolean mLoadError = false;
    private boolean mIsLoading = false;
    private boolean mAutoLoadMore = false;
    private boolean mAllLoadFinishView = true;

    private View mFooterView;

    public LoadMoreContainerBase(Context context) {
        super(context);
    }

    public LoadMoreContainerBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreContainerBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 使用默认提供的LoadMoreView
     */
    @SuppressWarnings("unused")
    public void useDefaultFooter() {
        LoadMoreDefaultFooterView footerView = new LoadMoreDefaultFooterView(getContext());
        footerView.setVisibility(GONE);
        setLoadMoreView(footerView);
        setLoadMoreUIHandler(footerView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // Logger.t("LoadMoreContainerBase").i("onFinishInflate");
        if (mFooterView != null) {
            addFooterView(mFooterView);
        }
    }

    private void tryToPerformLoadMore() {
        if (mIsLoading) {
            return;
        }
        if (!mHasMore) {
            return;
        }
        mIsLoading = true;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoading(this);
        }
        if (null != mLoadMoreHandler) {
            mLoadMoreHandler.onLoadMore(this);
        }
    }

    /**
     * 滑动到底部了，要开始处理加载更多的事件了
     */
    @SuppressWarnings("unused")
    protected void onReachBottom() {
        // Logger.i("onReachBottom");
        if (mLoadError) return;
        if (mAutoLoadMore) {
            tryToPerformLoadMore();
        } else {
            if (mHasMore) {
                mLoadMoreUIHandler.onWaitToLoadMore(this);
            }
        }
    }

    @Override
    public void setAutoLoadMore(boolean autoLoadMore) {
        mAutoLoadMore = autoLoadMore;
    }

    @Override
    public void setLoadMoreView(View view) {
        if (retrieveListView() == null) {
            mFooterView = view;
            return;
        }
        // remove previous
        if (mFooterView != null && mFooterView != view) {
            removeFooterView(view);
        }
        // add current
        mFooterView = view;
        mFooterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToPerformLoadMore();
            }
        });
        addFooterView(view);
    }

    @Override
    public void setLoadMoreUIHandler(LoadMoreUIHandler handler) {
        mLoadMoreUIHandler = handler;
    }

    @Override
    public void setLoadMoreHandler(LoadMoreHandler handler) {
        mLoadMoreHandler = handler;
    }

    @Override
    public void loadMoreFinish(boolean hasMore) {
        mLoadError = false;
        mIsLoading = false;
        mHasMore = hasMore;
        if (!mHasMore && !mAllLoadFinishView) {
            removeFooterView(mFooterView);
            return;
        }
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadFinish(this, hasMore);
        }
    }

    @Override
    public void loadMoreError() {
        mIsLoading = false;
        mLoadError = true;
        if (mLoadMoreUIHandler != null) {
            mLoadMoreUIHandler.onLoadError(this);
        }
    }

    @Override
    public void showLoadAllFinishView(boolean show) {
        mAllLoadFinishView = show;
    }

    protected abstract void addFooterView(View view);

    protected abstract void removeFooterView(View view);

    protected abstract Object retrieveListView();
}