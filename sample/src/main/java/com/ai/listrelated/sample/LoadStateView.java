package com.ai.listrelated.sample;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ai.listrelated.sample.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/7 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 加载数据过程中，加载，成功，失败的状态的View <br>
 */
public class LoadStateView extends LinearLayout {

    public static final int LOAD_ERROR = Integer.MAX_VALUE;
    public static final int LOAD_EMPTY = Integer.MAX_VALUE - 1;
    public static final int LOAD_LOADING = Integer.MAX_VALUE - 2;
    public static final int LOAD_SUCCESS = Integer.MAX_VALUE - 3;

    private ImageView mImageView;
    private TextView mTextView;
    private ProgressBar mProgressBar;

    @IntDef({LOAD_ERROR, LOAD_EMPTY, LOAD_LOADING, LOAD_SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LOAD_STATE_TYPE {

    }

    public LoadStateView(Context context) {
        super(context);
    }

    public LoadStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.load_state_iv);
        mTextView = (TextView) findViewById(R.id.load_state_tv);
        mProgressBar = (ProgressBar) findViewById(R.id.load_state_pb);
        // 默认就是正在加载
        // setType(LOAD_LOADING);
    }

    public void setType(@LOAD_STATE_TYPE int type) {
        setVisibility(VISIBLE);
        mProgressBar.setVisibility(GONE);
        switch (type) {
            case LOAD_ERROR:
                mImageView.setImageResource(R.drawable.ic_net_error);
                mTextView.setText("亲,网络有点差哦,点击可以重新加载哟");
                break;
            case LOAD_EMPTY:
                mImageView.setImageDrawable(null);
                mTextView.setText("亲,暂无数据呢");
                break;
            case LOAD_LOADING:
                mImageView.setImageDrawable(null);
                mProgressBar.setVisibility(VISIBLE);
                mTextView.setText("正在加载,请稍后哒");
                break;
            case LOAD_SUCCESS:
                mImageView.setImageDrawable(null);
                mTextView.setText("");
                setVisibility(GONE);
                break;
        }
    }

    public void setListener(OnClickListener listener) {
        this.setOnClickListener(listener);
    }

}
