package com.ai.listrelated.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ai.listrelated.R;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/5 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 基于FrameLayout的相对布局 <br>
 */
public class OppositeLayout extends FrameLayout {

    public OppositeLayout(Context context) {
        super(context);
    }

    public OppositeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            if (lp.horizontalCenterId != 0) {
                View view2 = findViewById(lp.horizontalCenterId);
                int left = view2.getLeft() + (view2.getWidth() - view.getMeasuredWidth()) / 2;
                view.layout(left, view.getTop(), left + view.getWidth(), view.getBottom());
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSize > 0 && heightSize > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                LayoutParams lp = (LayoutParams) view.getLayoutParams();
                if (lp.relativeWidth > 0) {
                    lp.width = (int) (lp.relativeWidth * widthSize);
                    if (lp.originalWidth > 0) {
                        lp.width = lp.originalWidth + lp.width;
                    }
                }
                if (lp.relativeHeight > 0) {
                    lp.height = (int) (lp.relativeHeight * heightSize);
                    if (lp.originalHeight > 0) {
                        lp.height = lp.originalHeight + lp.height;
                    }
                }

                if (lp.relativeWidth < 0) {
                    if (lp.height > 0) {
                        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                        float scale = ((float) lp.height) / view.getMeasuredHeight();
                        lp.width = (int) (view.getMeasuredWidth() * scale * -lp.relativeWidth);
                        if (lp.originalWidth > 0) {
                            lp.width = lp.originalWidth + lp.width;
                        }
                    }
                }
                if (lp.relativeHeight < 0) {
                    if (lp.width > 0) {
                        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                        float scale = ((float) lp.width) / view.getMeasuredWidth();
                        lp.height = (int) (view.getMeasuredHeight() * scale * -lp.relativeHeight);
                        if (lp.originalHeight > 0) {
                            lp.height = lp.originalHeight + lp.height;
                        }
                    }
                }

                if (lp.relativeTop != 0) {
                    lp.topMargin = (int) (lp.relativeTop * heightSize);
                    if (lp.originalTopMargin > 0) {
                        lp.topMargin = lp.originalTopMargin + lp.topMargin;
                    }
                }
                if (lp.relativeBottom != 0) {
                    lp.bottomMargin = (int) (lp.relativeBottom * heightSize);
                    if (lp.originalBottomMargin > 0) {
                        lp.bottomMargin = lp.originalBottomMargin + lp.bottomMargin;
                    }
                }
                if (lp.relativeLeft != 0) {
                    lp.leftMargin = (int) (lp.relativeLeft * widthSize);
                    if (lp.originalLeftMargin > 0) {
                        lp.leftMargin = lp.originalLeftMargin + lp.leftMargin;
                    }
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {

        /**
         * 如果某个View设置次属性，那么其余所有的View都按照这个View的水平中心对齐
         */
        protected int horizontalCenterId;

        /**
         * 相对于整个控件的顶部的偏移百分比，比如0.5即距离整个控件顶部一半的高度
         */
        protected float relativeTop;
        protected float relativeBottom;
        protected float relativeLeft;

        /**
         * 相对于整个控件的宽高，比如0.5表示占整个控件一半宽
         */
        protected float relativeHeight;
        protected float relativeWidth;

        /**
         * 原本的margin，即设置android:layout_marignTop的值
         */
        protected int originalTopMargin;
        protected int originalBottomMargin;
        protected int originalLeftMargin;

        /**
         * 原本的宽和高，记得MATCH_PARENT值为-1，WRAP_CONTENT值为-2
         */
        protected int originalHeight;
        protected int originalWidth;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            originalWidth = width;
            originalHeight = height;

            originalTopMargin = topMargin;
            originalBottomMargin = bottomMargin;
            originalLeftMargin = leftMargin;

            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OppositeLayout);

            horizontalCenterId = typedArray.getResourceId(R.styleable.OppositeLayout_layout_horizontal_center, 0);
            relativeTop = typedArray.getFloat(R.styleable.OppositeLayout_layout_relative_top, 0);
            relativeBottom = typedArray.getFloat(R.styleable.OppositeLayout_layout_relative_bottom, 0);
            relativeLeft = typedArray.getFloat(R.styleable.OppositeLayout_layout_relative_left, 0);

            relativeHeight = typedArray.getFloat(R.styleable.OppositeLayout_layout_relative_height, 0);
            relativeWidth = typedArray.getFloat(R.styleable.OppositeLayout_layout_relative_width, 0);

            typedArray.recycle();
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

}
