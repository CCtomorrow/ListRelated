package com.ai.listrelated.sample.defaultimpl;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/01/11 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 适用于GridLayoutManager的margin实现 <br>
 * 说明：这个是特殊开发的，并不能适用通用的
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private SpanSizeLookup spanSizeLookup;

    public GridItemDecoration(int space, SpanSizeLookup spanSizeLookup) {
        this.space = space;
        this.spanSizeLookup = spanSizeLookup;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spanCount = getSpanCount(parent);
        int count = parent.getAdapter().getItemCount();
        int position = parent.getChildLayoutPosition(view);
        int spanSize = spanSizeLookup.getSpanSize(position);
        // 计算开始
        outRect.top = space;
        if (spanSize == 1) {
            outRect.left = space; // 默认每个item左边都有间距，剩下的只需要计算，每行最后一个item的右边距即可
            if (position % spanCount == 0) {
                outRect.right = space;
            }
        } else if (spanSize == spanCount) {
            outRect.left = space;
            outRect.right = space;
        }
        // 底部
        if (count > 1) {
            if (count % 2 == 0) { // 最后一个数据有bottom即可
                if (position == count - 1) {
                    outRect.bottom = space;
                }
            } else {
                if (position == count - 1 || position == count - 2) {
                    outRect.bottom = space;
                }
            }
        }
    }

    /**
     * 获取SpanCount
     */
    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

}
