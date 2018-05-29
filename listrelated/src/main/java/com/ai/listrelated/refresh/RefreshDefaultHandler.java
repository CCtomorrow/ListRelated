package com.ai.listrelated.refresh;

import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/8 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> View是否可以下拉刷新的默认实现，参考的{@link SwipeRefreshLayout#canChildScrollUp()} <br>
 */
public class RefreshDefaultHandler implements RefreshHandler {

    @Override
    public boolean checkCanScrollUp(View target) {
        // mScrollY <= 0 的时候就表示可以下拉刷新了
        // 返回值返回是否可以向上滑动
        return canChildScrollUp(target);
    }

    /**
     * ViewCompat.canScrollVertically(view, int) ，第二个int类型参数
     * 负数: 顶部是否可以往下滚动
     * 正数: 底部是否可以往上滚动
     * 官方描述：“Negative to check scrolling up, positive to check scrolling down”
     * 这里的check不能翻译成“是否”，而是“停止、制止、阻止、遏制”的意思，so：
     * 第二个参数int 负数表示阻止（停止）向上滑动，正数表示阻止（停止）向下滑动。
     *
     * @param view
     * @return
     */
    public static boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                // -1 表示向上滑动
                return ViewCompat.canScrollVertically(view, -1) || view.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(view, -1);
        }
    }

}
