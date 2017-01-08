package com.ai.listrelated.sample.defaultimpl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ai.listrelated.adapter.abslistview.CommonAdapter;
import com.ai.listrelated.adapter.abslistview.ViewHolder;
import com.ai.listrelated.loadmore.LoadMoreGridViewContainer;
import com.ai.listrelated.loadmore.iface.LoadMoreContainer;
import com.ai.listrelated.loadmore.iface.LoadMoreHandler;
import com.ai.listrelated.refresh.RefreshDefaultHandler;
import com.ai.listrelated.refresh.RefreshLayout;
import com.ai.listrelated.sample.LoadStateView;
import com.ai.listrelated.sample.R;
import com.ai.listrelated.sample.ReplyBean;
import com.ai.listrelated.ui.fragment.BaseLazyFragment;
import com.ai.listrelated.view.GridViewWithHeaderAndFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/8 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> GridViewFragment <br>
 */
public class GridViewFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener, LoadMoreHandler {

    private RefreshLayout mRefreshLayout;
    private LoadMoreGridViewContainer mLoadMoreGridViewContainer;
    private GridViewWithHeaderAndFooter mGridView;
    private LoadStateView mStateView;

    public static final int FIRST_PAGE_NUM = 1;
    private volatile int mCurrentPage = FIRST_PAGE_NUM;
    private volatile int mTotalPage = ReplyBean.TOTAL_PAGE;

    private List<ReplyBean> mDatas = new ArrayList<>();
    private CommonAdapter<ReplyBean> mAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 测试加载失败使用的
     */
    private int tryCount = 0;

    public static GridViewFragment getInstance(Bundle data) {
        GridViewFragment fragment = new GridViewFragment();
        if (data != null) {
            fragment.setArguments(data);
        }
        return fragment;
    }

    @NonNull
    @Override
    public View onInflaterRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.load_more_grid_view, container, false);
    }

    @Override
    public void onFindViews(View rootview) {
        mRefreshLayout = (RefreshLayout) rootview.findViewById(R.id.refresh_layout);
        mLoadMoreGridViewContainer = (LoadMoreGridViewContainer) rootview.findViewById(R.id.load_more_grid_view_container);
        mGridView = (GridViewWithHeaderAndFooter) rootview.findViewById(R.id.load_more_grid_view);
        mStateView = (LoadStateView) rootview.findViewById(R.id.load_more_state_view);

        // 这些配置直接拷贝过去用即可
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(
                R.color.red_first, R.color.red_second,
                R.color.red_third, R.color.fourth);
        mRefreshLayout.setRefreshHandler(new RefreshDefaultHandler(), mGridView);

        // 这些配置直接拷贝去用即可
        mLoadMoreGridViewContainer.setAutoLoadMore(true);
        mLoadMoreGridViewContainer.useDefaultFooter();
        mLoadMoreGridViewContainer.setLoadMoreHandler(this);
    }

    @Override
    public void onBindContent() {
        mAdapter = new CommonAdapter<ReplyBean>(getActivity(), R.layout.item_layout, mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, ReplyBean item, int position) {
                viewHolder.setText(R.id.text, item.getContent());
                viewHolder.setImageResource(R.id.image, item.getImageid());
            }
        };
        mGridView.setAdapter(mAdapter);
        mStateView.setType(LoadStateView.LOAD_EMPTY);
        reqFirstPageData();
    }

    @Override
    public void onRefresh() {
        reqFirstPageData();
    }

    @Override
    public void onLoadMore(LoadMoreContainer loadMoreContainer) {
        mCurrentPage++;
        reqData(true);
    }

    /**
     * 请求第一页数据
     */
    private void reqFirstPageData() {
        // 第一页的时候显示loading和下拉刷新
        mRefreshLayout.setRefreshing(true);
        mCurrentPage = FIRST_PAGE_NUM;
        reqData(false);
    }

    /**
     * 请求数据
     *
     * @param loadMore 是否是loadMore
     */
    private void reqData(final boolean loadMore) {
        if (mCurrentPage > mTotalPage) return;
        ReplyBean.getData(mHandler, mCurrentPage,
                new ReplyBean.OnDataCallBack<List<ReplyBean>>() {
                    @Override
                    public void onCallback(List<ReplyBean> data) {
                        if (loadMore) {
                            // 加载第三页的时候制造一个错误
                            if (mCurrentPage == 3) {
                                tryCount++;
                                if (tryCount < 3) {
                                    mCurrentPage--;
                                    mLoadMoreGridViewContainer.loadMoreError();
                                    return;
                                } else {
                                    tryCount = 0;
                                }
                            }
                            mDatas.addAll(data);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mDatas.clear();
                            mDatas.addAll(data);
                            mAdapter.notifyDataSetChanged();
                        }
                        // 设置状态
                        mStateView.setType(LoadStateView.LOAD_SUCCESS);
                        mLoadMoreGridViewContainer.loadMoreFinish(mTotalPage > mCurrentPage);
                    }

                    @Override
                    public void onFinish() {
                        mRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
