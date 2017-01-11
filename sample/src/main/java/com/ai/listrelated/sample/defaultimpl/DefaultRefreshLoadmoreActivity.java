package com.ai.listrelated.sample.defaultimpl;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ai.listrelated.adapter.fragment.v4.BaseFragmentAdapter;
import com.ai.listrelated.sample.R;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/8 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 下拉刷新和加载更多的ListView，GridView，RecyclerView <br>
 */
public class DefaultRefreshLoadmoreActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private BaseFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_base_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("默认的实现");

//        ListViewFragment fragment = ListViewFragment.getInstance(null);
//        GridViewFragment fragment = GridViewFragment.getInstance(null);
//        RecyclerviewFragment fragment = RecyclerviewFragment.getInstance(null);
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), this);
        mAdapter.addFragment(BaseFragmentAdapter.makeFragmentName(mViewPager.getId(), 0),
                ListViewFragment.class, null, "ListView示例");
        mAdapter.addFragment(BaseFragmentAdapter.makeFragmentName(mViewPager.getId(), 1),
                GridViewFragment.class, null, "GridView示例");
        mAdapter.addFragment(BaseFragmentAdapter.makeFragmentName(mViewPager.getId(), 2),
                RecyclerviewFragment.class, null, "Recyclerview示例");
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

}
