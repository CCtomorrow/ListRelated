package com.ai.listrelated.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ai.listrelated.sample.defaultimpl.DefaultRefreshLoadmoreActivity;
import com.ai.listrelated.sample.loadmore.LoadmoreGridActivity;
import com.ai.listrelated.sample.loadmore.LoadmoreListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void defaultImpl(View view) {
        startActivity(new Intent(this, DefaultRefreshLoadmoreActivity.class));
    }

    public void loadmoreListView(View view) {
        startActivity(new Intent(this, LoadmoreListActivity.class));
    }

    public void loadmoreGridView(View view) {
        startActivity(new Intent(this, LoadmoreGridActivity.class));
    }

}
