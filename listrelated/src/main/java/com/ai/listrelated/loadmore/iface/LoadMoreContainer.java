package com.ai.listrelated.loadmore.iface;

import android.view.View;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2016/12/28 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 加载更多的LoadMoreContainer的接口，我通过一些对比，实现加载更多有两种方案 <br>
 * <b>一种是:</b>在一个布局里面分别排放刷新View，List之类的View，以及加载更多的View，这样的话，有比较
 * 多的代码要写，当然对新手也能锻炼技术，但是这样的话，可能会有性能的损耗，特别是使用padding的时候，使用scroller
 * 滚动可能还好点。
 * <b>一种是:</b>直接利用ListView添加FooterView的功能，或者是item的形式，GridView官方有提供一个可以添加HeaderView
 * 的例子，RecyclerView也一样。
 */
public interface LoadMoreContainer {

    /**
     * 是否自动加载更多，如果不是的话，需要用户手动点击加载
     *
     * @param autoLoadMore
     */
    void setAutoLoadMore(boolean autoLoadMore);

    /**
     * 设置加载更多的View，该View需要实现{@link LoadMoreUIHandler}
     *
     * @param view
     */
    void setLoadMoreView(View view);

    /**
     * 设置加载更多的UIHandler，基本就是上面的loadmoreView
     *
     * @param handler
     */
    void setLoadMoreUIHandler(LoadMoreUIHandler handler);

    /**
     * 加载更多功能触发接口
     *
     * @param handler
     */
    void setLoadMoreHandler(LoadMoreHandler handler);

    /**
     * 加载更多完成
     *
     * @param hasMore
     */
    void loadMoreFinish(boolean hasMore);

    /**
     * 加载更多出错
     */
    void loadMoreError();

    /**
     * 是否展示所有的数据都加载完成了之后的，展示的没有更多数据的View
     *
     * @param show
     */
    void showLoadAllFinishView(boolean show);
}
