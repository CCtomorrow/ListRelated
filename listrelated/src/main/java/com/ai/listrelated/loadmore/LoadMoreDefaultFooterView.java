package com.ai.listrelated.loadmore;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ai.listrelated.R;
import com.ai.listrelated.loadmore.iface.LoadMoreContainer;
import com.ai.listrelated.loadmore.iface.LoadMoreUIHandler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/10 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 默认加载更多的时候展示的View，也可以自定义使用一套，具体的每个方法说明很清楚了 <br>
 * 注意:这里由于RecyclerView的测量原因，使用别的布局会出问题
 */
public class LoadMoreDefaultFooterView extends RelativeLayout implements LoadMoreUIHandler {

    public static final int LOAD_WAIT = 1;
    public static final int LOAD_LOADING = 2;
    public static final int LOAD_FAIL = 3;
    public static final int LOAD_FINISH = 4;

    @IntDef({LOAD_WAIT, LOAD_LOADING, LOAD_FAIL, LOAD_FINISH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MaskState {
    }

    private ViewGroup mWaitView; // 等待加载更多时候的View
    private ViewGroup mLoadingView; // 加载更多时候展示的View
    private ViewGroup mFailView; // 加载失败展示的View
    private ViewGroup mFinishView; // 没有数据，所有服务器数据加载数据完了展示的View

    public LoadMoreDefaultFooterView(Context context) {
        super(context);
        setupViews();
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupViews();
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.load_more_default_view, this);
        mWaitView = (ViewGroup) findViewById(R.id.load_more_load_wait_view);
        mLoadingView = (ViewGroup) findViewById(R.id.load_more_loading_view);
        mFailView = (ViewGroup) findViewById(R.id.load_more_load_fail_view);
        mFinishView = (ViewGroup) findViewById(R.id.load_more_load_end_view);
    }

    /**
     * 通过传递进来的状态改变当前显示的View
     *
     * @param status @MaskState
     */
    private void changeVisible(@MaskState int status) {
        setVisibility(VISIBLE);
        mWaitView.setVisibility(status == LOAD_WAIT ? VISIBLE : GONE);
        mLoadingView.setVisibility(status == LOAD_LOADING ? VISIBLE : GONE);
        mFailView.setVisibility(status == LOAD_FAIL ? VISIBLE : GONE);
        mFinishView.setVisibility(status == LOAD_FINISH ? VISIBLE : GONE);
    }

    @Override
    public void onWaitToLoadMore(LoadMoreContainer container) {
        changeVisible(LOAD_WAIT);
    }

    @Override
    public void onLoading(LoadMoreContainer container) {
        changeVisible(LOAD_LOADING);
    }

    @Override
    public void onLoadFinish(LoadMoreContainer container, boolean hasMore) {
        if (!hasMore) {
            changeVisible(LOAD_FINISH);
        } else {
            setVisibility(GONE);
        }
    }

    @Override
    public void onLoadError(LoadMoreContainer container) {
        changeVisible(LOAD_FAIL);
    }

}
