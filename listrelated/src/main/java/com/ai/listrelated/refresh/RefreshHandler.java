package com.ai.listrelated.refresh;

import android.view.View;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/8 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 下拉刷新的功能接口，是否可以下拉刷新 <br>
 */
public interface RefreshHandler {
    /**
     * View是否可以下拉刷新
     *
     * @param target
     * @return
     */
    boolean checkCanScrollUp(View target);
}
