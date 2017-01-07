package com.ai.listrelated.sample.defaultimpl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ai.listrelated.loadmore.LoadMoreAbsListViewContainer;
import com.ai.listrelated.loadmore.iface.LoadMoreContainer;
import com.ai.listrelated.loadmore.iface.LoadMoreHandler;
import com.ai.listrelated.sample.R;
import com.ai.listrelated.sample.ReplyBean;
import com.ai.listrelated.sample.LoadStateView;
import com.ai.listrelated.ui.fragment.BaseLazyFragment;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/8 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> ListViewFragment <br>
 */
public class ListViewFragment extends BaseLazyFragment implements SwipeRefreshLayout.OnRefreshListener, LoadMoreHandler {

    private Toolbar mToolbar;
    private SwipeRefreshLayout mRefreshLayout;
    private LoadMoreAbsListViewContainer mListViewContainer;
    private ListView mListView;
    private LoadStateView mStateView;

    private int mCurrentPage = 0;
    private int mTotalPage = ReplyBean.TOTAL_PAGE;

    @NonNull
    @Override
    public View onInflaterRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.load_more_list_view, container, false);
    }

    @Override
    public void onFindViews(View rootview) {
        mToolbar = (Toolbar) rootview.findViewById(R.id.toolbar);
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

        mStateView.setType(LoadStateView.LOAD_LOADING);
    }

    @Override
    public void onBindContent() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore(LoadMoreContainer loadMoreContainer) {

    }

}
