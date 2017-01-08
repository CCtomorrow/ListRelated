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
import android.widget.ListView;

import com.ai.listrelated.adapter.abslistview.CommonAdapter;
import com.ai.listrelated.adapter.abslistview.ViewHolder;
import com.ai.listrelated.loadmore.LoadMoreAbsListViewContainer;
import com.ai.listrelated.loadmore.iface.LoadMoreContainer;
import com.ai.listrelated.loadmore.iface.LoadMoreHandler;
import com.ai.listrelated.sample.LoadStateView;
import com.ai.listrelated.sample.R;
import com.ai.listrelated.sample.ReplyBean;
import com.ai.listrelated.ui.fragment.BaseLazyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/8 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> ListViewFragment <br>
 */
public class ListViewFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener, LoadMoreHandler {

    private SwipeRefreshLayout mRefreshLayout;
    private LoadMoreAbsListViewContainer mListViewContainer;
    private ListView mListView;
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
    private final int tryCount = 2;

    public static ListViewFragment getInstance(Bundle data) {
        ListViewFragment fragment = new ListViewFragment();
        if (data != null) {
            fragment.setArguments(data);
        }
        return fragment;
    }

    @NonNull
    @Override
    public View onInflaterRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.load_more_list_view, container, false);
    }

    @Override
    public void onFindViews(View rootview) {
        mRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.refresh_layout);
        mListViewContainer = (LoadMoreAbsListViewContainer) rootview.findViewById(R.id.load_more_list_view_container);
        mListView = (ListView) rootview.findViewById(R.id.load_more_list_view);
        mStateView = (LoadStateView) rootview.findViewById(R.id.load_more_state_view);

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(
                R.color.red_first, R.color.red_second,
                R.color.red_third, R.color.fourth);

        mListViewContainer.setAutoLoadMore(true);
        mListViewContainer.useDefaultFooter();
        mListViewContainer.setLoadMoreHandler(this);
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
        mListView.setAdapter(mAdapter);
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
        mStateView.setType(LoadStateView.LOAD_LOADING);
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
                            mDatas.addAll(data);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mDatas.clear();
                            mDatas.addAll(data);
                            mAdapter.notifyDataSetChanged();
                        }
                        mListViewContainer.loadMoreFinish(mTotalPage > mCurrentPage);
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
