package com.ai.listrelated.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ai.listrelated.view.GridViewWithHeaderAndFooter;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2016/12/30 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> Loadmore包裹的Layout <br>
 */
public class LoadMoreGridViewContainer extends LoadMoreAbsListViewContainer {

    private GridViewWithHeaderAndFooter mGridView;

    public LoadMoreGridViewContainer(Context context) {
        super(context);
    }

    public LoadMoreGridViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreGridViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void addFooterView(View view) {
        if (mGridView != null) {
            mGridView.addFooterView(view);
        }
    }

    @Override
    protected void removeFooterView(View view) {
        if (mGridView != null) {
            mGridView.removeFooterView(view);
        }
    }

    @Override
    protected Object retrieveListView() {
        View view = null;
        for (int i = 0; i < getChildCount(); i++) {
            View tempView = getChildAt(i);
            if (tempView instanceof GridViewWithHeaderAndFooter) {
                view = tempView;
                break;
            }
        }
        if (view != null) {
            mGridView = (GridViewWithHeaderAndFooter) view;
            return mGridView;
        } else {
            return null;
        }
    }

}