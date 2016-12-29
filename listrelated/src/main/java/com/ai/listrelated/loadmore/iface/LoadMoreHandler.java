package com.ai.listrelated.loadmore.iface;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2016/12/29 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 加载更多的功能接口 <br>
 */
public interface LoadMoreHandler {

    /**
     * 触发加载更多
     *
     * @param loadMoreContainer
     */
    void onLoadMore(LoadMoreContainer loadMoreContainer);
}
