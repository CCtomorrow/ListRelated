package com.ai.listrelated.sample.defaultimpl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ai.listrelated.sample.R;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/8 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 下拉刷新和加载更多的ListView，GridView，RecyclerView <br>
 */
public class DefaultRefreshLoadmoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_base_layout);
        ListViewFragment fragment = ListViewFragment.getInstance(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

}
