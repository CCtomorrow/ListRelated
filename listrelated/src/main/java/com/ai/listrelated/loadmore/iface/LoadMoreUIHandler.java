package com.ai.listrelated.loadmore.iface;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2016/12/28 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 加载更多的ui接口，如果需要自定义LoadMore的View，可以实现这个接口 <br>
 */
public interface LoadMoreUIHandler {

    /**
     * 加载更多中，这个方法里面需要处理加载更多的时候展示的View
     *
     * @param container
     */
    void onLoading(LoadMoreContainer container);

    /**
     * 正常的加载完成了，这个方法里面需要处理正常的加载完成展示View的形式，并且传递进来是否还有更多的数据
     *
     * @param container
     * @param hasMore   是否还有数据
     */
    void onLoadFinish(LoadMoreContainer container, boolean hasMore);

    /**
     * 等待加载更多中，这个方法里面需要处理等待加载更多的时候处理展示的View
     *
     * @param container
     */
    void onWaitToLoadMore(LoadMoreContainer container);

    /**
     * 加载出错了，这个方法里面需要处理加载出错展示的View，并且可以传递需要展示的text进来
     *
     * @param container
     */
    void onLoadError(LoadMoreContainer container);
}
