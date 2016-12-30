package com.ai.listrelated.sample.loadmore;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ai.listrelated.loadmore.LoadMoreListViewContainer;
import com.ai.listrelated.loadmore.iface.LoadMoreContainer;
import com.ai.listrelated.loadmore.iface.LoadMoreHandler;
import com.ai.listrelated.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2016/12/30 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 加载更多ListView测试 <br>
 */
public class LoadmoreListActivity extends Activity {

    private ListView mListView;
    private LoadMoreListViewContainer mLoadWrap;
    private int mTotalPage = 5;
    private volatile int mCurentPage = 0;
    private List<String> mDatas = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private Handler mHandler = new Handler();

    private int tryCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_more_list_view);
        mLoadWrap = (LoadMoreListViewContainer)
                findViewById(R.id.load_more_list_view_container);
        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);
        mLoadWrap.useDefaultFooter();
        // mLoadWrap.setAutoLoadMore(true);
        mLoadWrap.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                if (mTotalPage > mCurentPage) {
                    loadData();
                } else {
                    // 没有更多数据了
                    mLoadWrap.loadMoreFinish(false);
                }
            }
        });
        loadData();
    }

    public void loadData() {
        mCurentPage++;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final List<String> data = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    data.add("page:" + mCurentPage + "<=====>数据<=====>" + (i + 1));
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        // 加载第三页的时候制造一个错误
                        if (mCurentPage == 3) {
                            mCurentPage--;
                            tryCount++;
                            if (tryCount < 3) {
                                mLoadWrap.loadMoreError();
                                return;
                            } else {
                                mCurentPage++;
                                tryCount = 0;
                            }
                        }

                        mDatas.addAll(data);
                        mAdapter.notifyDataSetChanged();
                        // 加载更多，第一页数据加载完毕就需要知道是否存在更多数据以便改变状态
                        mLoadWrap.loadMoreFinish(mTotalPage > mCurentPage);
                    }
                });
            }
        }).start();
    }

}
