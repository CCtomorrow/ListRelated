package com.ai.listrelated.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2016/12/30 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> Loadmore包裹的Layout <br>
 */
public class LoadMoreListViewContainer extends LoadMoreAbsListViewContainer {

    private ListView mListView;

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
        View view = null;
        for (int i = 0; i < getChildCount(); i++) {
            View tempView = getChildAt(i);
            if (tempView instanceof ListView) {
                view = tempView;
                break;
            }
        }
        if (view != null) {
            mListView = (ListView) view;
            return mListView;
        } else {
            return null;
        }
    }

}
