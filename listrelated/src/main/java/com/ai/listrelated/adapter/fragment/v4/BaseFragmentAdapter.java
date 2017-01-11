package com.ai.listrelated.adapter.fragment.v4;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.ai.listrelated.adapter.fragment.TabInFo;

import java.util.ArrayList;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/7 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> Fragment的adapter <br>
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {

    protected Context mContext;
    protected ArrayList<TabInFo> mTabInFos;
    protected SparseArray<Fragment> mFragments;
    protected FragmentManager mFragmentManager;

    public BaseFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mFragmentManager = fm;
        mTabInFos = new ArrayList<>();
        mFragments = new SparseArray<>();
    }

    /**
     * 获取对应位置的Fragment的tag，名称
     *
     * @param position Fragment所在的位置
     * @return
     */
    public String getFragmentName(int position) {
        return mTabInFos.get(position).getTag();
    }

    /**
     * 添加一个Fragment
     */
    public void addFragment(TabInFo info) {
        mTabInFos.add(info);
        notifyDataSetChanged();
    }

    /**
     * 添加一个Fragment
     *
     * @param tag   tag，名称
     * @param clss  类
     * @param args  参数
     * @param title 标题
     */
    public void addFragment(String tag, Class<?> clss, Bundle args, String title) {
        TabInFo info = new TabInFo(tag, clss, args, title);
        mTabInFos.add(info);
        notifyDataSetChanged();
    }

    /**
     * 获取Fragment
     *
     * @param position 位置
     * @return
     */
    public Fragment getFragment(int position) {
        String tag = getFragmentName(position);
        Fragment fragment = mFragmentManager.
                findFragmentByTag(tag);
        if (fragment == null) {
            return getItem(position);
        }
        return fragment;
    }

    @Override
    public Fragment getItem(int position) {
        TabInFo info = mTabInFos.get(position);
        Fragment fragment = mFragments.get(position);
        if (fragment == null) {
            fragment = Fragment.instantiate(mContext, info.getClss().getName(), info.getArgs());
            mFragments.put(position, fragment);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTabInFos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabInFos.get(position).getTitle();
    }

    /**
     * 为Fragment生成tag，名称
     *
     * @param viewId ViewPager的id
     * @param id     位置，当前的Fragment所在的position位置
     * @return
     */
    public static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

}
