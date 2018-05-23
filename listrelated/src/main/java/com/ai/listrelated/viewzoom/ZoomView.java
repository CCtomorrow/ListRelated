package com.ai.listrelated.viewzoom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2018/5/23 <br>
 * <b>@author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 可以放大以及缩小的View <br>
 */
public class ZoomView extends View {

    public static final float SCALE_INIT = 1.0F;
    public static final float SCALE_MID = 2.0F;
    public static final float SCALE_MAX = 4.0F;

    private final float[] matrixValues = new float[9];
    private float mTouchSlop;

    private boolean isCheckTopAndBottom, isCheckLeftAndRight, isAutoScale, enableAutoScale;

    private ScaleGestureDetector mScaleGestureDetector = null;
    private MoveGestureDetector moveGestureDetector = null;
    private GestureDetector mGestureDetector;
    private final Matrix mScaleMatrix = new Matrix();

    private TextPaint paint;
    private String text = "啦啦啦";

    public ZoomView(Context context) {
        this(context, null);
    }

    public ZoomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        paint.setTextSize(50);
        Handler handler = new Handler(Looper.getMainLooper());
        mScaleGestureDetector = new ScaleGestureDetector(context, mOnScaleGestureListener);
        moveGestureDetector = new MoveGestureDetector(context, mOnMoveGestureListener);
        mGestureDetector = new GestureDetector(context, mOnGestureListener, handler);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(1000, 1000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 放大缩小
        canvas.concat(mScaleMatrix);
        // 绘制操作
        canvas.drawColor(Color.LTGRAY);
        canvas.drawLine(0, getHeight() / 2 - 1, getWidth(), getHeight() / 2 + 1, paint);
        canvas.drawLine(getWidth() / 2 - 1, 0, getWidth() / 2 + 1, getHeight(), paint);
        float width = paint.measureText(text);
        float left = getWidth() / 2 - width / 2;
        float top = getHeight() / 2 - (paint.ascent() + paint.descent()) / 2;
        canvas.drawText(text, left, top, paint);
    }

    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rect = new RectF();
        rect.set(0, 0, getWidth(), getHeight());
        matrix.mapRect(rect);
        return rect;
    }

    /**
     * 获取当前的缩放系数，因为所以x，y缩放系数一样这里获取一个就行了
     */
    public final float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    /**
     * 缩放范围的检测
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        // 如果宽或高大于View的宽高，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于View的宽高，则让其居中
        if (rect.width() < width) {
            deltaX = width * 0.5F - rect.right + 0.5F * rect.width();
        }
        if (rect.height() < height) {
            deltaY = height * 0.5F - rect.bottom + 0.5F * rect.height();
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (!enableAutoScale) {
                return false;
            }
            if (isAutoScale) {
                return true;
            }
            float x = e.getX();
            float y = e.getY();
            if (getScale() < SCALE_MID) {
                ZoomView.this.postDelayed(
                        new AutoScaleRunnable(SCALE_MID, x, y), 16);
                isAutoScale = true;
            } else if (getScale() >= SCALE_MID
                    && getScale() < SCALE_MAX) {
                ZoomView.this.postDelayed(
                        new AutoScaleRunnable(SCALE_MAX, x, y), 16);
                isAutoScale = true;
            } else {
                ZoomView.this.postDelayed(
                        new AutoScaleRunnable(SCALE_INIT, x, y), 16);
                isAutoScale = true;
            }
            return true;
        }
    };

    private class AutoScaleRunnable implements Runnable {
        static final float BIGGER = 1.07F;
        static final float SMALLER = 0.93F;
        private float mTargetScale;
        private float tmpScale;
        /**
         * 缩放的中心
         */
        private float x;
        private float y;

        /**
         * 传入目标缩放值，根据目标值与当前值，判断应该放大还是缩小
         *
         * @param targetScale
         */
        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            } else {
                tmpScale = SMALLER;
            }
        }

        @Override
        public void run() {
            // 进行缩放
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            invalidate();
            final float currentScale = getScale();
            //如果值在合法范围内，继续缩放
            if (((tmpScale > 1f) && (currentScale < mTargetScale))
                    || ((tmpScale < 1f) && (mTargetScale < currentScale))) {
                ZoomView.this.postDelayed(this, 16);
            } else {
                //设置为目标的缩放比例
                final float deltaScale = mTargetScale / currentScale;
                mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
                checkBorderAndCenterWhenScale();
                invalidate();
                isAutoScale = false;
            }
        }
    }

    ScaleGestureDetector.OnScaleGestureListener mOnScaleGestureListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // 第一次 1.0F
            float scale = getScale();
            // 第一次 1.0F
            float scaleFactor = detector.getScaleFactor();
            // 缩放的范围控制
            if ((scale < SCALE_MAX && scaleFactor > SCALE_INIT)
                    || (scale > SCALE_INIT && scaleFactor < SCALE_INIT)) {
                // 最大值最小值判断
                if (scaleFactor * scale < SCALE_INIT) {
                    scaleFactor = SCALE_INIT / scale;
                }
                if (scaleFactor * scale > SCALE_MAX) {
                    scaleFactor = SCALE_MAX / scale;
                }
                // 设置缩放比例
                mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
                checkBorderAndCenterWhenScale();
                invalidate();
            }
            return true;
        }
    };

    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }

    /**
     * 超出边界的一个还原算法
     */
    private void checkMatrixBounds() {
        RectF rect = getMatrixRectF();
        float deltaX = 0, deltaY = 0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.top > 0 && isCheckTopAndBottom) {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom) {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight) {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight) {
            deltaX = viewWidth - rect.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    MoveGestureDetector.OnMoveGestureListener mOnMoveGestureListener = new MoveGestureDetector.SimpleMoveGestureDetector() {

        @Override
        public boolean onMove(MoveGestureDetector detector) {
            float moveX = (int) detector.getMoveX();
            float moveY = (int) detector.getMoveY();
            RectF rect = getMatrixRectF();
            isCheckLeftAndRight = isCheckTopAndBottom = true;
            // 如果宽度小于屏幕宽度，则禁止左右移动
            if (rect.width() < getWidth()) {
                moveX = 0;
                isCheckLeftAndRight = false;
            }
            // 如果高度小雨屏幕高度，则禁止上下移动
            if (rect.height() < getHeight()) {
                moveY = 0;
                isCheckTopAndBottom = false;
            }
            mScaleMatrix.postTranslate(moveX, moveY);
            checkMatrixBounds();
            invalidate();
            return true;
        }

    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        moveGestureDetector.onToucEvent(event);
        return true;
    }
}
