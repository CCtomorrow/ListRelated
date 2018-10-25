package com.ai.listrelated.slideview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2018/10/19 <br>
 * <b>@author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> Custom LayoutManager <br>
 */
public class SwipeLayoutManager extends RecyclerView.LayoutManager implements ItemTouchHelper.ViewDropHandler, RecyclerView.OnItemTouchListener {

    /**
     * 需要展示的View的个数
     */
    private int showItemCount;
    private float scaleGapX;
    private float scaleGapY;
    private float[][] scales;
    private RecyclerView mRecyclerView;
    private RecyclerView.Recycler mRecycler;

    /**
     * 捕捉用户松手之后的一些操作
     */
    private float mTouchSlop;
    private int mDuration = 380;
    private int mInDuration = 380;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private int mMaxFlingVelocity;
    private int mMiniFlingVelocity;
    private VelocityTracker mVelocityTracker;

    /**
     * 默认状态;手指上滑，看底部;手指下滑，看顶部
     */
    private static final int STATE_DEF = -1;
    private static final int STATE_UP = 0;
    private static final int STATE_DOWN = 1;

    /**
     * 手指滑动状态
     */
    private int mScrollerState = STATE_DEF;
    /**
     * 动画还没执行完成不允许滑动
     */
    private boolean canScroller = true;
    /**
     * 手指松开后的minDuration的时间里面不允许再次滑动
     */
    //private boolean isCanScrollerAgain = true;
    private long downInLast = 0;
    private int mVerticalOffset;
    private int mFirstVisiPos;

    private ILoadMore mILoadMore;

    private static final String tag = "SwipeLayoutManager";

    public interface ILoadMore {
        void onLoadMore();
    }

    public SwipeLayoutManager(Context context, RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        showItemCount = 2;
        scaleGapX = 0.06F;
        scaleGapY = 0.045F;
        scales = new float[showItemCount + 1][2];
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaxFlingVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMiniFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        mRecyclerView.setChildDrawingOrderCallback(new RecyclerView.ChildDrawingOrderCallback() {
            @Override
            public int onGetChildDrawingOrder(int childCount, int i) {
                //反序绘制
                return childCount - 1 - i;
            }
        });
        //需要监听手指的按下抬起以及速度事件
        mRecyclerView.addOnItemTouchListener(this);
        //直接禁止掉RV的Fling操作，自己来做
        mRecyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                return true;
            }
        });
    }

    public void setILoadMore(ILoadMore loadMore) {
        mILoadMore = loadMore;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private View getChildClosestToStart() {
        //添加的时候是进行了反转的
        return getChildAt(0);
    }

    private View getChildClosestToEnd() {
        //添加的时候是进行了反转的
        return getChildAt(getChildCount() - 1);
    }

    @Override
    public void prepareForDrop(View view, View target, int x, int y) {
    }

    @Override
    public boolean canScrollVertically() {
        return true;
        //return isCanScrollerAgain;
    }

    private void scrollBy(RecyclerView.State state) {
        View topView = getChildClosestToStart();
        View bottomView = getChildClosestToEnd();
        int bottomPosition = getPosition(bottomView);
        if (mScrollerState == STATE_UP) {
            if (mVerticalOffset < 0) {
                return;
            }
            //Log.e(tag, "上滑看底部 mVerticalOffset" + mVerticalOffset);
            //期望最顶部的View向上滑动到1/3，底层View就已经正常了
            if (getChildCount() < showItemCount + 1 && bottomPosition < state.getItemCount() - 1) {
                View futureView = mRecycler.getViewForPosition(bottomPosition + 1);
                addView(futureView);
                measureChildWithMargins(futureView, 0, 0);
                int widthSpce = getWidth() - getDecoratedMeasuredWidth(futureView);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(futureView);
                int left = (widthSpce - getPaddingLeft() - getPaddingRight()) / 2;
                int top = (heightSpace - getPaddingTop() - getPaddingBottom()) / 2;
                int right = left + getDecoratedMeasuredWidth(futureView);
                int bottom = top + getDecoratedMeasuredHeight(futureView);
                layoutDecorated(futureView, left, top, right, bottom);
                //TranslationY必须重置为0
                futureView.setTranslationY(0);
                //要把此View的Scale设置到最后一个View的下一个View的水平
                futureView.setScaleX(bottomView.getScaleX() - scaleGapX);
                futureView.setScaleY(bottomView.getScaleY() + scaleGapY);
            }
            //Log.e(tag, "before topView translationY:" + topView.getTranslationY());
            // 顶部View不用变化
            topView.setTranslationY(-mVerticalOffset);
            //Log.e(tag, "after topView translationY:" + topView.getTranslationY());
            for (int i = 1; i < getChildCount(); i++) {
                View child = getChildAt(i);
                //x 0.89 ---> 0.945
                //y 0.94 ---> 0.91
                float scx = scales[i][0] + ((scales[i - 1][0] - scales[i][0]) *
                        Math.abs(mVerticalOffset) * 3 / topView.getHeight());
                float scy = scales[i][1] - ((scales[i][1] - scales[i - 1][1]) *
                        Math.abs(mVerticalOffset) * 3 / topView.getHeight());
                if (child.getScaleX() < scales[i - 1][0]) {
                    child.setScaleX(scx);
                }
                if (child.getScaleY() > scales[i - 1][1]) {
                    child.setScaleY(scy);
                }
            }
        } else if (mScrollerState == STATE_DOWN) {
            if (mVerticalOffset > 0) {
                return;
            }
            //Log.e(tag, "下滑看顶部 mVerticalOffset" + mVerticalOffset);
            // TODO: 2018/10/25 临时方案，这样并不好，只能适用显示2个item的情况
            boolean could = false;
            if (mFirstVisiPos == state.getItemCount() - 1 && getChildCount() == 1) {
                // 最后一个
                could = true;
            } else if (mFirstVisiPos == state.getItemCount() - 2 && getChildCount() == 2) {
                //倒数第二个
                could = true;
            } else if (mFirstVisiPos < state.getItemCount() - showItemCount && getChildCount() == showItemCount) {
                could = true;
            }
            if (could) {
                View futureView = mRecycler.getViewForPosition(getPosition(topView) - 1);
                addView(futureView, 0);
                measureChildWithMargins(futureView, 0, 0);
                int widthSpce = getWidth() - getDecoratedMeasuredWidth(futureView);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(futureView);
                int left = (widthSpce - getPaddingLeft() - getPaddingRight()) / 2;
                int top = (heightSpace - getPaddingTop() - getPaddingBottom()) / 2;
                int right = left + getDecoratedMeasuredWidth(futureView);
                int bottom = top + getDecoratedMeasuredHeight(futureView);
                layoutDecorated(futureView, left, top, right, bottom);
                //要把此View的Scale设置到第一个View的水平
                futureView.setScaleX(topView.getScaleX());
                futureView.setScaleY(topView.getScaleY());
                futureView.setTranslationY(-getVerticalSpace());
            }
            View newTopView = getChildClosestToStart();
            newTopView.setTranslationY(-getVerticalSpace() - mVerticalOffset);
            for (int i = 1; i < getChildCount(); i++) {
                View child = getChildAt(i);
                //x 0.945 ---> 0.89
                //y 0.91 ---> 0.94
                float scx = scales[i - 1][0] - ((scales[i - 1][0] - scales[i][0]) *
                        Math.abs(mVerticalOffset) * 3 / newTopView.getHeight());
                float scy = scales[i - 1][1] + ((scales[i][1] - scales[i - 1][1]) *
                        Math.abs(mVerticalOffset) * 3 / newTopView.getHeight());
                if (child.getScaleX() > scales[i][0]) {
                    child.setScaleX(scx);
                }
                if (child.getScaleY() < scales[i][1]) {
                    child.setScaleY(scy);
                }
            }
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //位移0、没有子View 当然不移动
        if (dy == 0 || getChildCount() == 0) {
            return 0;
        }
        if (!canScroller) {
            return 0;
        }
        //顶部下拉
        if (dy < 0 && mVerticalOffset <= 0 && mFirstVisiPos <= 0) {
            return 0;
        }
        //底部上拉
        if (dy > 0 && mVerticalOffset >= 0 && mFirstVisiPos == getItemCount() - 1) {
            return 0;
        }
        if (mScrollerState == STATE_DEF) {
            mScrollerState = dy > 0 ? STATE_UP : STATE_DOWN;
        }
        //Log.e(tag, "scrollVerticallyBy dy:" + dy);
        mVerticalOffset += dy;
        scrollBy(state);
        return dy;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        mRecycler = recycler;
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }
        if (mScrollerState == STATE_DEF) {
            mVerticalOffset = 0;
        }
        //Log.e(tag, "onLayoutChildren mScrollerState:" + mScrollerState + "  mVerticalOffset:" + mVerticalOffset);
        //移除所有的view
        detachAndScrapAttachedViews(recycler);
        //将可见区域的childView layout出来
        layoutScrap(recycler);
    }

    /**
     * 将可见区域的childView layout出来
     */
    private void layoutScrap(RecyclerView.Recycler recycler) {
        int startIndex = mFirstVisiPos;
        int endIndex = mFirstVisiPos + showItemCount;
        if (endIndex > getItemCount()) {
            endIndex = getItemCount();
        }
        for (int i = startIndex; i < endIndex; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpce = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            int left = (widthSpce - getPaddingLeft() - getPaddingRight()) / 2;
            int top = (heightSpace - getPaddingTop() - getPaddingBottom()) / 2;
            int right = left + getDecoratedMeasuredWidth(view);
            int bottom = top + getDecoratedMeasuredHeight(view);
            layoutDecorated(view, left, top, right, bottom);
            int start = i - startIndex;
            int end = startIndex + showItemCount;
            //x 0.945 0.89 0.835
            //y 0.91 0.94 0.97
            float scx = 1 - (start + 1) * scaleGapX;
            float scy = 1 - (end - i) * scaleGapY;
            view.setScaleX(scx);
            view.setScaleY(scy);
            //测试过程发现，滑动过程，会触发到onLayoutChildren
            if (start == 0) {
                if (mScrollerState == STATE_UP) {
                    //Log.e(tag, "layoutScrap STATE_UP mVerticalOffset:" + mVerticalOffset);
                    view.setTranslationY(-mVerticalOffset);
                }
//                else if (mScrollerState == STATE_DOWN) {
//                    //Log.e(tag, "layoutScrap STATE_DOWN mVerticalOffset:" + mVerticalOffset);
//                    view.setTranslationY(-getVerticalSpace() - mVerticalOffset);
//                }
                else {
                    view.setTranslationY(0);
                }
            } else {
                view.setTranslationY(0);
            }
            scales[start][0] = scx;
            scales[start][1] = scy;
            if (start == showItemCount - 1) {
                //新加入的和最后一个状态相同
                //scales[start + 1][0] = scx - scaleGapX;
                //scales[start + 1][1] = scy + scaleGapY;
                scales[start + 1][0] = scx;
                scales[start + 1][1] = scy;
            }
        }
    }

    void obtainVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
        }
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent event) {
        final int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            //Log.e(tag, "onInterceptTouchEvent ACTION_DOWN");
            mInitialTouchX = event.getX();
            mInitialTouchY = event.getY();
            obtainVelocityTracker();
        } else if (action == MotionEvent.ACTION_MOVE) {
//            if (mScrollerState == STATE_DEF) {
//                mScrollerState = scrollerState(event);
//            }
            //Log.e(tag, "onInterceptTouchEvent ACTION_MOVE");
        } else if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            //Log.e(tag, "onInterceptTouchEvent ACTION_UP");
            //isCanScrollerAgain = false;
            upHandle();
//            mRecyclerView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    isCanScrollerAgain = true;
//                }
//            }, mInDuration * 2);
            mScrollerState = STATE_DEF;
            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(event);
        }
        return false;
    }

    /**
     * 顶部View滑动出去
     */
    public void smtoOut() {
        for (int i = 1; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            //x 0.89 ---> 0.945
            //y 0.94 ---> 0.91
            child.animate()
                    .scaleXBy((scales[i - 1][0] - child.getScaleX()))
                    .scaleYBy(-(child.getScaleY() - scales[i - 1][1]))
                    .setDuration(mDuration)
                    .start();
        }
        final View topView = getChildClosestToStart();
        // 顶部View不用变化
        int all = getVerticalSpace();
        //手指往上滑动，getTranslationY为负数
        float originy = topView.getTranslationY();
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(topView, "translationY", originy, -all);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(mDuration);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                canScroller = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFirstVisiPos++;
                removeAndRecycleView(topView, mRecycler);
                canScroller = true;
                if (mFirstVisiPos == getItemCount() - showItemCount) {
                    if (mILoadMore != null) {
                        mILoadMore.onLoadMore();
                    }
                }
            }
        });
        animator.start();
    }

    /**
     * 顶部View滑动到原位置
     */
    private void smtoOrigin() {
        for (int i = 1; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //x 0.945 ---> 0.89
            //y 0.91 ---> 0.94
            child.animate()
                    .scaleXBy(-(child.getScaleX() - scales[i][0]))
                    .scaleYBy((scales[i][1] - child.getScaleY()))
                    .setDuration(mDuration)
                    .start();
        }
        View topView = getChildClosestToStart();
        //手指往上滑动，getTranslationY为负数
        float originy = topView.getTranslationY();
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(topView, "translationY", originy, 0);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(mDuration);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                canScroller = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (getChildCount() > showItemCount) {
                    removeAndRecycleView(getChildClosestToEnd(), mRecycler);
                }
                canScroller = true;
            }
        });
        animator.start();
    }

    /**
     * 已经滑出去的顶部View滑动回来
     */
    private void smtoIn() {
        for (int i = 1; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //x 0.945 ---> 0.89
            //y 0.91 ---> 0.94
            child.animate()
                    .scaleXBy(-(child.getScaleX() - scales[i][0]))
                    .scaleYBy((scales[i][1] - child.getScaleY()))
                    .setDuration(mInDuration)
                    .start();
        }
        final View topView = getChildClosestToStart();
        float originy = topView.getTranslationY();
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(topView, "translationY", originy, 0);
        animator.setInterpolator(new OvershootInterpolator(1F));
        animator.setDuration(mInDuration);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                canScroller = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFirstVisiPos--;
                if (getChildCount() > showItemCount) {
                    removeAndRecycleView(getChildClosestToEnd(), mRecycler);
                }
                canScroller = true;
            }
        });
        animator.start();
    }

    /**
     * 已经滑了部分进来的顶部View滑动回去消失不见
     */
    private void smtoDelete() {
        for (int i = 1; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            //x 0.89 ---> 0.945
            //y 0.94 ---> 0.91
            child.animate()
                    .scaleXBy((scales[i - 1][0] - child.getScaleX()))
                    .scaleYBy(-(child.getScaleY() - scales[i - 1][1]))
                    .setDuration(mInDuration)
                    .start();
        }
        View topView = getChildClosestToStart();
        float originy = topView.getTranslationY();
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(topView, "translationY", originy, -getVerticalSpace());
        animator.setDuration(mInDuration);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                canScroller = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (getChildCount() > showItemCount) {
                    removeAndRecycleView(getChildClosestToStart(), mRecycler);
                }
                canScroller = true;
            }
        });
        animator.start();
    }

    private int scrollerState(MotionEvent event) {
        final float xDiff = event.getX() - mInitialTouchX;
        final float yDiff = event.getY() - mInitialTouchY;
        //上滑
        boolean isScrollY = Math.abs(yDiff) > mTouchSlop && Math.abs(yDiff) >= Math.abs(xDiff);
        if (isScrollY && yDiff < 0) {
            return STATE_UP;
        } else if (isScrollY && yDiff > 0 && mFirstVisiPos != 0) {
            return STATE_DOWN;
        }
        return STATE_DEF;
    }

    /**
     * up或者cancel事件的处理
     */
    private void upHandle() {
        float distance = Math.abs(mVerticalOffset);
        boolean couldToNext = Math.abs(distance) >= getVerticalSpace() / 3;
        mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
        boolean needToNext = false;
        if (mScrollerState == STATE_UP) {
            needToNext = -mVelocityTracker.getYVelocity() > mMiniFlingVelocity * 4;
        } else if (mScrollerState == STATE_DOWN) {
            needToNext = mVelocityTracker.getYVelocity() > mMiniFlingVelocity * 4;
        }
        if (mScrollerState == STATE_UP) {
            if ((couldToNext || needToNext) && mFirstVisiPos != getItemCount() - 1) {
                //Log.e(tag, "upHandle out");
                smtoOut();
            } else {
                //Log.e(tag, "upHandle origin");
                smtoOrigin();
            }
        } else if (mScrollerState == STATE_DOWN) {
            if (couldToNext || needToNext) {
                //Log.e(tag, "upHandle in");
                long now = System.currentTimeMillis();
                if (now - downInLast < mInDuration) {
                    return;
                }
                downInLast = now;
                smtoIn();
            } else {
                //Log.e(tag, "upHandle del");
                smtoDelete();
            }
        }
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent event) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public int getDecoratedMeasurementHorizontal(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + params.leftMargin + params.rightMargin;
    }

    public int getDecoratedMeasurementVertical(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + params.topMargin + params.bottomMargin;
    }

    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

}
