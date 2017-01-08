package com.ai.listrelated.sample;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2017/1/8 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 测试的数据类 <br>
 */
public class ReplyBean {

    public static final int TOTAL_PAGE = 5;

    private String content;
    private int imageid;

    public ReplyBean() {
    }

    public ReplyBean(String content, int imageid) {
        this.content = content;
        this.imageid = imageid;
    }

    public String getContent() {
        return "测试数据啦:" + content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public static void getData(
            final Handler handler,
            final int page,
            final OnDataCallBack<List<ReplyBean>> listener) {
        ThreadManager.getManager().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final List<ReplyBean> data = new ArrayList<>();
                for (int i = 1; i <= 20; i++) {
                    ReplyBean replyBean = new ReplyBean();
                    replyBean.setContent("<=====>数据+" + ((page - 1) * 10 + i) + "<=====>");
                    replyBean.setImageid(R.drawable.ic_beauty);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onCallback(data);
                        listener.onFinish();
                    }
                });

            }
        });
    }

    public static interface OnDataCallBack<T> {
        void onCallback(T data);

        void onFinish();
    }

}
