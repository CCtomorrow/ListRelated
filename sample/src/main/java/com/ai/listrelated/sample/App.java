package com.ai.listrelated.sample;

import android.app.Application;

import com.socks.library.KLog;

/**
 * <b>Project:</b> ListRelated <br>
 * <b>Create Date:</b> 2016/12/30 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Logger.init("ListRelated").logLevel(LogLevel.FULL);
        KLog.init(true, "ListRelated");
    }
}
