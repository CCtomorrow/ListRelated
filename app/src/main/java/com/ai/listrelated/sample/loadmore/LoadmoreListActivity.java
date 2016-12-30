package com.ai.listrelated.sample.loadmore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
public class LoadmoreListActivity extends AppCompatActivity {

    private ListView mListView;
    private int mTotalPage = 5;
    private int mCurentPage = 1;
    private List<String> mDatas = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_more_list_view);
        final LoadMoreListViewContainer loadWrap = (LoadMoreListViewContainer)
                findViewById(R.id.load_more_list_view_container);
        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);
        loadWrap.useDefaultFooter();
        loadWrap.setAutoLoadMore(true);
        loadWrap.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                if (mCurentPage > mTotalPage) {
                    // 没有更多数据了
                    loadWrap.loadMoreFinish(false);
                } else {
                    loadData();
                }
            }
        });
    }

    public void loadData() {
        mCurentPage++;
        List<String> data = new ArrayList<>();
        int start = mCurentPage * 10;
        for (int i = 0; i < 20; i++) {
            data.add("数据<=============>" + (start + i + 1));
        }
        mDatas.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

}
