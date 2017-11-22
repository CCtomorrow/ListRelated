package com.ai.listrelated.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ai.listrelated.sample.defaultimpl.DefaultRefreshActivity;
import com.ai.listrelated.sample.web.WebrowserActivity;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
    }

    public void defaultImpl(View view) {
        startActivity(new Intent(this, DefaultRefreshActivity.class));
    }

    public void goWebrowser(View view) {
        startActivity(new Intent(this, WebrowserActivity.class));
    }
}
