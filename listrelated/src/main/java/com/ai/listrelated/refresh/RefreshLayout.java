package com.ai.listrelated.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/8 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class RefreshLayout extends SwipeRefreshLayout {

    private RefreshHandler mRefreshHandler;
    private View mTarget;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRefreshHandler(RefreshHandler refreshHandler, View target) {
        mRefreshHandler = refreshHandler;
        mTarget = target;
    }

    @Override
    public boolean canChildScrollUp() {
        if (mRefreshHandler != null && mTarget != null) {
            return mRefreshHandler.checkCanScrollUp(mTarget);
        }
        return super.canChildScrollUp();
    }


}
