/**
 * 加载更多的兼容ListView和RecyclerView的实现，不是添加FooterView或者最后一个Item为加载更多的View
 * 采用的方式是为ListView或者GridView或者RecyclerView包裹一层，底部放上LoadMoreView
 * 采用下面的代码，有修改：修改的地方我会标注出来以方便使用者和原版本区分
 * https://github.com/liaohuqiu/cube-sdk
 */
package com.ai.listrelated.loadmore;