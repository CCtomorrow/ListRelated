package com.ai.listrelated.sample.slide;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ai.listrelated.sample.R;
import com.ai.listrelated.slideview.SwipeLayoutManager;

import java.util.Random;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2018/10/19 <br>
 * <b>@author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class SlideActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        mRecyclerView = findViewById(R.id.slid_view);
        initEvent();
    }

    private void initEvent() {
        SwipeLayoutManager manager = new SwipeLayoutManager(this, mRecyclerView);
        manager.setILoadDataListener(new SwipeLayoutManager.ILoadDataListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplication(), "刷新", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadMore() {

            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(new RvAdapter());
    }

    static class RvAdapter extends RecyclerView.Adapter<RvHolder> {

        Random random = new Random();

        @Override
        public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e("SlideActivity", "onCreateViewHolder");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_slide_item, parent, false);
            RvHolder holder = new RvHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RvHolder holder, int position) {
            //随机颜色
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            holder.itemView.setBackgroundColor(Color.rgb(r, g, b));
            holder.mTextView.setText("Special Item:" + (position + 1));
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }

    static class RvHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public RvHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.text);
        }
    }

}
