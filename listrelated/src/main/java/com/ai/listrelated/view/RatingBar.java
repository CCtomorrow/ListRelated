package com.ai.listrelated.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.ai.listrelated.R;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2018/6/6 <br>
 * <b>@author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 自定义rating bar <br>
 */
public class RatingBar extends View {

    private Paint mBitmapPaint;
    private TextPaint mTextPaint;

    private int mPicWidth;
    private int mPicHeight;
    private int mDrawablePadding;
    private BitmapDrawable mDrawable = null;

    private int mTextWidth;
    private int mTextHeight;
    private String mText = "";
    private ColorStateList mTextColor = null;
    private int mCurrentColor;
    private int mTextSize = 15;
    private int mPictextPadding;

    private int mstarNum = 0;
    private int mStarColor;

    private int mStar;

    private PorterDuffColorFilter mDuffColorFilter;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConsRatingBar);
        mDrawable = (BitmapDrawable) a.getDrawable(R.styleable.ConsRatingBar_android_src);
        mDrawablePadding = a.getDimensionPixelSize(R.styleable.ConsRatingBar_android_drawablePadding, mDrawablePadding);

        mText = (String) a.getText(R.styleable.ConsRatingBar_android_text);
        mTextSize = a.getDimensionPixelSize(R.styleable.ConsRatingBar_android_textSize, mTextSize);
        mTextColor = a.getColorStateList(R.styleable.ConsRatingBar_android_textColor);
        mPictextPadding = a.getDimensionPixelSize(R.styleable.ConsRatingBar_pictextPadding, mPictextPadding);

        mstarNum = a.getInt(R.styleable.ConsRatingBar_android_numStars, mstarNum);
        mStarColor = a.getColor(R.styleable.ConsRatingBar_starColor, 0);
        a.recycle();

        initPaint();

        if (mStarColor != 0) {
            mDuffColorFilter = new PorterDuffColorFilter(mStarColor, PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initPaint() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        if (mTextColor == null) {
            mTextColor = ColorStateList.valueOf(0xFF000000);
        }
        int color = mTextColor.getColorForState(getDrawableState(), 0);
        if (color != mCurrentColor) {
            mCurrentColor = color;
        }
        mTextPaint.setColor(mCurrentColor);
        mTextPaint.setTextSize(mTextSize);
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

    /**
     * 更新颜色
     */
    private void updateTextColors() {
        boolean inval = false;
        int color = mTextColor.getColorForState(getDrawableState(), 0);
        if (color != mCurrentColor) {
            mCurrentColor = color;
            inval = true;
        }
        if (inval) {
            invalidate();
        }
    }

    public void setStar(int star) {
        mStar = star;
        invalidate();
    }

    private void measureText() {
        mTextWidth = (int) mTextPaint.measureText(mText);
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTextHeight = (int) Math.ceil(fm.descent - fm.top);
    }

    private void measurePic() {
        mPicWidth = mDrawable.getIntrinsicWidth() * mstarNum + (mstarNum - 1) * mDrawablePadding;
        mPicHeight = mDrawable.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureText();
        measurePic();
        int width = getMeasureWidth(widthMeasureSpec);
        int height = getMeasureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 测量View的宽度
     *
     * @param widthMeasureSpec widthMeasureSpec
     * @return 返回View的测量宽度
     */
    private int getMeasureWidth(int widthMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            int padding = getPaddingLeft() + getPaddingRight();
            // 文字宽度，图片宽度
            result = padding + mTextWidth + mPictextPadding + mPicWidth;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    /**
     * 测量View的高度
     *
     * @param heightMeasureSpec heightMeasureSpec
     * @return 返回View的测量高度
     */
    private int getMeasureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            int padding = getPaddingTop() + getPaddingBottom();
            result = Math.max(mTextHeight, mPicHeight) + padding;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int y = (int) (getHeight() / 2 - (mTextPaint.ascent() + mTextPaint.descent()) / 2);
        canvas.drawText(mText, getPaddingLeft(), y, mTextPaint);
        int left = getPaddingLeft() + mTextWidth + mPictextPadding;
        int top = getHeight() / 2 - mPicHeight / 2;
        for (int i = 0; i < mstarNum; i++) {
            if (mStar > 0 && (mStar - 1) >= i) {
                mBitmapPaint.setColorFilter(mDuffColorFilter);
            } else {
                mBitmapPaint.setColorFilter(null);
            }
            int dx = left + mDrawable.getIntrinsicWidth() * i + mDrawablePadding * i;
            canvas.drawBitmap(mDrawable.getBitmap(), dx, top, mBitmapPaint);
        }
    }
}
