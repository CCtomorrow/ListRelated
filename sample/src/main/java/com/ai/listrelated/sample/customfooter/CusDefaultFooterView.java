package com.ai.listrelated.sample.customfooter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.listrelated.loadmore.iface.LoadMoreContainer;
import com.ai.listrelated.loadmore.iface.LoadMoreUIHandler;
import com.ai.listrelated.sample.R;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/8 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 自定义加载更多界面，记得继承RelativeLayout，由于RecyclerView的测量问题 <br>
 */
public class CusDefaultFooterView extends RelativeLayout implements LoadMoreUIHandler {

    private TextView mTextView;

    public CusDefaultFooterView(Context context) {
        this(context, null);
    }

    public CusDefaultFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusDefaultFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.cus_load_more_footer, this);
        mTextView = (TextView) findViewById(R.id.cube_views_load_more_default_footer_text_view);
    }

    @Override
    public void onLoading(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText("正在加载…");
    }

    @Override
    public void onLoadFinish(LoadMoreContainer container, boolean hasMore) {
        if (!hasMore) {
            setVisibility(VISIBLE);
            mTextView.setText("全部数据加载完毕");
        } else {
            setVisibility(GONE);
        }
    }

    @Override
    public void onWaitToLoadMore(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText("点击加载更多");
    }

    @Override
    public void onLoadError(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText("加载失败,点击重试");
    }

}
