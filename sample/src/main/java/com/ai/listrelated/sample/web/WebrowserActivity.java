package com.ai.listrelated.sample.web;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ai.listrelated.adapter.fragment.v4.BaseFragmentAdapter;
import com.ai.listrelated.sample.R;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/11/21 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class WebrowserActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private BaseFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_base_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.GONE);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), this);
        mAdapter.addFragment(BaseFragmentAdapter.makeFragmentName(mViewPager.getId(), 0),
                WebrowserFragment.class, null, "浏览器ONE");
        mAdapter.addFragment(BaseFragmentAdapter.makeFragmentName(mViewPager.getId(), 1),
                WebrowserFragment.class, null, "浏览器TWO");
        mAdapter.addFragment(BaseFragmentAdapter.makeFragmentName(mViewPager.getId(), 2),
                WebrowserFragment.class, null, "浏览器THREE");
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

}
